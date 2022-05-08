package com.tienda.online.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.online.model.Usuario;
import com.tienda.online.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		LOGGER.info("Usuario registro: {}", usuario);
		usuario.setTipo("USER");
		usuario.setIdRol(2);
		usuarioService.save(usuario);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {	
		return "usuario/login";
	}
	
	@PostMapping("/access")
	public String access(Usuario usuario, HttpSession sesion) {
		LOGGER.info("Acceso: {}", usuario);
		
		Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
		
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
	
}
