package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;


public class TipoConvocatoriaMatricula implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	public TipoConvocatoriaMatricula() {
		super();
	} 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_CONVOCATORIA_MATRICULA_SEC" )
	@SequenceGenerator(name = "TIPO_CONVOCATORIA_MATRICULA_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_TIPO_CONVOCATORIA_MATRICULA")
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
		return "TipoConvocatoriaMatricula [id=" + id + ", activo=" + activo + ", nombre=" + nombre + "]";
	}

	
	
   
}
