package com.tienda.online.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
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

import com.itextpdf.text.DocumentException;
import com.tienda.online.model.Pedido;
import com.tienda.online.model.Producto;
import com.tienda.online.model.Rol;
import com.tienda.online.model.Usuario;
import com.tienda.online.service.IPedidoService;
import com.tienda.online.service.IProductoService;
import com.tienda.online.service.IUsuarioService;
import com.tienda.online.utils.JavaMail;
import com.tienda.online.utils.ProductoExporterExcel;

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

	// Encriptador
	BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

	/**
	 * Método get de página principal del administrador donde se lista los productos y se guarda en sesión el id del usuario
	 * @param modelo El modelo que pasa la lista de productos y la sesión
	 * @param sesion La sesión
	 * @return Retorna a la página principal del administrador
	 */
	@GetMapping("")
	public String home(Model modelo, HttpSession sesion) {

		List<Producto> listaProductos = productoService.findAll();
		modelo.addAttribute("listaProductos", listaProductos);
		modelo.addAttribute("sesion", sesion.getAttribute("idUsuario"));

		return "administrador/home";
	}

	/**
	 * Método get que lista todos los usuarios registrados
	 * @param modelo El modelo que pasa los usuarios
	 * @return Retorna al apartado de usuarios del administrador
	 */
	@GetMapping("/usuarios")
	public String usuarios(Model modelo) {
		modelo.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}

	/**
	 * Método get para crear un usuario
	 * @return Retorna a la página para crear un usuario
	 */
	@GetMapping("/crear_usuario")
	public String crearUsuario() {
		return "administrador/crear_usuario";
	}

	/**
	 * Método get que lista todos los pedidos realizados
	 * @param modelo El modelo
	 * @return Retorna a la página de pedidos
	 */
	@GetMapping("/pedidos")
	public String pedidos(Model modelo) {

		modelo.addAttribute("pedidos", pedidoService.findAll());

		return "administrador/pedidos";
	}

	/**
	 * Método get que localiza un pedido por su id
	 * @param id El id del pedido
	 * @param modelo El modelo que pasa los detalles de un pedido
	 * @return Retorna al apartado del detalle del pedido
	 */
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Long id, Model modelo) {
		LOGGER.info("Id del pedido: {}",id);

		Pedido pedido = pedidoService.findById(id).get();

		modelo.addAttribute("detalles", pedido.getDetalle());

		return "administrador/detalle_pedido";
	}

	/**
	 * Método get para editar un pedido por su id
	 * @param id El id del pedido
	 * @param modelo El modelo que pasa el pedido
	 * @return Retorna al apartado de editar el pedido
	 */
	@GetMapping("/pedidos/editar_pedido/{id}")
	public String editarPedido(@PathVariable Long id, Model modelo) {
		Pedido pedido = new Pedido();
		Optional<Pedido> pedidoOpcional = pedidoService.findById(id);
		pedido = pedidoOpcional.get();

		LOGGER.info("Producto buscado: {}", pedido);

		modelo.addAttribute("pedido", pedido);

		return "administrador/editar_pedido";
	}

	/**
	 * Método post para actualizar los datos de un pedido. En caso de actualizar el estado a 'E' de Enviado se enviará un email al cliente con los datos de su factura.
	 * @param pedido El pedido que se va a modificar
	 * @param flash Atributo flash para enviar el mensaje de aviso
	 * @return Redirecciona al método de pedidos
	 */
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
		} else {
			flash.addFlashAttribute("pedidoEditado", "Pedido editado correctamente");
		}

		pedidoService.update(pedido);

		return "redirect:/administrador/pedidos";
	}

	/**
	 * Método get para eliminar un pedido a través de su id
	 * @param id El id del pedido
	 * @param flash Atributo flash para enviar el mensaje de aviso
	 * @return Redirecciona al método de pedidos
	 */
	@GetMapping("/pedidos/eliminar_pedido/{id}")
	public String eliminarPedido(@PathVariable Long id, RedirectAttributes flash) {
		@SuppressWarnings("unused")
		Pedido p = new Pedido();
		p = pedidoService.findById(id).get();

		flash.addFlashAttribute("pedidoEliminado", "Pedido eliminado correctamente");

		pedidoService.delete(id);

		return "redirect:/administrador/pedidos";

	}

	/**
	 * Método get que muestra el perfil de un usuario
	 * @param id El id del usuario
	 * @param modelo El modelo que pasara el usuario loggeado
	 * @param usuario El usuario del que se va a mostrar el detalle
	 * @return Retorna al perfil del usuario
	 */
	@GetMapping("/detalle_perfil/{id}")
	public String mostrarPerfil (@PathVariable Long id, Model modelo, @ModelAttribute Usuario usuario) {
		LOGGER.info("Id del usuario: {}",id);

		usuario = usuarioService.findById(id).get();

		modelo.addAttribute("usuarioLoggeado", usuario);

		return "administrador/detalle_perfil";

	}

	/**
	 * Método get para editar el perfil del usuario a través de su id
	 * @param id El id del usuario
	 * @param modelo El modelo para pasar el usuario
	 * @return Retorna al apartado de editar el perfil
	 */
	@GetMapping("/detalle_perfil/editar_perfil/{id}")
	public String editarPerfil(@PathVariable Long id, Model modelo) {
		Usuario usuario = new Usuario();
		Optional<Usuario> usuarioOpcional = usuarioService.findById(id);
		usuario = usuarioOpcional.get();

		LOGGER.info("Usuario buscado: {}", usuario);

		modelo.addAttribute("usuario", usuario);

		return "administrador/editar_perfil";
	}

	/**
	 * Método post para actualizar el perfil del usuario. Se vuelve a encriptar su contraseña
	 * @param usuario El usuario 
	 * @param flash Atributo flash para enviar el mensaje a la vista
	 * @return Redirecciona al detalle del perfil
	 */
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
		// Si hay cambios en la contraseña se recodifica
		if(!usuario.getPassword().equals(usuarioModificado.getPassword())) {
			usuario.setPassword(passEncode.encode(usuario.getPassword()));
		}

		usuarioService.update(usuario);

		return "redirect:/administrador/detalle_perfil/".concat(usuarioModificado.getId().toString());
	}

	/**
	 * Método get para eliminar el perfil de un usuario	
	 * @param id El id del usuario
	 * @param flash Atributo flash para enviar el mensaje a la vista
	 * @return Redirecciona al método usuarios
	 */
	@GetMapping("/detalle_perfil/eliminar_perfil/{id}")
	public String eliminarPerfil(@PathVariable Long id, RedirectAttributes flash) {
		@SuppressWarnings("unused")
		Usuario u = new Usuario();
		u = usuarioService.findById(id).get();

		flash.addFlashAttribute("perfilEliminado", "Perfil eliminado correctamente");

		usuarioService.delete(id);

		return "redirect:/administrador/usuarios";

	}	

	/**
	 * Método post para guardar un usuario
	 * @param usuario El usuario
	 * @param sesion La sesión actual
	 * @param flash Atributo flash para enviar el mensaje a la vista
	 * @return Redirecciona al método usuarios 
	 * @throws IOException
	 */
	@PostMapping("/usuarios/save")
	public String save(Usuario usuario, HttpSession sesion, RedirectAttributes flash) throws IOException {
		LOGGER.info("Sesión del usuario: {}", sesion.getAttribute("idUsuario"));
		LOGGER.info("Este es el objeto producto de la vista {}", usuario);

		flash.addFlashAttribute("usuarioGuardado", "Usuario guardado correctamente");

		usuarioService.save(usuario);
		return "redirect:/administrador/usuarios";
	}

	/**
	 * Método get para exportar los productos a excel
	 * @param response La respuesta http
	 * @throws DocumentException 
	 * @throws IOException
	 */
	@GetMapping("/exportarProductosExcel")
	public void exportarListadoProductosExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_".concat(fechaActual).concat(".xlsx");

		response.setHeader(cabecera, valor);

		List<Producto> productos = productoService.findAll();

		ProductoExporterExcel exporter = new ProductoExporterExcel(productos);
		exporter.exportar(response);

	}
}
