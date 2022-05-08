package com.tienda.online.service;

import java.util.List;
import java.util.Optional;

import com.tienda.online.model.Usuario;

public interface IUsuarioService {

	public Optional<Usuario> findById(Long id);
	
	public Usuario save(Usuario usuario);
	
	public Optional<Usuario> findByEmail(String email);
	
	public List<Usuario> findAll();
	
}
