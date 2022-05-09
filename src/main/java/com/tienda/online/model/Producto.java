package com.tienda.online.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="id_categoria")
	private int idCategoria;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	@Column(name="fecha_alta")
	private Timestamp fechaAlta;
	@Column(name="fecha_baja")
	private Timestamp fechaBaja;
	private float impuesto;
	private String imagen;
	
	@ManyToOne
	private Usuario usuario;
	
	public Producto() {
		super();
	}
	public Producto(Producto producto) {
		super();
	}
	
	public Producto(Long id, int idCategoria, String nombre, String descripcion, double precio, int stock,
			Timestamp fechaAlta, Timestamp fechaBaja, float impuesto, String imagen) {
		super();
		this.id = id;
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}
	
	public Producto(Long id, String nombre, String descripcion, double precio, int stock, String imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.imagen = imagen;
	}
	
	public Producto(Long id, int idCategoria, String nombre, String descripcion, double precio, int stock,
			Timestamp fechaAlta, Timestamp fechaBaja, float impuesto, String imagen, Usuario usuario) {
		super();
		this.id = id;
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
		this.impuesto = impuesto;
		this.imagen = imagen;
		this.usuario = usuario;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Timestamp getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Timestamp fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Timestamp getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Timestamp fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public float getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", idCategoria=" + idCategoria + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", precio=" + precio + ", stock=" + stock + ", fechaAlta=" + fechaAlta + ", fechaBaja="
				+ fechaBaja + ", impuesto=" + impuesto + ", imagen=" + imagen + "]";
	}
	
	
	
}
