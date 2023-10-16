package com.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.logicaNegocio.GestionReclamoService;
import com.persistencia.entities.Estado;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Reclamo;
import com.persistencia.exception.ServicesException;

@Named(value = "gestionReclamos") // JEE8
@SessionScoped // JEE8
public class GestionReclamos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	GestionReclamoService serivce;
	
	@Inject
	GestionPersona gestionPersona;

	@Inject
	DFView dfView;

	private Reclamo reclamoSeleccionado;
	
	private Reclamo reclamoAlta;
	
	private Date fechaSeleccionada;
	
	private Date fechaAlta;
	
	private List<Estado>estadosReclamo;

	@PostConstruct
	void init() {
		reclamoSeleccionado = null;
		fechaAlta=null;
		reclamoAlta=new Reclamo();
		try {
			estadosReclamo=serivce.listarEstados();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void guardarCambios() {
		try {
			
			reclamoSeleccionado.setFechaEvento(new java.sql.Date(fechaSeleccionada.getTime()));
			serivce.modificar(reclamoSeleccionado);

			fechaSeleccionada=null;
			reclamoSeleccionado = new Reclamo();
			
			dfView.closeResponsive();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void crearReclamo() {
		try {
			
			reclamoAlta.setFechaEvento(new java.sql.Date(fechaAlta.getTime()));
			reclamoAlta.setFechaHora(new Timestamp(System.currentTimeMillis()));
			reclamoAlta.setEstudiante((Estudiante) gestionPersona.getUsuarioLogeado());
			reclamoAlta.setEstado(serivce.buscarEstadoReclamo("Ingresado"));
			serivce.modificar(reclamoAlta);

			fechaSeleccionada=null;
			reclamoSeleccionado = new Reclamo();
			
			dfView.closeResponsive();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void onRowClick(Reclamo event) {

		System.out.println(event);

		reclamoSeleccionado = event;
		dfView.viewReclamoMod();

	}
	
	public void altaReclamo() {
		dfView.viewReclamoAlta();
	}

	public Reclamo getReclamoSeleccionado() {
		return reclamoSeleccionado;
	}

	public void setReclamoSeleccionado(Reclamo reclamoSeleccionado) {
		this.reclamoSeleccionado = reclamoSeleccionado;
	}

	public Date getFechaSeleccionada() {
		fechaSeleccionada=reclamoSeleccionado.getFechaEvento();
		return fechaSeleccionada;
	}

	public void setFechaSeleccionada(Date fechaSeleccionada) {
		this.fechaSeleccionada = fechaSeleccionada;
	}

	public List<Estado> getEstadosReclamo() {
		return estadosReclamo;
	}

	public void setEstadosReclamo(List<Estado> estadosReclamo) {
		this.estadosReclamo = estadosReclamo;
	}

	public Reclamo getReclamoAlta() {
		return reclamoAlta;
	}

	public void setReclamoAlta(Reclamo reclamoAlta) {
		this.reclamoAlta = reclamoAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	
	
}
