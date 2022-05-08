package com.tienda.online.service;

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

}
