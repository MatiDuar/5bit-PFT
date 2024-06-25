package com.persistencia.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="PLANTILLAS_CONSTANCIAS")
public class PlantillaConstancia implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANTILLA_COSTANCIA_SEC" )
	@SequenceGenerator(name = "PLANTILLA_COSTANCIA_SEC", sequenceName = "PLANTILLA_COSTANCIA_SEC", allocationSize = 1)
	@Column(name="ID_PLANTILLA")
	private Long id;
	
	@Column(name="NOMBRE",nullable=false,length=50)
	private String nombre;
	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;
	
	public PlantillaConstancia() {
		super();
	} 
	
   
}
