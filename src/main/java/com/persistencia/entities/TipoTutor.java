package com.persistencia.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


@Entity
public class TipoTutor implements Serializable {

	public TipoTutor() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_TUTOR" )
	@SequenceGenerator(name = "SEQ_TIPO_TUTOR", initialValue = 1, allocationSize = 1)
	@Column(name="ID_TIPO_TUTOR")
	private Long id;
	
	@Column(nullable=false,length=50,unique=true)
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
		return "TipoTutor [id=" + id + ", nombre=" + nombre + "]";
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
		TipoTutor other = (TipoTutor) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
   
}
