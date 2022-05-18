package com.tienda.online.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "precio_unidad")
	private double precioUnidad;
	private int unidades;
	private double total;
	private String nombre;
	
	@ManyToOne
	private Pedido pedido;
	
	@ManyToOne
	private Producto producto;
	
	
	public DetallePedido() {
		super();
	}

	
	
	public DetallePedido(Long id, double precioUnidad, int unidades, double total, String nombre,
			Pedido pedido, Producto producto) {
		super();
		this.id = id;
		this.precioUnidad = precioUnidad;
		this.unidades = unidades;
		this.total = total;
		this.nombre = nombre;
		this.pedido = pedido;
		this.producto = producto;
	}



	public DetallePedido(Long id, double precioUnidad, int unidades, double total, String nombre) {
		super();
		this.id = id;
		this.precioUnidad = precioUnidad;
		this.unidades = unidades;
		this.total = total;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(double precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	@Override
	public String toString() {
		return "DetallePedido [id=" + id + ", precioUnidad=" + precioUnidad + ", unidades=" + unidades + ", total=" + total + ", nombre=" + nombre + "]";
	}


	


	
	
}
