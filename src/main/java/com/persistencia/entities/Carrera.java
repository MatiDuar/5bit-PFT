package com.persistencia.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


public class Carrera implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	public Carrera() {
		super();
	} 

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARRERA" )
	@SequenceGenerator(name = "SEQ_CARRERA", initialValue = 1, allocationSize = 1)
	@Column(name="ID_CARRERA")
	private Long id;
	
	@Column(name="NOMBRE",nullable=false,length=50,unique=true)
	private String nombre;
	
	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;
	
	@Column(name="CANT_SEMESTRE",nullable=false)
	private int cantSemestre;
	
	@Column(name="CREDITOS_OBLIGATORIOS",nullable=false)
	private int creditosObligatorios;
	
	@Column(name="CREDITOS_OPTATIVOS",nullable=false)
	private int creditosOptativos;
	@Column(name="CREDITOS_LIBRE_CONFIGURACION",nullable=false)
	private int creditosLibreConfiguracion;
	@Column(name="CREDITOS_PROYECTO",nullable=false)
	private int creditosProyecto;
	@Column(name="CREDITOS_PRACTICAS_PROFESIONALES",nullable=false)
	private int creditosPracticasProfesionales;
	
	
	@JoinTable(name = "CARRERA_MATERIA", joinColumns = @JoinColumn(name = "ID_CARRERA", nullable = false), 
			inverseJoinColumns = @JoinColumn(name = "ID_MATERIA", nullable = false))
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Materia> materias;

	public void addMateria(Materia materia) {
		if (this.materias == null) {
			this.materias = new ArrayList<>();
		}

		this.materias.add(materia);
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public int getCantSemestre() {
		return cantSemestre;
	}

	public void setCantSemestre(int cantSemestre) {
		this.cantSemestre = cantSemestre;
	}

	public int getCreditosObligatorios() {
		return creditosObligatorios;
	}


	public void setCreditosObligatorios(int creditosObligatorios) {
		this.creditosObligatorios = creditosObligatorios;
	}


	public int getCreditosOptativos() {
		return creditosOptativos;
	}


	public void setCreditosOptativos(int creditosOptativos) {
		this.creditosOptativos = creditosOptativos;
	}


	public int getCreditosLibreConfiguracion() {
		return creditosLibreConfiguracion;
	}


	public void setCreditosLibreConfiguracion(int creditosLibreConfiguracion) {
		this.creditosLibreConfiguracion = creditosLibreConfiguracion;
	}


	public int getCreditosProyecto() {
		return creditosProyecto;
	}


	public void setCreditosProyecto(int creditosProyecto) {
		this.creditosProyecto = creditosProyecto;
	}


	public int getCreditosPracticasProfesionales() {
		return creditosPracticasProfesionales;
	}


	public void setCreditosPracticasProfesionales(int creditosPracticasProfecionales) {
		this.creditosPracticasProfesionales = creditosPracticasProfecionales;
	}


	public List<Materia> getMaterias() {
		return materias;
	}


	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}


	@Override
	public String toString() {
		return "Carrera [id=" + id + ", nombre=" + nombre + ", activo=" + activo + ", cantSemestre=" + cantSemestre
				+ ", creditosObligatorios=" + creditosObligatorios + ", creditosOptativos=" + creditosOptativos
				+ ", creditosLibreConfiguracion=" + creditosLibreConfiguracion + ", creditosProyecto="
				+ creditosProyecto + ", creditosPracticasProfecionales=" + creditosPracticasProfesionales
				+ ", materias=" + materias + "]";
	}

	
	
	
	

	
	
   
}
