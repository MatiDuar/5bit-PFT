package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
public class TipoConstancia implements Serializable {

	public TipoConstancia() {
		super();
	} 
	private static final long serialVersionUID = 1L;	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_CONSTANCIA_SEC" )
	@SequenceGenerator(name = "TIPO_CONSTANCIA_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_TIPO_CONSTANCIA")
	private Long id;
	
	@Column(name="DESCRIPCION",nullable=false,length=50)
	private String nombre;
	
	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;
	
	@ManyToOne
	@JoinColumn(name="ID_PLANTILLA")
	private PlantillaConstancia plantillaConstancia;

	public PlantillaConstancia getPlantillaConstancia() {
		return plantillaConstancia;
	}

	public void setPlantillaConstancia(PlantillaConstancia plantillaConstancia) {
		this.plantillaConstancia = plantillaConstancia;
	}

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
		return "TipoConstancia [id=" + id + ", nombre=" + nombre + ", activo=" + activo + "]";
	}
	
	
   
}
