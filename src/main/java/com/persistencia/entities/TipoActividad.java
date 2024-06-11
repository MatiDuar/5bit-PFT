package com.persistencia.entities;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


@Entity
public class TipoActividad implements Serializable {

	public TipoActividad() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_ACTIVIDAD" )
	@SequenceGenerator(name = "SEQ_TIPO_ACTIVIDAD", initialValue = 1, allocationSize = 1)
	@Column(name="ID_TIPO_ACTIVIDAD")
	private Long id;
	
	@Column(name="DESCRIPCION",nullable=false,length=50,unique=true)
	private String nombre;
	
	@Column(name="ES_CALIFICADO",nullable=false)
	private Boolean esCalificado;
	
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

	public Boolean getEsCalificado() {
		return esCalificado;
	}

	public void setEsCalificado(Boolean esCalificado) {
		this.esCalificado = esCalificado;
	}

	@Override
	public String toString() {
		return "TipoActividad [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(esCalificado, id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoActividad other = (TipoActividad) obj;
		return Objects.equals(esCalificado, other.esCalificado) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre);
	}
	
	
	
	
   
}
