package com.tienda.online.utils;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tienda.online.model.Usuario;
import com.tienda.online.service.IUsuarioService;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private IUsuarioService usuarioService;
	
//	@Autowired
//	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	HttpSession sesion;
	
	private Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	
	/**
	 * Método que carga un usuario según sus claves de acceso
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("Este es el username");
		
		Optional<Usuario> optionalUser = usuarioService.findByEmail(username);
		
		if (optionalUser.isPresent()) {
			LOGGER.info("Este es el id del usuario {}", optionalUser.get().getId());
			sesion.setAttribute("idUsuario", optionalUser.get().getId());
			Usuario usuario = optionalUser.get();
			return User.builder().username(usuario.getNombre()).password(usuario.getPassword()).roles(usuario.getTipo()).build();
		}else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		
		
	}



	

	
}
