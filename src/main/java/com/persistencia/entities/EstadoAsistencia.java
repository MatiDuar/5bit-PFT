package com.persistencia.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


@Entity
@Table(name="ESTADOS_ASISTENCIAS")
public class EstadoAsistencia implements Serializable {

	public EstadoAsistencia() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTADO_ASISTENCIA_SEC" )
	@SequenceGenerator(name = "ESTADO_ASISTENCIA_SEC", sequenceName = "ESTADO_ASISTENCIA_SEC", allocationSize = 1)
	@Column(name="ID_ESTADO_ASISTENCIA")
	private Long id;
	
	@Column(name="DESCRIPCION",nullable=false,length=50)
	private String nombre;

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

	@Override
	public String toString() {
		return "EstadoAsistencia [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoAsistencia other = (EstadoAsistencia) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}
	
	
	
	
   
}
