package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="MATRICULAS")
public class Matricula implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	public Matricula() {
		super();
	} 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MATRICULA_SEC" )
	@SequenceGenerator(name = "MATRICULA_SEC", sequenceName = "MATRICULA_SEC", allocationSize = 1)
	@Column(name="ID_MATRICULA")
	private Long id;
	

	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;
	
	@Column(name="NOTA",nullable=true)
	private float nota;	
	
	@Column(name="CREDITOS",nullable=false)
	private int creditos;	
	
	@Column(name="SEMESTRE",nullable=false)
	private int semestre;	

	@Column(name="FECHA_INSCRIPCION",nullable=false)
	private Date fecha_inscripcion;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_MATERIA")
	private Materia materia;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_INSCRIPCION")
	private Inscripcion inscripcion;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_TIPO_ASIGNATURA")
	private TipoAsignatura tipoAsignatura;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_TIPO_CONVOCATORIA_MATRICULA")
	private TipoConvocatoriaMatricula convocatoriaTipo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public Date getFecha_inscripcion() {
		return fecha_inscripcion;
	}

	public void setFecha_inscripcion(Date fecha_inscripcion) {
		this.fecha_inscripcion = fecha_inscripcion;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	

	
	public Inscripcion getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	
	

	public TipoAsignatura getTipoAsignatura() {
		return tipoAsignatura;
	}

	public void setTipoAsignatura(TipoAsignatura tipoAsignatura) {
		this.tipoAsignatura = tipoAsignatura;
	}

	public TipoConvocatoriaMatricula getConvocatoriaTipo() {
		return convocatoriaTipo;
	}

	public void setConvocatoriaTipo(TipoConvocatoriaMatricula convocatoriaTipo) {
		this.convocatoriaTipo = convocatoriaTipo;
	}

	@Override
	public String toString() {
		return "Matricula [id=" + id + ", activo=" + activo + ", nota=" + nota + ", creditos=" + creditos
				+ ", semestre=" + semestre + ", fecha_inscripcion=" + fecha_inscripcion + ", materia=" + materia
				+ ", inscripcion=" + inscripcion + ", tipoAsignatura=" + tipoAsignatura + ", convocatoriaTipo="
				+ convocatoriaTipo + "]";
	}



	
	
	
	
	
	
   
}
