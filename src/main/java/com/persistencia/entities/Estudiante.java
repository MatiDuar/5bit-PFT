package com.persistencia.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="ESTUDIANTES")
@PrimaryKeyJoinColumn(referencedColumnName="ID_USUARIO")
@SequenceGenerator(name = "ESTUDIANTE_SEC", initialValue = 1, allocationSize = 1)
public class Estudiante extends Usuario implements Serializable {
	
	public Estudiante() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	
	
	
	@Column(name="ANO_INGRESO")
	private int anoIngreso;
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="ID_ESTUDIANTE")
	Set<Inscripcion> inscripciones;
	public void addInscripcion(Inscripcion inscripcion) {
		if (this.inscripciones == null) {
			this.inscripciones = new HashSet<>();
		}

		this.inscripciones.add(inscripcion);
	}

	public int getAnoIngreso() {
		return anoIngreso;
	}

	public void setAnoIngreso(int anoIngreso) {
		this.anoIngreso = anoIngreso;
	}
	public Set<Inscripcion> getInscripciones() {
		return inscripciones;
	}
	public void setInscripciones(Set<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}
	



	
	
	
   
}
