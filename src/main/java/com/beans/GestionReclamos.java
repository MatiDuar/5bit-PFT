package com.beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.logicaNegocio.GestionReclamoService;
import com.persistencia.entities.AccionReclamo;
import com.persistencia.entities.Analista;
import com.persistencia.entities.Estado;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.ModalidadesEventos;
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

	private AccionReclamo accionReclamoSeleccionado;
	private Date fechaSeleccionada;

	private Date fechaAlta;

	private List<Estado> estadosReclamo;
	
	private Estado estadoReclamoAlta;
	
	
	private String tipoReclamo;
	
	private String yearRange;

	@PostConstruct
	void init() {
		reclamoSeleccionado = null;
		fechaAlta = null;
		reclamoAlta = new Reclamo();
		accionReclamoSeleccionado = new AccionReclamo();
		tipoReclamo="";
		estadoReclamoAlta=new Estado();
		
		try {
			estadosReclamo = serivce.listarEstados();
			yearRange="1950-2000";
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void guardarCambios() {
		try {
			
			if (fechaSeleccionada != null) {
				reclamoSeleccionado.setFechaEvento(new java.sql.Date(fechaSeleccionada.getTime()));
			}
			serivce.modificar(reclamoSeleccionado);

			fechaSeleccionada = null;
			reclamoSeleccionado = new Reclamo();

			dfView.closeResponsive();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String crearReclamo() {
		try {
			if (fechaAlta != null) {
				reclamoAlta.setFechaEvento(new java.sql.Date(fechaAlta.getTime()));
			}

			reclamoAlta.setFechaHora(new Timestamp(System.currentTimeMillis()));
			reclamoAlta.setEstudiante((Estudiante) gestionPersona.getUsuarioLogeado());
			reclamoAlta.setEstado(serivce.buscarEstadoReclamo("Ingresado"));
			serivce.modificar(reclamoAlta);

			fechaSeleccionada = null;
			reclamoSeleccionado = new Reclamo();
			reclamoAlta= new Reclamo();
			dfView.closeResponsive();
		
				
			
			
			

			return "";

			
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} 

	}

	public void onRowClick(Reclamo event) {



		reclamoSeleccionado = event;
		dfView.viewReclamoMod();

	}

	

	public void realizarAccionReclamo() {
		try {
		
			accionReclamoSeleccionado.setAnalista((Analista) gestionPersona.getUsuarioLogeado());
			accionReclamoSeleccionado.setReclamo(reclamoSeleccionado);
			accionReclamoSeleccionado.setFechaHoraReclamo(new Timestamp(System.currentTimeMillis()));

			serivce.crearAccionReclamo(accionReclamoSeleccionado);

			
			accionReclamoSeleccionado = new AccionReclamo();

			dfView.closeResponsive();
			
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	
	public void onRowEditEstado(RowEditEvent<Estado> es) {

		try {
			serivce.crearEstadoReclamo(es.getObject());
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void altaEstadoReclamo() {
		try {
			estadoReclamoAlta.setActivo(true);
	
			serivce.crearEstadoReclamo(estadoReclamoAlta);
			estadoReclamoAlta=new Estado();
			
			
			dfView.closeResponsive();
			dfView.closeResponsive();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void darDeBajaReclamo(Reclamo reclamo) throws ServicesException {
		if(serivce.buscarAccionesPorReclamo(reclamo).isEmpty()) {
			serivce.darDeBajaReclamo(reclamo.getId());
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("reclamos.xhtml");
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		}else {
			String msg1 = "Para poder eliminar un reclamo no tiene que tener acciones relacionadas.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		
	}
	
	public boolean renderNombreEvento() {
		return tipoReclamo.equalsIgnoreCase("VME");
	}
	
	public boolean renderTodo() {
		return tipoReclamo.equalsIgnoreCase("otro");
	}
	
	public boolean renderTodoMod() {
		return !(reclamoSeleccionado.getNombreActividad()==null && reclamoSeleccionado.getNombreEventoVME()==null);
	}
	
	public boolean renderNombreEventoMod() {
		if(reclamoSeleccionado.getNombreEventoVME()==null) {
			return false;
		}else {
			return true;
		}
		
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
		fechaSeleccionada = reclamoSeleccionado.getFechaEvento();
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

	public AccionReclamo getAccionReclamoSeleccionado() {
		return accionReclamoSeleccionado;
	}

	public void setAccionReclamoSeleccionado(AccionReclamo accionReclamoSeleccionado) {
		this.accionReclamoSeleccionado = accionReclamoSeleccionado;
	}

	public Estado getEstadoReclamoAlta() {
		return estadoReclamoAlta;
	}

	public void setEstadoReclamoAlta(Estado estadoReclamoAlta) {
		this.estadoReclamoAlta = estadoReclamoAlta;
	}

	public String getTipoReclamo() {
		return tipoReclamo;
	}

	public void setTipoReclamo(String tipoReclamo) {
		this.tipoReclamo = tipoReclamo;
	}

	public String getYearRange() {
		return yearRange;
	}

	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}
	
	

}
