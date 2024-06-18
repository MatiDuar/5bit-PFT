package com.persistencia.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


 
@Entity
public class EstadosEventos implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	public EstadosEventos() {
		super();
	} 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTADO_EVENTO_SEC" )
	@SequenceGenerator(name = "ESTADO_EVENTO_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_ESTADO")
	private Long id_estado;
	
	@Column(name="NOMBRE",nullable=false,length=20)
	private String nombreEstadoEvento;
	
	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;

	public Long getId() {
		return id_estado;
	}

	public void setId(Long id_estado) {
		this.id_estado = id_estado;
	}

	public String getNombre() {
		return nombreEstadoEvento;
	}

	public void setNombre(String nombre) {
		this.nombreEstadoEvento = nombre;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Estado [id_estado=" + id_estado + ", nombreEstadoEvento=" + nombreEstadoEvento + ", activo=" + activo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activo, id_estado, nombreEstadoEvento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadosEventos other = (EstadosEventos) obj;
		return Objects.equals(activo, other.activo) && Objects.equals(id_estado, other.id_estado)
				&& Objects.equals(nombreEstadoEvento, other.nombreEstadoEvento);
	}
	
   
}
