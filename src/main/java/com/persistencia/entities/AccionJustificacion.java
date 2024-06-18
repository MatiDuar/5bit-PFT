package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


public class AccionJustificacion implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCION_JUSTIFICACION_SEC" )
	@SequenceGenerator(name = "ACCION_JUSTIFICACION_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_ACCION_JUSTIFICACION")
	private Long id;
	
	@Column(name="FECHA_HORA",nullable=false)
	private Date fechaHora;
	
	@Column(name="DETALLE",nullable=false, length=150)
	private String detalle;
	

	@ManyToOne
	@JoinColumn(name="ID_JUSTIFICACION")
	private Justificacion justificacion;
	
	@ManyToOne
	@JoinColumn(name="ID_ANALISTA")
	private Analista analista;
	
	public AccionJustificacion() {
		super();
	}


	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Justificacion getReclamo() {
		return justificacion;
	}

	public void setJustificacion(Justificacion justificacion) {
		this.justificacion = justificacion;
	}

	public Analista getAnalista() {
		return analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	@Override
	public String toString() {

		return "AccionJustificacion [id=" + id + ", fechaHora=" + fechaHora + ", detalle=" + detalle + ", Justificacion="
				+ justificacion + ", analista=" + analista + "]";
	}

}
