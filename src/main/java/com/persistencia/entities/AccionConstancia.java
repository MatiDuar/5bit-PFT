package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="ACCIONES_CONSTANCIA")
public class AccionConstancia implements Serializable {

	private static final long serialVersionUID = 1L;	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCION_CONSTANCIA_SEC" )
	@SequenceGenerator(name = "ACCION_CONSTANCIA_SEC", sequenceName = "TIPO_ACTIVIDAD_SEC", allocationSize = 1)
	@Column(name="ID_ACCION_CONSTANCIA")
	private Long id;
	
	@Column(name="FECHA_HORA",nullable=false)
	private Date fechaHora;
	
	@Column (name="DETALLE",nullable=false, length=150)
	private String detalle;
	
	@ManyToOne
	@JoinColumn(name="ID_CONSTANCIA")
	private Constancia constancia;
	
	@ManyToOne
	@JoinColumn(name="ID_ANALISTA")
	private Analista analista;
	
	
	public AccionConstancia() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long idAccionConstancia) {
		this.id = idAccionConstancia;
	}


	public Date getFechaHora() {
		return fechaHora;
	}


	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalleConstancia) {
		this.detalle = detalleConstancia;
	}


	public Constancia getConstancia() {
		return constancia;
	}


	public void setConstancia(Constancia constancia) {
		this.constancia = constancia;
	}


	public Analista getAnalista() {
		return analista;
	}


	public void setAnalista(Analista analista) {
		this.analista = analista;
	} 

   
}
