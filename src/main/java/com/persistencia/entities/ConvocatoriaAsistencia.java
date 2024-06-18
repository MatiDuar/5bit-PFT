package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;


public class ConvocatoriaAsistencia implements Serializable {

	

private static final long serialVersionUID = 1L;	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONVOCATORIA_ASISTENCIA_SEC" )
	@SequenceGenerator(name = "CONVOCATORIA_ASISTENCIA_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_ASISTENCIA")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTUDIANTE")
	private Estudiante estudiante;
	
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;
	
	@Column (name="CALIFICACION",nullable=false)
	private float calificacion;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTADO_ASISTENCIA")
	private EstadoAsistencia estadoAsistencia;
	
	public ConvocatoriaAsistencia() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long idAsistencia) {
		this.id = idAsistencia;
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

	public float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(float calificacion) {
		this.calificacion = calificacion;
	}

	public EstadoAsistencia getEstadoAsistencia() {
		return estadoAsistencia;
	}

	public void setEstadoAsistencia(EstadoAsistencia estadoAsistencia) {
		this.estadoAsistencia = estadoAsistencia;
	}

	@Override
	public String toString() {
		return "ConvocatoriaAsistencia [id=" + id + ", estudiante=" + estudiante + ", evento="
				+ evento + ", calificacion=" + calificacion + ", estadoAsistencia=" + estadoAsistencia + "]";
	}
	
   
}
