package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="TIPO_ASIGNATURA")
public class TipoAsignatura implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	public TipoAsignatura() {
		super();
	} 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_ASIGNATURA_SEC" )
	@SequenceGenerator(name = "TIPO_ASIGNATURA_SEC", sequenceName = "TIPO_ASIGNATURA_SEC", allocationSize = 1)
	@Column(name="ID_TIPO_ASIGNATURA")
	private Long id;

	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;
	
	@Column(name="NOMBRE",nullable=false)
	private String nombre;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "TipoAsignatura [id=" + id + ", activo=" + activo + ", nombre=" + nombre + "]";
	}
	
	
	
	
	
	
	

	
   
}
