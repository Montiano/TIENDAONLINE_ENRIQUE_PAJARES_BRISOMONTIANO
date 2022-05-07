package com.tienda.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.online.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
