package com.persistencia.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


@Entity
public class Departamento implements Serializable {

	public Departamento() {
		super();
	} 
	private static final long serialVersionUID = 1L;	
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTO_SEC" )
	@SequenceGenerator(name = "DEPARTAMENTO_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_DEPARTAMENTO")
	private Long id;
	
	@Column(name="DESCRIPCION",nullable=false,length=50,unique=true)
	private String nombre;
	
	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;

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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nombre=" + nombre + ", activo=" + activo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		return Objects.equals(id, other.id);
	}
	
	
   
}
