package com.tienda.online.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "configuracion")
public class Configuracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String direccion;
	private String localidad;
	private String provincia;
	private String telefono;
	private String cif;
	
	
	public Configuracion() {
		super();
	}
	public Configuracion(Long id, String nombre, String direccion, String localidad, String provincia, String telefono,
			String cif) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.telefono = telefono;
		this.cif = cif;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	@Override
	public String toString() {
		return "Configuracion [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", localidad="
				+ localidad + ", provincia=" + provincia + ", telefono=" + telefono + ", cif=" + cif + "]";
	}

}
