package com.tienda.online.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tienda.online.model.Pedido;
import com.tienda.online.model.Producto;
import com.tienda.online.model.Rol;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IPedidoService;
import com.tienda.online.service.IProductoService;
import com.tienda.online.service.IUsuarioService;
import com.tienda.online.utils.JavaMail;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	private final Logger LOGGER = LoggerFactory.getLogger(AdministradorController.class);
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();
	
	@GetMapping("")
	public String home(Model modelo, HttpSession sesion) {
		
		List<Producto> listaProductos = productoService.findAll();
		modelo.addAttribute("listaProductos", listaProductos);
		modelo.addAttribute("sesion", sesion.getAttribute("idUsuario"));
		
		return "administrador/home";
	}
	
	@GetMapping("/usuarios")
	public String usuarios(Model modelo) {
		modelo.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}
	
	@GetMapping("/crear_usuario")
	public String crearUsuario() {
		return "administrador/crear_usuario";
	}
	
	@GetMapping("/pedidos")
	public String pedidos(Model modelo) {
		
		modelo.addAttribute("pedidos", pedidoService.findAll());
		
		return "administrador/pedidos";
	}
	
	
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Long id, Model modelo) {
		LOGGER.info("Id del pedido: {}",id);
		
		Pedido pedido = pedidoService.findById(id).get();
		
		modelo.addAttribute("detalles", pedido.getDetalle());
		
		return "administrador/detalle_pedido";
	}
	
	
	@GetMapping("/pedidos/editar_pedido/{id}")
	public String editarPedido(@PathVariable Long id, Model modelo) {
		Pedido pedido = new Pedido();
		Optional<Pedido> pedidoOpcional = pedidoService.findById(id);
		pedido = pedidoOpcional.get();
		
		LOGGER.info("Producto buscado: {}", pedido);
		
		modelo.addAttribute("pedido", pedido);
		
		return "administrador/editar_pedido";
	}
	
	
	@PostMapping("/pedidos/update")
	public String update(Pedido pedido, RedirectAttributes flash) {
		Pedido pedidoModificado = new Pedido();
		pedidoModificado = pedidoService.findById(pedido.getId()).get();
		
		pedido.setUsuario(pedidoModificado.getUsuario());
			
		if(pedido.getEstado().equals("E")) {
			
			pedido.setNumFactura(pedidoService.generateNumFra());
			
			String destinatario = pedido.getUsuario().getEmail();
			String texto = ("Su pedido con nº de factura ".concat(pedido.getNumFactura()).concat(" ha sido enviado.\n")
					.concat("El valor total del pedido es: ").concat(pedido.getTotal().toString().concat(" €\n"))
					.concat("El método de pago elegido es: ").concat(pedido.getMetodoPago())
					.concat("\nFecha de la factura: ").concat(pedido.getFecha().toString())
					.concat("\n\nYa puede descargarse su factura en Pdf si lo desea.\n\nMuchas gracias por su compra.\nUn saludo!"));
			JavaMail.enviarMail(destinatario, texto);
			flash.addFlashAttribute("pedidoEditado", "Pedido editado correctamente. Se envió un email al cliente");
		}
		
		flash.addFlashAttribute("pedidoEditado", "Pedido editado correctamente");
		
		pedidoService.update(pedido);
		
		return "redirect:/administrador/pedidos";
	}
	
	@GetMapping("/pedidos/eliminar_pedido/{id}")
	public String eliminarPedido(@PathVariable Long id, RedirectAttributes flash) {
		Pedido p = new Pedido();
		p = pedidoService.findById(id).get();
		
		flash.addFlashAttribute("pedidoEliminado", "Pedido eliminado correctamente");
		
		pedidoService.delete(id);
		
		return "redirect:/administrador/pedidos";
			
	}
	
	@GetMapping("/detalle_perfil/{id}")
	public String mostrarPerfil (@PathVariable Long id, Model modelo, @ModelAttribute Usuario usuario) {
		LOGGER.info("Id del usuario: {}",id);
		
		usuario = usuarioService.findById(id).get();
		
		modelo.addAttribute("usuarioLoggeado", usuario);
		
		return "administrador/detalle_perfil";
		
		
	}
	
	@GetMapping("/detalle_perfil/editar_perfil/{id}")
	public String editarPerfil(@PathVariable Long id, Model modelo) {
		Usuario usuario = new Usuario();
		Optional<Usuario> usuarioOpcional = usuarioService.findById(id);
		usuario = usuarioOpcional.get();
		
		LOGGER.info("Usuario buscado: {}", usuario);
		
		modelo.addAttribute("usuario", usuario);
		
		return "administrador/editar_perfil";
	}
	
	
	@PostMapping("/detalle_perfil/update")
	public String update(Usuario usuario, RedirectAttributes flash) {
		Usuario usuarioModificado = new Usuario();
		usuarioModificado = usuarioService.findById(usuario.getId()).get();
		
		flash.addFlashAttribute("perfilEditado", "Perfil guardado correctamente");
		
		System.out.println("usuarioModificado: " + usuarioModificado);
		System.out.println("usuario: " + usuario);
		
		Rol rol = new Rol(1L,"ADMIN");
		usuario.setRol(rol);
		usuario.setTipo("ADMIN");
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		
		usuarioService.update(usuario);
		
		return "redirect:/administrador/detalle_perfil/".concat(usuarioModificado.getId().toString());
	}
	
		
	@GetMapping("/detalle_perfil/eliminar_perfil/{id}")
	public String eliminarPerfil(@PathVariable Long id, RedirectAttributes flash) {
		Usuario u = new Usuario();
		u = usuarioService.findById(id).get();
		
		flash.addFlashAttribute("perfilEliminado", "Perfil eliminado correctamente");
		
		usuarioService.delete(id);
		
		return "redirect:/administrador/usuarios";
			
	}	
	
	
	@PostMapping("/usuarios/save")
	public String save(Usuario usuario, HttpSession sesion, RedirectAttributes flash) throws IOException {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		LOGGER.info("Este es el objeto producto de la vista {}", usuario);
					
		flash.addFlashAttribute("usuarioGuardado", "Usuario guardado correctamente");
		
		usuarioService.save(usuario);
		return "redirect:/administrador/usuarios";
	}
}
