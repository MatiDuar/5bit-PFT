package com.persistencia.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MATERIAS")
public class Materia implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	public Materia() {
		super();
	} 
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MATERIA_SEQ" )
	@SequenceGenerator(name = "MATERIA_SEQ", sequenceName = "MATERIA_SEQ", allocationSize = 1)
	@Column(name="ID_MATERIA")
	private Long id;
	
	@Column (name="NOMBRE",nullable=false, length=150,unique=true)
	private String nombre;
	
	@ManyToMany( mappedBy="materias")
	private List <Carrera> carreras;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
   
}
