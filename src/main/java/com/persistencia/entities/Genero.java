package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="GENEROS")
public class Genero implements Serializable {
	
	public Genero() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENERO_SEC" )
	@SequenceGenerator(name = "GENERO_SEC", sequenceName = "GENERO_SEC", allocationSize = 1)
	@Column(name="ID_GENERO")
	private Long id;
	
	@Column(name="NOMBRE",nullable=false,length=50,unique=true)
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
		return "Genero [id=" + id + ", nombre=" + nombre + ", activo=" + activo + "]";
	}
	
	
	
	
   
}
