package com.persistencia.dto;

import java.sql.Date;

public class EscolaridadDTO {
	
private Integer idEstudiante;
private String nombre1;
private String apellido1;
private Integer eventoId;
private Integer calificacion;
private String tituloEvento;
private Integer creditos;
private Date fechaInicio;
private Date fechaFin;
private String semestre;
private String modalidad;
private String nombreItr;




public EscolaridadDTO(Integer idEstudiante, String nombre1, String apellido1, Integer eventoId, Integer calificacion,
		String tituloEvento, Integer creditos, Date fechaInicio, Date fechaFin, String semestre, String modalidad,
		String nombreItr) {
	super();
	this.idEstudiante = idEstudiante;
	this.nombre1 = nombre1;
	this.apellido1 = apellido1;
	this.eventoId = eventoId;
	this.calificacion = calificacion;
	this.tituloEvento = tituloEvento;
	this.creditos = creditos;
	this.fechaInicio = fechaInicio;
	this.fechaFin = fechaFin;
	this.semestre = semestre;
	this.modalidad = modalidad;
	this.nombreItr = nombreItr;
}


public EscolaridadDTO() {
	super();
}


public Integer getIdEstudiante() {
	return idEstudiante;
}
public void setIdEstudiante(Integer idEstudiante) {
	this.idEstudiante = idEstudiante;
}
public String getNombre1() {
	return nombre1;
}
public void setNombre1(String nombre1) {
	this.nombre1 = nombre1;
}
public String getApellido1() {
	return apellido1;
}
public void setApellido1(String apellido1) {
	this.apellido1 = apellido1;
}
public Integer getEventoId() {
	return eventoId;
}
public void setEventoId(Integer eventoId) {
	this.eventoId = eventoId;
}
public Integer getCalificacion() {
	return calificacion;
}
public void setCalificacion(Integer calificacion) {
	this.calificacion = calificacion;
}
public String getTituloEvento() {
	return tituloEvento;
}
public void setTituloEvento(String tituloEvento) {
	this.tituloEvento = tituloEvento;
}

public Integer getCreditos() {
	return creditos;
}

public void setCreditos(Integer creditos) {
	this.creditos = creditos;
}

public Date getFechaInicio() {
	return fechaInicio;
}
public void setFechaInicio(Date fechaInicio) {
	this.fechaInicio = fechaInicio;
}
public Date getFechaFin() {
	return fechaFin;
}
public void setFechaFin(Date fechaFin) {
	this.fechaFin = fechaFin;
}
public String getSemestre() {
	return semestre;
}
public void setSemestre(String semestre) {
	this.semestre = semestre;
}
public String getModalidad() {
	return modalidad;
}
public void setModalidad(String modalidad) {
	this.modalidad = modalidad;
}
public String getNombreItr() {
	return nombreItr;
}
public void setNombreItr(String nombreItr) {
	this.nombreItr = nombreItr;
}


@Override
public String toString() {
	return "escolaridadDTO [idEstudiante=" + idEstudiante + ", nombre1=" + nombre1 + ", apellido1=" + apellido1
			+ ", eventoId=" + eventoId + ", calificacion=" + calificacion + ", tituloEvento=" + tituloEvento
			+ ", Creditos=" + creditos + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", semestre="
			+ semestre + ", modalidad=" + modalidad + ", nombreItr=" + nombreItr + "]";
}



}
