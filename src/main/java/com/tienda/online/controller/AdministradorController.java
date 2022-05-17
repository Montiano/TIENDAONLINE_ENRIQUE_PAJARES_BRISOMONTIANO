package com.tienda.online.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tienda.online.model.Pedido;
import com.tienda.online.model.Producto;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IPedidoService;
import com.tienda.online.service.IProductoService;
import com.tienda.online.service.IUsuarioService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	private Logger LOGGER = LoggerFactory.getLogger(AdministradorController.class);
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	
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
	
	@GetMapping("/perfil/{id}")
	public String mostrarPerfil (@PathVariable Long id, Model modelo) {
		LOGGER.info("Id del usuario: {}",id);
		
		Usuario usuario = usuarioService.findById(id).get();
		
		modelo.addAttribute("usuarioLoggeado", usuario);
		
		return "administrador/perfil";
	}
	
	
}
