package com.tienda.online.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.online.model.DetallePedido;
import com.tienda.online.repository.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements IDetallePedidoService{

	@Autowired
	private DetallePedidoRepository detallePedidoRepository;
	
	@Override
	public DetallePedido save(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}

	@Override
	public Optional<DetallePedido> get(Long id) {
		return detallePedidoRepository.findById(id);
	}

	@Override
	public List<DetallePedido> findAll() {
		return detallePedidoRepository.findAll();
	}

	@Override
	public Optional<DetallePedido> findById(Long id) {
		return detallePedidoRepository.findById(id);
	}

	
	
}
