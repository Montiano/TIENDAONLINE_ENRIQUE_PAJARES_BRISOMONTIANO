package com.tienda.online.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tienda.online.model.Pedido;
import com.tienda.online.model.Rol;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IPedidoService;
import com.tienda.online.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();
	
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		LOGGER.info("Usuario registro: {}", usuario);
		Rol rol = new Rol(2L,"USER");
		usuario.setTipo("USER");
		usuario.setRol(rol);
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		usuarioService.save(usuario);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {	
		return "usuario/login";
	}
	
	@GetMapping("/access")
	public String access(Usuario usuario, HttpSession sesion) {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		LOGGER.info("Acceso: {}", usuario);
		System.out.println("Dayo del usuario es: " + sesion.getAttribute("idUsuario"));
		
		Optional<Usuario> user = usuarioService.findById(Long.parseLong(sesion.getAttribute("idUsuario").toString()));
		
		//LOGGER.info("Usuario de base de datos: {}", user.get());
		// Con optional nos permite hacer estas validaciones
		if(user.isPresent()) {
			sesion.setAttribute("idUsuario", user.get().getId());
			if(user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			} else {
				return "redirect:/";
			}
		} else {
			LOGGER.info("Usuario no existe");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/shopping")
	public String shopping(Model modelo, HttpSession sesion) {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		modelo.addAttribute("sesion", sesion.getAttribute("idUsuario"));
		
		Usuario usuario = usuarioService.findById(Long.parseLong(sesion.getAttribute("idUsuario").toString())).get();
		List<Pedido> pedidos = pedidoService.findByUsuario(usuario);
		
		modelo.addAttribute("pedidos", pedidos);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalle/{id}")
	public String purchaseDetail(@PathVariable Long id, HttpSession sesion, Model modelo) {	
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		LOGGER.info("Id del pedido: {}", id);
		
		Optional<Pedido> pedido = pedidoService.findById(id);
		
		modelo.addAttribute("detalles", pedido.get().getDetalle());
		
		modelo.addAttribute("sesion", sesion.getAttribute("idUsuario"));
		
		return "usuario/detalle_compra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession sesion, RedirectAttributes flash) {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		
		sesion.removeAttribute("idUsuario");
		
		flash.addFlashAttribute("sesionCerrada", "Se ha cerrado la sesión correctamente");
		
		return "redirect:/";
	}
	
}
