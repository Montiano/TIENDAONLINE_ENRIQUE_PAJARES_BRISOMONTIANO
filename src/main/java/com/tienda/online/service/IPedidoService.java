package com.tienda.online.service;

import java.util.List;
import java.util.Optional;

import com.tienda.online.model.Pedido;
import com.tienda.online.model.Usuario;

public interface IPedidoService {

	public List<Pedido> findAll();
	
	public Pedido save(Pedido pedido);
	
	public String generateNumFra();
	
	public List<Pedido> findByUsuario(Usuario usuario);
	
	public Optional<Pedido> findById(Long id);
}
