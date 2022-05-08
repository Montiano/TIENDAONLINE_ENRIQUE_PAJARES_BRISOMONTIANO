package com.tienda.online.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.online.model.Pedido;
import com.tienda.online.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements IPedidoService{

	// Con autowired Spring sabe que tiene que inyectar este objeto a esta clase de servicio
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Override
	public Pedido save(Pedido pedido) {		
		return pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}

	
	public String generateNumFra() {
		String numeracionConcatenada = "FRA.".concat(LocalDateTime.now().toString());
		return numeracionConcatenada;
	}
}
