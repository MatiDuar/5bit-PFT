package com.persistencia.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


@Entity
public class ITR implements Serializable {

	public ITR() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITR_SEC" )
	@SequenceGenerator(name = "ITR_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_ITR")
	private Long id;
	
	@Column(name="NOMBRE",length=50,nullable=false,unique=true)
	private String nombre;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="ID_DEPARTAMENTO")
	private Departamento departamento;
	
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

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "ITR [id=" + id + ", nombre=" + nombre + ", departamento=" + departamento + ", activo=" + activo + "]";
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
		ITR other = (ITR) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
   
}
