package com.tienda.online.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tipo;
	
	@OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
	private List<Usuario> usuario;

	public Rol() {
		super();
	}
	
	public Rol(Long id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	public Rol(Long id, String tipo, List<Usuario> usuario) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", tipo=" + tipo + "]";
	}


	
	
	
}
