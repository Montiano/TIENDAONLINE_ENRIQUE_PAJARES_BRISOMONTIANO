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
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IProductoService;
import com.tienda.online.service.IUsuarioService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
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
		
//		int posicion = 0;
//		if(detallesPedido.size()>0) {
//			for (int i = 0; i < detallesPedido.size(); i++) {
//				if(id == detallesPedido.get(i).getId()) {
//					posicion = i;
//				}
//			}
//			if (id == detallesPedido.get(posicion).getId()) {
//				unidades = detallesPedido.get(posicion).getUnidades() + unidades;
//				sumaTotal = detallesPedido.get(posicion).getPrecioUnidad() * unidades;
//				detallesPedido.get(posicion).setUnidades(unidades);
//				detallesPedido.get(posicion).setTotal(sumaTotal);
//			} else {
//				detallesPedido.add(detallePedido);
//			}
//		} else {
//			detallesPedido.add(detallePedido);
//		}
		
		detallePedido.setUnidades(unidades);
		detallePedido.setPrecioUnidad(producto.getPrecio());
		detallePedido.setNombre(producto.getNombre());
		detallePedido.setTotal(producto.getPrecio()*unidades);
		detallePedido.setProducto(producto);
		
		// Validar para que solo haya un producto por lista y no se añada dos veces
		Long idProducto = producto.getId();
		// Función anónima con la que recorremos toda la lista y si coinciden los ids ya existe
		boolean existe = detallesPedido.stream().anyMatch(p->p.getProducto().getId()==idProducto);
		// Si no existe lo añade, si no, no
		if(!existe) {
			detallesPedido.add(detallePedido);
		}
			
		// Función anónima con la que cogemos todos los productos y los sumamos
		sumaTotal = detallesPedido.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		pedido.setTotal(sumaTotal);
		
		modelo.addAttribute("detallesPedido", detallesPedido);
		modelo.addAttribute("pedido", pedido);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/delete/cart/{id}")
	public String deleteProductCart(@PathVariable Long id, Model modelo) {
		List<DetallePedido> listaPedidosNueva = new ArrayList<DetallePedido>();
		
		// Recorremos la lista y si el id no es igual al id pasado lo añadimos
		for (DetallePedido detallePedido : listaPedidosNueva) {
			if(detallePedido.getProducto().getId()!=id) {
				listaPedidosNueva.add(detallePedido);
			}
		}
		// Modificamos la lista de pedidos
		detallesPedido = listaPedidosNueva;
		
		double sumaTotal = 0;
		sumaTotal = detallesPedido.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		pedido.setTotal(sumaTotal);
		
		modelo.addAttribute("detallesPedido", detallesPedido);
		modelo.addAttribute("pedido", pedido);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model modelo) {
		
		modelo.addAttribute("detallesPedido", detallesPedido);
		modelo.addAttribute("pedido", pedido);
		
		return "/usuario/carrito";
	}
	
	@GetMapping("/order")
	public String order(Model modelo) {	
		Usuario usuario = usuarioService.findById(2L).get();
		
		modelo.addAttribute("detallesPedido", detallesPedido);
		modelo.addAttribute("pedido", pedido);
		modelo.addAttribute("usuario", usuario);
		
		return "usuario/resumen_pedido";
	}
	
	
	
	
}
