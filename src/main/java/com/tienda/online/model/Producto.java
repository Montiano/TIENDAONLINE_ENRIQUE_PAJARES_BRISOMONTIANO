package com.tienda.online.model;



import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
	private String categoria;
	private double precio;
	private int stock;
	@Column(name="fecha_alta")
	private Date fechaAlta;
	@Column(name="fecha_baja")
	private Date fechaBaja;
	private float impuesto;
	private String imagen;
	
	@ManyToOne
	private Usuario usuario;
	
//	@ManyToOne
//	private Categoria categoria;
	
	@OneToMany(mappedBy = "producto", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DetallePedido> detalle;
	
	
	public Producto() {
		super();
	}
	public Producto(Producto producto) {
		super();
	}
		

	public Producto(Long id, String nombre, String descripcion, String categoria, double precio, int stock,
			Date fechaAlta, Date fechaBaja, float impuesto, String imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoria = categoria;
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
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
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
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", categoria=" + categoria
				+ ", precio=" + precio + ", stock=" + stock + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja
				+ ", impuesto=" + impuesto + ", imagen=" + imagen + "]";
	}

	

	
	
	

	
	
	
}
