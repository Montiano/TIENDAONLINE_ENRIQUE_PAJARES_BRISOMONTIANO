package com.tienda.online.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.online.model.DetallePedido;
import com.tienda.online.model.Pedido;
import com.tienda.online.model.Producto;
import com.tienda.online.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	List<DetallePedido> detallesPedido = new ArrayList<DetallePedido>();
	
	Pedido pedido = new Pedido();
	
	@GetMapping("")
	public String home(Model modelo) {	
		modelo.addAttribute("listaProductos", productoService.findAll());
		return "usuario/home";
	}
	
	@GetMapping("producto_home/{id}")
	public String productoHome(@PathVariable Long id, Model modelo) {
		LOGGER.info("Id producto enviado por parámetro {}", id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		modelo.addAttribute("producto", producto);
			
		return "usuario/producto_home";
	}
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Long id, @RequestParam int unidades, Model modelo) {
		DetallePedido detallePedido = new DetallePedido();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Optional<Producto> productoOptional = productoService.get(id);
		LOGGER.info("Producto añadido: {}", productoOptional.get());
		LOGGER.info("Unidades: {}", unidades);
		producto = productoOptional.get();
		
		detallePedido.setUnidades(unidades);
		detallePedido.setPrecioUnidad(producto.getPrecio());
		detallePedido.setNombre(producto.getNombre());
		detallePedido.setTotal(producto.getPrecio()*unidades);
		detallePedido.setProducto(producto);
		
		detallesPedido.add(detallePedido);
		
		sumaTotal = detallesPedido.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		pedido.setTotal(sumaTotal);
		
		modelo.addAttribute("detallesPedido", detallesPedido);
		modelo.addAttribute("pedido", pedido);
		
		return "usuario/carrito";
	}
	
}
