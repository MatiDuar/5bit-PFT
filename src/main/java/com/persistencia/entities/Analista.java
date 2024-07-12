package com.persistencia.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name="ANALISTAS")
@PrimaryKeyJoinColumn(referencedColumnName="ID_USUARIO")
@SequenceGenerator(name = "ANALISTA_SEC", sequenceName = "ANALISTA_SEC", allocationSize = 1)
public class Analista extends Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	public Analista() {
		super();
	}
	
	
	 @Column(name="ID_ANALISTA")
	 private long idAnalista;
	
	
	 @ManyToMany(mappedBy="analistas")
	 private Set <Evento> eventos;


	public long getIdAnalista() {
		return idAnalista;
	}


	public void setIdAnalista(long idAnalista) {
		this.idAnalista = idAnalista;
	}

	
	 
   
}
