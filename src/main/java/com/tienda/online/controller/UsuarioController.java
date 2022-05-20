package com.tienda.online.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.tienda.online.model.DetallePedido;
import com.tienda.online.model.Pedido;
import com.tienda.online.model.Producto;
import com.tienda.online.model.Rol;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IDetallePedidoService;
import com.tienda.online.service.IPedidoService;
import com.tienda.online.service.IUsuarioService;
import com.tienda.online.utils.FacturaExporterPDF;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	@Autowired
	private IDetallePedidoService detallePedidoService;
	
	BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();
	
	@GetMapping("/registro")
	public String create(Model modelo) {
		modelo.addAttribute("usuario", new Usuario());
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, RedirectAttributes flash ) {
		LOGGER.info("Usuario registro: {}", usuario);
		
		if(bindingResult.hasErrors()) {			
			return "usuario/registro";
		}
		else {
			Rol rol = new Rol(2L,"USER");
			usuario.setTipo("USER");
			usuario.setRol(rol);
			usuario.setPassword(passEncode.encode(usuario.getPassword()));
			Usuario user;
			try {
				//List<Usuario> listaUsuarios = usuarioService.findAll().stream().filter(u->u.getEmail().contains(usuario.getEmail())).collect(Collectors.toList());
				user = usuarioService.findByEmail(usuario.getEmail()).get();
			} catch (NoSuchElementException e) {
				user = null;
			}
			
			// Comprobamos si el email introducido ya está registrado
			if(user != null) {
				LOGGER.info("Usuario encontrado: "+user);
				flash.addFlashAttribute("usuarioYaRegistrado", "Lo sentimos, el email introducido ya está registrado, inténtelo con otro...");
				return "redirect:/usuario/registro";
			}else {
				flash.addFlashAttribute("usuarioRegistrado", "Usuario registrado correctamente");
				LOGGER.info("Usuario guardado correctamente");
				usuarioService.save(usuario);
				return "redirect:/usuario/login";
			}
		}
		
		
			
	}
	
	@GetMapping("/login")
	public String login() {	
		return "usuario/login";
	}
	
	@GetMapping("/access")
	public String access(Usuario usuario, HttpSession sesion) {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		LOGGER.info("Acceso: {}", usuario);
		LOGGER.info("Dato del usuario es: " + sesion.getAttribute("idUsuario"));
		
		//int id = Integer.parseInt(sesion.getAttribute("idUsuario").toString());	
		
		Optional<Usuario> user = usuarioService.findById(Long.parseLong(sesion.getAttribute("idUsuario").toString()));
		
		//LOGGER.info("Usuario de base de datos: {}", user.get());
		// Con optional nos permite hacer estas validaciones
		if(user.isPresent()) {
			sesion.setAttribute("idUsuario", user.get().getId());
			if(user.get().getTipo().equals("ADMIN")) {
				sesion.setAttribute("sessionActive", 1);
				return "redirect:/administrador";
			} else {
				sesion.setAttribute("sessionActive", 2);
				LOGGER.info("Sesión activa es: " + sesion.getAttribute("sessionActive"));
				return "redirect:/";
			}
		} else {
			sesion.setAttribute("sessionActive", null);
			LOGGER.info("Sesión activa es: " + sesion.getAttribute("sessionActive"));
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
	
	@GetMapping("/cancelar/{id}")
	public String purchaseCancel(@PathVariable Long id, HttpSession sesion, Model modelo, RedirectAttributes flash) {	
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		LOGGER.info("Id del pedido: {}", id);
		
		Optional<Pedido> pedido = pedidoService.findById(id);
		
		pedido.get().setEstado("PC");
		pedidoService.update(pedido.get());
		
		modelo.addAttribute("pedido", pedido);
		
		modelo.addAttribute("detalles", pedido.get().getDetalle());
		
		modelo.addAttribute("sesion", sesion.getAttribute("idUsuario"));
		
		flash.addFlashAttribute("solicitudCancelacion", "Se ha solicitado la cancelación del pedido");
		
		return "redirect:/usuario/shopping";
	}
	
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession sesion, RedirectAttributes flash) {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		
		sesion.removeAttribute("idUsuario");
		
		flash.addFlashAttribute("sesionCerrada", "Se ha cerrado la sesión correctamente");
		
		return "redirect:/";
	}
	
	@GetMapping("/exportarFacturaPDF/{id}")
	public void exportarFacturaPDF(@PathVariable Long id, HttpServletResponse response) throws DocumentException, IOException {
		
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Detalle_Pedido_".concat(fechaActual).concat(".pdf");
		
		response.setHeader(cabecera, valor);
		
		Optional<Pedido> pedido = pedidoService.findById(id);
		  //List<DetallePedido> detallesPedido = detallePedidoService.findById(id);
		  
		 FacturaExporterPDF exporter = new FacturaExporterPDF(pedido.get().getDetalle());
		 exporter.exportar(response);
		 
		
	}
	
	@GetMapping("/detalle_perfil/{id}")
	public String mostrarPerfil (@PathVariable Long id, Model modelo, @ModelAttribute Usuario usuario) {
		LOGGER.info("Id del usuario: {}",id);
		
		usuario = usuarioService.findById(id).get();
		
		modelo.addAttribute("usuarioLoggeado", usuario);
		
		return "usuario/detalle_perfil";
		
		
	}
	
	@GetMapping("/detalle_perfil/editar_perfil/{id}")
	public String editarPerfil(@PathVariable Long id, Model modelo) {
		Usuario usuario = new Usuario();
		Optional<Usuario> usuarioOpcional = usuarioService.findById(id);
		usuario = usuarioOpcional.get();
		
		LOGGER.info("Usuario buscado: {}", usuario);
		
		modelo.addAttribute("usuario", usuario);
		
		return "usuario/editar_perfil";
	}
	
	
	@PostMapping("/detalle_perfil/update")
	public String update(Usuario usuario, RedirectAttributes flash) {
		Usuario usuarioModificado = new Usuario();
		usuarioModificado = usuarioService.findById(usuario.getId()).get();
		
		flash.addFlashAttribute("perfilEditado", "Perfil guardado correctamente");
		
		System.out.println("usuarioModificado: " + usuarioModificado);
		System.out.println("usuario: " + usuario);
			
		Rol rol = new Rol(2L,"USER");
		usuario.setRol(rol);
		usuario.setTipo("USER");
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		
		usuarioService.update(usuario);
		
		return "redirect:/usuario/detalle_perfil/".concat(usuarioModificado.getId().toString());
	}
	
	
}
