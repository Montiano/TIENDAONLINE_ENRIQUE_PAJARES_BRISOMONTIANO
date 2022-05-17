package com.tienda.online.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@Email
	@NotNull
	
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String nombre;
	@NotNull
	private String apellido1;
	
	private String apellido2;
	@NotNull
	private String direccion;
	@NotNull
	private String provincia;
	@NotNull
	private String localidad;
	
	private String telefono;
	@NotNull
	private String dni;
	
	private String tipo;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Producto> productos;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Pedido> pedidos;
	
	@ManyToOne
	private Rol rol;
	
	public Usuario() {
		super();
	}
	public Usuario(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	
	public Usuario(Long id, @Email @NotNull String email, String password, String nombre, String apellido1,
			String apellido2, String direccion, String provincia, String localidad, String telefono, String dni,
			String tipo, List<Producto> productos, List<Pedido> pedidos, Rol rol) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.provincia = provincia;
		this.localidad = localidad;
		this.telefono = telefono;
		this.dni = dni;
		this.tipo = tipo;
		this.productos = productos;
		this.pedidos = pedidos;
		this.rol = rol;
	}
	public Usuario(Long id, String email, String password, String nombre, String direccion, String dni) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", password=" + password + ", nombre=" + nombre
				+ ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", direccion=" + direccion + ", provincia="
				+ provincia + ", localidad=" + localidad + ", telefono=" + telefono + ", dni=" + dni + ", tipo=" + tipo
				+ "]";
	}

	
	
	
	
	
}
