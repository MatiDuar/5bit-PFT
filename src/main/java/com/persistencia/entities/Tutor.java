package com.persistencia.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "TUTORES")
@PrimaryKeyJoinColumn(referencedColumnName="ID_USUARIO")
public class Tutor extends Usuario implements Serializable {

	public Tutor() {
		super();
	} 
	
	private static final long serialVersionUID = 1L;	

	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TUTOR_SEC")
    @SequenceGenerator(name = "TUTOR_SEC", sequenceName = "TUTOR_SEC", allocationSize = 1)
	@Column(name="ID_TUTOR")
	private Long idTutor;
	

	@ManyToOne(optional=false)
	@JoinColumn(name="ID_AREA_TUTOR")
	private AreaTutor areaTutor;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="ID_TIPO_TUTOR")
	private TipoTutor tipoTutor;


	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "RESP_TUTORES_EVENTOS", 
               joinColumns = @JoinColumn(name = "ID_TUTOR", nullable = false), 
               inverseJoinColumns = @JoinColumn(name = "ID_EVENTO", nullable = false))
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

	public Long getIdTutor() {
		return idTutor;
	}

	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}

	public Set<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + getId()+" ]" +" Tutor [idTutor=" + idTutor + ", areaTutor=" + areaTutor + ", tipoTutor=" + tipoTutor+"]";
	}


   
}
