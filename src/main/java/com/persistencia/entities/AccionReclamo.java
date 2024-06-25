package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="ACCIONES_RECLAMOS")
public class AccionReclamo implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCION_RECLAMO_SEC" )
	@SequenceGenerator(name = "ACCION_RECLAMO_SEC", sequenceName = "ACCION_RECLAMO_SEC", allocationSize = 1)
	@Column(name="ID_ACCION_RECLAMO")
	private Long id;
	
	@Column(name="FECHA_HORA",nullable=false)
	private Timestamp fechaHoraReclamo;
	
	@Column(name="DETALLE",nullable=false, length=150)
	private String detalleReclamo;
	
	@ManyToOne
	@JoinColumn(name="ID_RECLAMO")
	private Reclamo reclamo;
	@ManyToOne
	@JoinColumn(name="ID_ANALISTA")
	private Analista analista;
	
	public AccionReclamo() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getFechaHoraReclamo() {
		return fechaHoraReclamo;
	}
	public void setFechaHoraReclamo(Timestamp fechaHoraReclamo) {
		this.fechaHoraReclamo = fechaHoraReclamo;
	}
	public String getDetalleReclamo() {
		return detalleReclamo;
	}
	public void setDetalleReclamo(String detalleReclamo) {
		this.detalleReclamo = detalleReclamo;
	}
	public Reclamo getReclamo() {
		return reclamo;
	}
	public void setReclamo(Reclamo reclamo) {
		this.reclamo = reclamo;
	}
	public Analista getAnalista() {
		return analista;
	}
	public void setAnalista(Analista analista) {
		this.analista = analista;
	}
	@Override
	public String toString() {
		return "AccionReclamo [id=" + id + ", fechaHoraReclamo=" + fechaHoraReclamo
				+ ", detalleReclamo=" + detalleReclamo + ", reclamo=" + reclamo + ", analista=" + analista + "]";
	}
	
   
}
