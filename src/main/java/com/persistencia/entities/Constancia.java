package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


public class Constancia implements Serializable {

	
private static final long serialVersionUID = 1L;	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSTANCIA_SEC" )
	@SequenceGenerator(name = "CONSTANCIA_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_CONSTANCIA")
	private Long id;
	
	@Column(name="FECHA_HORA",nullable=false)
	private Date fechaHora;
	
	@Column (name="DETALLE",nullable=false, length=150)
	private String detalle;
	
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;
	
	@ManyToOne
	@JoinColumn(name="ID_TIPO_CONSTANCIA")
	private TipoConstancia tipoConstancia;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTUDIANTE")
	private Estudiante estudiante;
	
	public Constancia() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long idConstancia) {
		this.id = idConstancia;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public TipoConstancia getTipoConstancia() {
		return tipoConstancia;
	}

	public void setTipoConstancia(TipoConstancia tipoConstancia) {
		this.tipoConstancia = tipoConstancia;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	@Override
	public String toString() {
		return "Constancia [id=" + id + ", fechaHora=" + fechaHora + ", detalle=" + detalle
				+ ", evento=" + evento + ", estado=" + estado + ", tipoConstancia=" + tipoConstancia + "]";
	}
	
   
}
