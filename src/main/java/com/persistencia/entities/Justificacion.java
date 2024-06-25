package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.*;


@Entity
@Table(name="JUSTIFICACIONES")
public class Justificacion implements Serializable {

	public Justificacion() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JUSTIFICACION_SEC" )
	@SequenceGenerator(name = "JUSTIFICACION_SEC", sequenceName = "JUSTIFICACION_SEC", allocationSize = 1)
	@Column(name="ID_JUSTIFICACION")
	private Long id;
	
	@Column(name="FECHA_HORA",nullable=false)
	private Timestamp fechaHora;
	
	@Column(name="DETALLE",nullable=false,length=150)
	private String detalle;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="ID_ESTUDIANTE")
	private Estudiante estudiante;

	@ManyToOne(optional=false)
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
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

	@Override
	public String toString() {
		return "Justificacion [id=" + id + ", fechaHora=" + fechaHora + ", estudiante=" + estudiante + ", evento="
				+ evento + ", estado=" + estado + "]";
	}
	
	
	
	
   
}
