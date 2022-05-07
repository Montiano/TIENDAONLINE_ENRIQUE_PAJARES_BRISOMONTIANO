package com.tienda.online.controller;

import java.io.IOException;
import java.util.Optional;

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

import com.tienda.online.model.Producto;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.ProductoService;
import com.tienda.online.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
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
	public String save(Producto producto, @RequestParam("imagen") MultipartFile file) throws IOException {
		LOGGER.info("Este es el objeto producto de la vista {}", producto);
		Usuario u = new Usuario(1L, 0, null, null, null, null, null, null, null, null, null, null);
		producto.setUsuario(u);
		
		// En caso de que creamos un producto
		if(producto.getId()==null) {
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		} 
		// En caso de editar el producto pero no cambiamos la imagen
		else {
			if(file.isEmpty()) {
				Producto p = new Producto();
				p = productoService.get(producto.getId()).get();
				producto.setImagen(p.getImagen());
			} else {
				String nombreImagen = upload.saveImage(file);
				producto.setImagen(nombreImagen);
			}
		}
		productoService.save(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model modelo) {
		Producto producto = new Producto();
		Optional<Producto> optionalProducto = productoService.get(id);
		producto = optionalProducto.get();
		
		LOGGER.info("Producto buscado: {}", producto);
		
		modelo.addAttribute("producto", producto);
			
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto) {
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		productoService.delete(id);
		return "redirect:/productos";
	}
}
