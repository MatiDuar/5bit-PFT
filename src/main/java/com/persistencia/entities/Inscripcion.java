package com.persistencia.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="INSCRIPCIONES")
public class Inscripcion implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSCRIPCION_SEQ" )
	@SequenceGenerator(name = "INSCRIPCION_SEQ", sequenceName = "INSCRIPCION_SEQ", allocationSize = 1)
	@Column(name="ID_INSCRIPCION")
	private Long id;

	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ID_ESTUDIANTE")
	private Estudiante estudiante;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ID_CARRERA")
	private Carrera carrera;


	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_INSCRIPCION")
	Set<Matricula>matriculas;
	
	public void addMatricula(Matricula matricula) {
		if (this.matriculas == null) {
			this.matriculas = new HashSet<>();
		}

		this.matriculas.add(matricula);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}


	public Set<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(Set<Matricula> matriculas) {
		this.matriculas = matriculas;
	}



	private static final long serialVersionUID = 1L;	
	public Inscripcion() {
		super();
	}

	@Override
	public String toString() {
		return "Inscripcion [id=" + id + ", activo=" + activo + ", estudiante=" + estudiante + ", carrera=" + carrera
				+ "]";
	} 
	

	
	
	

	
   
}
