package com.persistencia.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name="AREAS_TUTORES")
public class AreaTutor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_TUTOR_SEC")
	@SequenceGenerator(name = "AREA_TUTOR_SEC", sequenceName = "AREA_TUTOR_SEC", allocationSize = 1)
	@Column(name="ID_AREA_TUTOR")
	private Long id;

	@Column(name="DESCRIPCION",nullable = false, length = 150, unique = true)
	private String nombre;

	public AreaTutor() {
		super();
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

	@Override
	public String toString() {
		return "AreaTutor [id=" + id + ", Nombre=" + nombre + "]";
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
		AreaTutor other = (AreaTutor) obj;
		return Objects.equals(id, other.id);
	}

}
