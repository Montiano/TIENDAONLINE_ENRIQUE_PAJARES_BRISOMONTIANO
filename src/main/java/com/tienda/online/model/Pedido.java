package com.tienda.online.model;


import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name="pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date fecha;
	@Column(name = "metodo_pago")
	private String metodoPago;
	private String estado;
	@Column(name = "num_factura")
	private String numFactura;
	private Double total;
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<DetallePedido> detalle;
	
	public Pedido() {
		super();
	}

	public Pedido(Long id, Date fecha, String metodoPago, String estado, String numFactura,
			Double total) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.metodoPago = metodoPago;
		this.estado = estado;
		this.numFactura = numFactura;
		this.total = total;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fechaCreacion) {
		this.fecha = fechaCreacion;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DetallePedido> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetallePedido> detalle) {
		this.detalle = detalle;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + ", metodoPago=" + metodoPago + ", estado=" + estado
				+ ", numFactura=" + numFactura + ", total=" + total + "]";
	}





	
	
}
