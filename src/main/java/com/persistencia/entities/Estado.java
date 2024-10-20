package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="ESTADOS")
public class Estado implements Serializable {
	
	public Estado() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTADO_SEC" )
	@SequenceGenerator(name = "ESTADO_SEC", sequenceName = "ESTADO_SEC", allocationSize = 1)
	@Column(name="ID_ESTADO")
	private Long id;
	
	@Column(name="DESCRIPCION",nullable=false,length=50)
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
		return "Estado [id=" + id + ", nombre=" + nombre + ", activo=" + activo + "]";
	}
	
	
   
}
