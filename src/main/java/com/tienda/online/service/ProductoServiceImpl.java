package com.tienda.online.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.online.model.Producto;
import com.tienda.online.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements IProductoService{

	// Inyectamos a la clase un objeto
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {	
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(Long id) {	
		return productoRepository.findById(id);
	}

	@Override
	public void update(Producto producto) {
		// JpaRepository reconoce si ya está en la BD y lo actualiza
		productoRepository.save(producto);		
	}

	@Override
	public void delete(Long id) {
		productoRepository.deleteById(id);
	}

	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

}
