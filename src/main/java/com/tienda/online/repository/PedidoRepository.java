package com.tienda.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.online.model.Pedido;
import com.tienda.online.model.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	List<Pedido> findByUsuario(Usuario usuario);
	
	
}
