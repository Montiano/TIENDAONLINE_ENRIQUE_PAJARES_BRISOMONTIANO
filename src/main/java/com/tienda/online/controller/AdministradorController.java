package com.tienda.online.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.online.model.Pedido;
import com.tienda.online.model.Producto;
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
	public String home(Model modelo) {
		
		List<Producto> listaProductos = productoService.findAll();
		modelo.addAttribute("listaProductos", listaProductos);
		
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
	
	
	
	
}
