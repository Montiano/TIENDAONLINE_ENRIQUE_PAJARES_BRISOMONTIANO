package com.tienda.online.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tienda.online.model.DetallePedido;
import com.tienda.online.model.Pedido;
import com.tienda.online.model.Producto;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IDetallePedidoService;
import com.tienda.online.service.IPedidoService;
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

	@Autowired
	private IPedidoService pedidoService;

	@Autowired
	private IDetallePedidoService detallePedidoService;



	List<DetallePedido> detallesPedido = new ArrayList<DetallePedido>();

	Pedido pedido = new Pedido();



	@GetMapping("")
	public String home(Model modelo, HttpSession sesion) {

		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));

		String sesionAtributo = String.valueOf(sesion.getAttribute("idUsuario"));
		System.err.println("Valor del atributo es:"+sesionAtributo);

		modelo.addAttribute("sesionAtributo", sesionAtributo);


		if(sesion.getAttribute("sessionActive")!=null) {
			int var = (int) sesion.getAttribute("sessionActive");
			System.err.println("Session active redireccionada es "+var);
		}

		modelo.addAttribute("listaProductos", productoService.findAll());

		modelo.addAttribute("sesion", sesion.getAttribute("idUsuario"));

		return "usuario/home";
	}

	@GetMapping("producto_home/{id}")
	public String productoHome(@PathVariable Long id, Model modelo, HttpSession sesion) {
		LOGGER.info("Id producto enviado por parámetro {}", id);

		/*
		 * if(sesion.getAttribute("idUsuario")==1) {
		 * modelo.addAttribute("administrador", true); }
		 */

		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();

		modelo.addAttribute("producto", producto);

		return "usuario/producto_home";
	}

	@PostMapping("/cart")
	public String addCart(@RequestParam Long id, @RequestParam int unidades, @RequestParam int stock, Model modelo, RedirectAttributes flash) {
		DetallePedido detallePedido = new DetallePedido();
		Producto producto = new Producto();
		double sumaTotal = 0;

		Optional<Producto> productoOptional = productoService.get(id);
		LOGGER.info("Producto añadido: {}", productoOptional.get());
		LOGGER.info("Unidades: {}", unidades);
		producto = productoOptional.get();

		if(stock>=unidades) {
			
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

			// Cambiamos el stock y lo guardamos en la bd
			producto.setStock(stock-unidades);			
			productoService.save(producto);

			modelo.addAttribute("producto", producto);
			modelo.addAttribute("detallesPedido", detallesPedido);
			modelo.addAttribute("pedido", pedido);

			return "usuario/carrito";
		}
		else {
			flash.addFlashAttribute("errorStock", "Lo sentimos, no hay stock suficiente de este producto");
			return "redirect:/producto_home/".concat(id.toString());
		}
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
	public String getCart(Model modelo, HttpSession sesion) {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));

		modelo.addAttribute("detallesPedido", detallesPedido);
		modelo.addAttribute("pedido", pedido);

		modelo.addAttribute("sesion", sesion.getAttribute("idUsuario"));

		return "/usuario/carrito";
	}

	@GetMapping("/order")
	public String order(Model modelo, HttpSession sesion, RedirectAttributes flash) {	
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));

		if(sesion.getAttribute("idUsuario")==null) {
			LOGGER.info("Usuario no identificado");
			flash.addFlashAttribute("mensajeNoLogeado", "Lo sentimos, no se puede añadir el producto... Primero necesita loggearse");
			return "redirect:/usuario/login";
			//return "usuario/login";

		} else {

			Usuario usuario = usuarioService.findById(Long.parseLong(sesion.getAttribute("idUsuario").toString())).get();

			modelo.addAttribute("detallesPedido", detallesPedido);
			modelo.addAttribute("pedido", pedido);
			modelo.addAttribute("usuario", usuario);

			return "usuario/resumen_pedido";
		}
	}

	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession sesion, @RequestParam String metodoPago, RedirectAttributes flash) {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		Date fechaCreacion = new Date();
		pedido.setFecha(fechaCreacion);
		pedido.setNumFactura(pedidoService.generateNumFra());

		Usuario usuario = usuarioService.findById(Long.parseLong(sesion.getAttribute("idUsuario").toString())).get();

		// Se guarda el pedido
		pedido.setUsuario(usuario);
		pedido.setEstado("E");
		pedido.setMetodoPago(metodoPago);
		pedidoService.save(pedido);

		// Se guarda el detalle del pedido
		for (DetallePedido detallePedido : detallesPedido) {
			detallePedido.setPedido(pedido);
			detallePedidoService.save(detallePedido);
		}
		// Limpiamos el pedido y la lista de detalles
		pedido = new Pedido();
		detallesPedido.clear();
		
		flash.addFlashAttribute("pedidoRealizado", "Pedido realizado correctamente");

		return "redirect:/";
	}


	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model modelo) {
		LOGGER.info("Nombre del producto: {}", nombre);
		// Cogemos todos los productos a través de stream y utilizamos filter para filtrar la búsqueda y lo pasamos a lista
		List<Producto> listaProductos = productoService.findAll().stream().filter(p->p.getNombre().contains(nombre)).collect(Collectors.toList());
		modelo.addAttribute("listaProductos", listaProductos);

		return "usuario/home";
	}
}
