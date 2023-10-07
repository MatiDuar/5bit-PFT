package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
public class Reclamo implements Serializable {

	public Reclamo() {
		super();
	} 
	private static final long serialVersionUID = 1L;	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECLAMO" )
	@SequenceGenerator(name = "SEQ_RECLAMO", initialValue = 1, allocationSize = 1)
	private Long id;
	
	
	@Column(nullable=true)
	private Timestamp fechaHora;
	
	@Column(nullable=false)
	private String detalle;
	
	@ManyToOne(optional=false)
	private Estudiante estudiante;

	@ManyToOne(optional=false)
	private Evento evento;
	
	@ManyToOne(optional=false)
	private Estado estado;

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
	
	

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	@Override
	public String toString() {
		return "Reclamo [id=" + id + ", fechaHora=" + fechaHora + ", estudiante=" + estudiante + ", evento=" + evento
				+ ", estado=" + estado + "]";
	}
	
	
	
	
   
}
