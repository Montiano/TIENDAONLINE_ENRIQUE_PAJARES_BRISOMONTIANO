package com.tienda.online.service;

import java.util.Optional;

import com.tienda.online.model.Usuario;

public interface IUsuarioService {

	public Optional<Usuario> findById(Long id);
	
}