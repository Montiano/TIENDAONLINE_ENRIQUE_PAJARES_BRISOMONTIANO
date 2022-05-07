package com.tienda.online.service;

import java.util.List;
import java.util.Optional;

import com.tienda.online.model.Producto;

public interface ProductoService {

	public Producto save(Producto producto);	
	// Con optional podemos validar si el objeto existe o no en BD
	public Optional<Producto> get(Long id);
	
	public void update(Producto producto);
	
	public void delete(Long id);
	
	public List<Producto> findAll();
	
}
