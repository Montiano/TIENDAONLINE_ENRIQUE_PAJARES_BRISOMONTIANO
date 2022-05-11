package com.tienda.online.service;

import java.util.List;
import java.util.Optional;

import com.tienda.online.model.DetallePedido;

public interface IDetallePedidoService {

	public DetallePedido save (DetallePedido detallePedido);
	
	public Optional<DetallePedido> findById(Long id);
	
	public List<DetallePedido> findAll();

	public Optional<DetallePedido> get(Long id);
}
