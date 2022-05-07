package com.tienda.online.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "id_pedido")
	private int idPedido;
	@Column(name = "id_producto")
	private int idProducto;
	@Column(name = "precio_unidad")
	private float precioUnidad;
	private int unidades;
	private float impuesto;
	private double total;
	
	@OneToOne
	private Pedido pedido;
	
	@ManyToOne
	private Producto producto;
	
	
	public DetallePedido() {
		super();
	}

	public DetallePedido(Long id, int idPedido, int idProducto, float precioUnidad, int unidades, float impuesto,
			double total) {
		super();
		this.id = id;
		this.idPedido = idPedido;
		this.idProducto = idProducto;
		this.precioUnidad = precioUnidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public float getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(float precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
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

	@Override
	public String toString() {
		return "DetallePedido [id=" + id + ", idPedido=" + idPedido + ", idProducto=" + idProducto + ", precioUnidad="
				+ precioUnidad + ", unidades=" + unidades + ", impuesto=" + impuesto + ", total=" + total + "]";
	}
	
	
}
