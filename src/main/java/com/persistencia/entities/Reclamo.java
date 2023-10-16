package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECLAMO")
	@SequenceGenerator(name = "SEQ_RECLAMO", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String titulo;

	@Column(nullable = true)
	private Timestamp fechaHora;

	@Column(nullable = false)
	private String detalle;

	@ManyToOne(optional = false)
	private Estudiante estudiante;

	@ManyToOne(optional = false)
	private Estado estado;
	
	@Column(nullable = true)
	private String nombreEventoVME;
	
	@Column(nullable = true)
	private String nombreActividad;
	
	@Column(nullable = true)
	private int semestre;
	
	@Column(nullable = true)
	private Date fechaEvento;
	
	@Column(nullable = true)
	private String nombreDocente;
	
	@Column(nullable = true)
	private int creditos;


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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombreEventoVME() {
		return nombreEventoVME;
	}

	public void setNombreEventoVME(String nombreEventoVME) {
		this.nombreEventoVME = nombreEventoVME;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	@Override
	public String toString() {
		return "Reclamo [id=" + id + ", titulo=" + titulo + ", fechaHora=" + fechaHora + ", detalle=" + detalle
				+ ", estudiante=" + estudiante + ", estado=" + estado + ", nombreEventoVME="
				+ nombreEventoVME + ", nombreActividad=" + nombreActividad + ", semestre=" + semestre + ", fechaEvento="
				+ fechaEvento + ", nombreDocente=" + nombreDocente + ", creditos=" + creditos + "]";
	}

	

}
