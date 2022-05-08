package com.tienda.online.service;

import java.util.List;

import com.tienda.online.model.Pedido;

public interface IPedidoService {

	public List<Pedido> findAll();
	
	public Pedido save(Pedido pedido);
	
	public String generateNumFra();
}
