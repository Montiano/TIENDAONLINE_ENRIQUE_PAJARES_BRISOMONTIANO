package com.tienda.online.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tienda.online.model.Producto;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IProductoService;
import com.tienda.online.service.IUsuarioService;
import com.tienda.online.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private UploadFileService upload;
	
	@GetMapping("")
	public String show(Model modelo) {
		modelo.addAttribute("listaProductos", productoService.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession sesion, RedirectAttributes flash) throws IOException {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		LOGGER.info("Este es el objeto producto de la vista {}", producto);
		
		Usuario u = usuarioService.findById(Long.parseLong(sesion.getAttribute("idUsuario").toString())).get();
		producto.setUsuario(u);
		
		// En caso de que creamos un producto
		if(producto.getId()==null) {
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		} 
		
		flash.addFlashAttribute("productoGuardado", "Producto guardado correctamente");
		
		productoService.save(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model modelo) {
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		LOGGER.info("Producto buscado: {}", producto);
		
		modelo.addAttribute("producto", producto);
			
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto, @RequestParam("img") MultipartFile file, RedirectAttributes flash) throws IOException {
		Producto p = new Producto();
		p = productoService.get(producto.getId()).get();
		
		// En caso de editar el producto pero no cambiamos la imagen
		if(file.isEmpty()) {
			
			producto.setImagen(p.getImagen());
		// Cuando se edite también la imagen
		} else {	
			// Si la imagen no es la de por defecto
			if(!p.getImagen().equals("producto.png")) {
				upload.deleteImage(p.getImagen());
			}				
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		producto.setUsuario(p.getUsuario());
		
		flash.addFlashAttribute("productoEditado", "Producto editado correctamente");
		
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {
		Producto p = new Producto();
		p = productoService.get(id).get();
		
		// Si la imagen no es la de por defecto
		if(!p.getImagen().equals("producto.png")) {
			upload.deleteImage(p.getImagen());
		}
		
		flash.addFlashAttribute("productoEliminado", "Producto eliminado correctamente");
		
		productoService.delete(id);
		return "redirect:/productos";
	}
}
