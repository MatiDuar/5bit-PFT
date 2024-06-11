package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class Evento implements Serializable {

	public Evento() {
		super();
	}

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENTO" )
	@SequenceGenerator(name = "SEQ_EVENTO", initialValue = 1, allocationSize = 1)
	@Column(name="ID_EVENTO")
	private Long id;

	@Column(name="FECHA_INIC",nullable = false)
	private Timestamp FechaInicio;

	@Column(name="FECHA_FIN",nullable = false)
	private Timestamp FechaFin;

	@Column(name="TITULO",nullable = false, length = 50)
	private String titulo;

	@ManyToOne
	@JoinColumn(name="ID_TIPO_ACTIVIDAD")
	private TipoActividad tipoActividad;
	
	@ManyToOne
	@JoinColumn(name="ID_MODALIDAD")
	private ModalidadesEventos modalidad;

	@Column(name="CREDITOS",nullable = true,length = 2)
	private String creditos;

	@Column(name="SEMESTRE",nullable = true,length = 2)
	private String semestre;
	
	@Column(name="LOCACION")
	private String localizacion;
	
	@ManyToOne
	@JoinColumn(name="ID_ITR")
	private ITR itr;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private EstadosEventos estado;

	@JoinTable(name = "RESP_TUTOR_EVENTO", joinColumns = @JoinColumn(name = "ID_EVENTO", nullable = false), 
			inverseJoinColumns = @JoinColumn(name = "ID_TUTOR", nullable = true))
	@ManyToMany( fetch = FetchType.EAGER)

	private List<Tutor> tutores;

	public void addTutor(Tutor tutor) {
		if (this.tutores == null) {
			this.tutores = new ArrayList<>();
		}

		this.tutores.add(tutor); 
	}

	@JoinTable(name = "ANALIST_GEST_EVENTOS", 
			joinColumns = @JoinColumn(name = "ID_EVENTO", nullable = false), 
			inverseJoinColumns =@JoinColumn(name = "ID_ANALISTA", nullable = true))
	@ManyToMany( fetch = FetchType.EAGER)

	private Set<Analista> analistas;

	public void addAnalista(Analista analista) {
		if (this.analistas == null) {
			this.analistas = new HashSet<>();
		}

		this.analistas.add(analista);
	}



	public List<Tutor> getTutores() {
		return tutores;
	}



	public void setTutores(List<Tutor> tutores) {
		this.tutores = tutores;
	}



	public Set<Analista> getAnalistas() {
		return analistas;
	}



	public void setAnalistas(Set<Analista> analistas) {
		this.analistas = analistas;
	}
	
	public String getLocalizacion() {
		return localizacion;
	}



	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getFechaInicio() {
		return FechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		FechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return FechaFin;
	}

	public void setFechaFin(Timestamp  fechaFin) {
		FechaFin = fechaFin;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	

	
	
	public String getCreditos() {
		return creditos;
	}



	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}



	public String getSemestre() {
		return semestre;
	}



	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}



	public ITR getItr() {
		return itr;
	}



	public void setItr(ITR itr) {
		this.itr = itr;
	}



	public ModalidadesEventos getModalidad() {
		return modalidad;
	}



	public void setModalidad(ModalidadesEventos modalidad) {
		this.modalidad = modalidad;
	}

	public EstadosEventos getEstado() {
		return estado;
	}



	public void setEstado(EstadosEventos estado) {
		this.estado = estado;
	}



	@Override
	public String toString() {
		return "Evento [id=" + id + ", FechaInicio=" + FechaInicio + ", FechaFin=" + FechaFin + ", titulo=" + titulo
				+ ", tipoActividad=" + tipoActividad + ", creditos=" + creditos + ", semestre=" + semestre + "tutores:"+tutores+"]";
	}

}
