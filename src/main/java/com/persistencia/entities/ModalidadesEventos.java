package com.persistencia.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


 
@Entity
public class ModalidadesEventos implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	public ModalidadesEventos() {
		super();
	} 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTADOS_MODALIDADES" )
	@SequenceGenerator(name = "SEQ_ESTADOS_MODALIDADES", initialValue = 1, allocationSize = 1)
	@Column(name="ID_MODALIDAD")
	private Long id_modalidad;
	
	@Column(name="NOMBRE",nullable=false,length=20)
	private String nombreModalidadEvento;
	
	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;

	public Long getId() {
		return id_modalidad;
	}

	public void setId(Long id_modalidad) {
		this.id_modalidad = id_modalidad;
	}

	public String getNombre() {
		return nombreModalidadEvento;
	}

	public void setNombre(String nombre) {
		this.nombreModalidadEvento = nombre;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Estado [id_modalidad=" + id_modalidad + ", nombreModalidadEvento=" + nombreModalidadEvento + ", activo=" + activo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activo, id_modalidad, nombreModalidadEvento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModalidadesEventos other = (ModalidadesEventos) obj;
		return Objects.equals(activo, other.activo) && Objects.equals(id_modalidad, other.id_modalidad)
				&& Objects.equals(nombreModalidadEvento, other.nombreModalidadEvento);
	}
	
	
   
}
