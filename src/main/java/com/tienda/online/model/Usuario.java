package com.tienda.online.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_rol")
	private int idRol;
	private String email;
	private String clave;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String provincia;
	private String localidad;
	private String telefono;
	private String dni;
	
	@OneToMany(mappedBy = "usuario")
	private List<Producto> productos;
	
	@OneToMany(mappedBy = "usuario")
	private List<Pedido> pedidos;
	
	public Usuario() {
		super();
	}
	public Usuario(String email, String clave) {
		super();
		this.email = email;
		this.clave = clave;
	}
	public Usuario(Long id, int idRol, String email, String clave, String nombre, String apellido1, String apellido2,
			String direccion, String provincia, String localidad, String telefono, String dni) {
		super();
		this.id = id;
		this.idRol = idRol;
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.provincia = provincia;
		this.localidad = localidad;
		this.telefono = telefono;
		this.dni = dni;
	}
	
	public Usuario(Long id, String email, String clave, String nombre, String direccion, String dni) {
		super();
		this.id = id;
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.direccion = direccion;
		this.dni = dni;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", idRol=" + idRol + ", email=" + email + ", clave=" + clave + ", nombre=" + nombre
				+ ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", direccion=" + direccion + ", provincia="
				+ provincia + ", localidad=" + localidad + ", telefono=" + telefono + ", dni=" + dni + "]";
	}
	

	
	
}
