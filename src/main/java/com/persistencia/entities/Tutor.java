package com.persistencia.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Tutor
 *
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="ID_USUARIO")
public class Tutor extends Usuario implements Serializable {

	public Tutor() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	

	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TUTOR_SEC" )
	@SequenceGenerator(name = "TUTOR_SEC", initialValue = 1, allocationSize = 1)
	@Column(name="ID_TUTOR")
	private Long idTutor;
	

	@ManyToOne(optional=false)
	@JoinColumn(name="ID_AREA_TUTOR")
	private AreaTutor areaTutor;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="ID_TIPO_TUTOR")
	private TipoTutor tipoTutor;


	@ManyToMany( mappedBy="tutores")
	 private Set <Evento> eventos;


	public AreaTutor getAreaTutor() {
		return areaTutor;
	}

	public void setAreaTutor(AreaTutor areaTutor) {
		this.areaTutor = areaTutor;
	}

	public TipoTutor getTipoTutor() {
		return tipoTutor;
	}

	public void setTipoTutor(TipoTutor tipoTutor) {
		this.tipoTutor = tipoTutor;
	}


	
	
	
   
}
