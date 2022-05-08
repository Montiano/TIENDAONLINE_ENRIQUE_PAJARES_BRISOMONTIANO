package com.tienda.online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.online.model.Producto;
import com.tienda.online.service.IProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	@Autowired
	private IProductoService productoService;
	
	@GetMapping("")
	public String home(Model modelo) {
		
		List<Producto> listaProductos = productoService.findAll();
		modelo.addAttribute("listaProductos", listaProductos);
		
		return "administrador/home";
	}
}
