package com.beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import com.logicaNegocio.GestionEventoService;
import com.persistencia.entities.ConvocatoriaAsistencia;
import com.persistencia.entities.EstadoAsistencia;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.ITR;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.Reclamo;
import com.persistencia.entities.TipoActividad;
import com.persistencia.entities.Tutor;
import com.persistencia.exception.ServicesException;

@Named(value = "gestionEventos") // JEE8
@SessionScoped // JEE8
public class GestionEventos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	GestionEventoService persistenciaBean;

	@Inject
	DFView dfView;

	@Inject
	PickListView pickListView;
	
	@Inject
	EmailSender emailSender;

	private EstadosEventos estadoAEditar;

	private ModalidadesEventos modalidadAEditar;

	private Evento eventoSeleccionado;

	private Evento eventoSeleccionadoMod;

	private List<Evento> eventos;
	
	private List<Evento> eventosFiltrados;

	private List<TipoActividad> tiposActividades;

	private List<ModalidadesEventos> modalidadesEvento;
	
	private List<ModalidadesEventos> modalidadesEventoActivos;

	private List<EstadosEventos> estadosEvento;

	private List<Tutor> tutoresSeleccionados;

	private List<Tutor> tutoresSeleccionadosMod;

	private List<ConvocatoriaAsistencia> convocatoriasSeleccionadas;

	private List<EstadoAsistencia> estadosAsistencia;

	private Timestamp minDate;

	private Timestamp maxDate;

	private Timestamp fechaInicioEvento;

	
	@PostConstruct
	public void init() {
		try {

			// fechaInicioEvento= new Timestamp(0);

			eventos = persistenciaBean.listarEventos();
			tiposActividades = persistenciaBean.listarTiposActividad();
			modalidadesEvento = persistenciaBean.listarModadlidadesEvento();
			modalidadesEventoActivos = persistenciaBean.listarModadlidadesEvento();
			estadosEvento = persistenciaBean.listarEstadosEventos();
			estadosAsistencia = persistenciaBean.listarEstadosAsistencia();
			eventoSeleccionado = new Evento();
			tutoresSeleccionados = new LinkedList<>();
			eventoSeleccionadoMod = new Evento();

//			//Conseguir fecha minima y maxima para los eventos.
//			Calendar fechMin = Calendar.getInstance();
//			Calendar fechMax = Calendar.getInstance();
//			fechMax.setTime(fechMax.getTime());
//			
//			fechMin.set(Calendar.YEAR,2011);
//			fechMin.set(Calendar.MONTH, Calendar.JANUARY);
//			fechMin.set(Calendar.DAY_OF_MONTH, 1);
//			fechMin.set(Calendar.HOUR, 0);
//			
//			fechMax.add(Calendar.YEAR, +10);
//			
//			minDate = new Timestamp(fechMin.getTimeInMillis());
//	        maxDate = new Timestamp(fechMax.getTimeInMillis());
//	        System.out.println(minDate);
//	        System.out.println(maxDate);
//			
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Timestamp getMinDate() {
		return minDate;
	}

	public void setMinDate(Timestamp minDate) {
		this.minDate = minDate;
	}

	public Timestamp getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Timestamp maxDate) {
		this.maxDate = maxDate;
	}

	public void asignarTutores() {
		pickListView.setEventoSeleccionado(eventoSeleccionado);

		dfView.viewAsignarTutores();
	}

	public Tutor buscarTutorPorId(long id) {
		try {
			return persistenciaBean.buscarTutorPorId(id);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

   public List<Evento> buscarEventosEntreFechas(Date fechaInicio, Date FechaFin) {
	   
	   
	   
		return null;
	}
	
	public void altaEvento() {

		try {
		
			eventoSeleccionado.setEstado(persistenciaBean.buscarEstadoEvento("Futuro"));
			eventoSeleccionado.setTutores(tutoresSeleccionados);
			persistenciaBean.crearEvento(eventoSeleccionado);

			// ##################### Envio de Mails #####################
			for (Tutor tutor : eventoSeleccionado.getTutores()) {
				emailSender.enviarMail("Asignación a Evento: " + eventoSeleccionado.getTitulo(),
						"Estimado/a " + tutor.getNombre1() + " " + tutor.getApellido1() + ",\n\n"
								+ "Le informamos que ha sido asignado como tutor al siguiente evento:\n"
								+ "Nombre del evento: " + eventoSeleccionado.getTitulo() + "\n"
								+ "Fecha Inicio del evento: " + eventoSeleccionado.getFechaInicio()
								+ "\n\n" + "Atentamente,\n" + "El equipo de gestión de eventos",
						tutor.getMailInstitucional());
			}
			// ##############################################################
			
			String msg1 = "";
			
			System.out.println("Tutores en eventoSeleccionado: "+ eventoSeleccionado.getTutores());
			
			if( !eventoSeleccionado.getTutores().isEmpty()) {
				msg1= "Se ha creado el evento con éxito y se ha informado a los tutores.";
			}else {
				msg1= "Se ha creado el evento con éxito.";
			}
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null,facesMsg);
			
			eventoSeleccionado = new Evento();
			
		}catch (Exception e) {
			System.out.println("Error en alta de evento: "+e);
		}
		

	}
	
	public void darDeBajaEvento(Evento e) {
		try {
			if (persistenciaBean.buscarConvocatoriaPorEvento(e).isEmpty()) {
				persistenciaBean.borrarEvento(e);

				FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");

			} else {
				String msg1 = "Para poder eliminar un evento no tiene que contener estudiantes convocados.";
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
		} catch (ServicesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void onRowEdit(RowEditEvent<Evento> evento) {

		persistenciaBean.modificarEvento(evento.getObject());

	}

	public void onRowEditReclamo(RowEditEvent<Reclamo> reclamo) {

		try {
			persistenciaBean.modificarReclamo(reclamo.getObject());
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onRowCancelReclamo(RowEditEvent<Reclamo> reclamo) {

	}

	public void guardarCambios(Evento evento) {

		persistenciaBean.modificarEvento(evento);

	}
	
	

	public void onRowCancel(RowEditEvent<Evento> evento) {

	}

	public void onRowEditRegistroAsistencia(RowEditEvent<Evento> evento) {

		persistenciaBean.crearEvento(evento.getObject());

	}

	public void onRowCancelRegistroAsistencia(RowEditEvent<Evento> evento) {

	}

	public void tutoresAsignadosMod(Evento evento) {
		pickListView.setEventoSeleccionado(evento);
		eventoSeleccionadoMod = evento;
		System.out.println(evento.toString() + " en gestion eVento");
		dfView.viewAsignarTutoresMod();
	}

	public void convocatoriaEvento(Evento evento) {
		eventoSeleccionadoMod = evento;

		dfView.viewEstudiantesConvocados();
	}

	public void registroAsistencia(Evento evento) {
		try {
			convocatoriasSeleccionadas = persistenciaBean.buscarConvocatoriaPorEvento(evento);
			eventoSeleccionadoMod = evento;
			dfView.viewRegistroAsistencia();

		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void guardarCambiosRegistroAsistencias() {
		try {
			persistenciaBean.modificarConvocatoriaAsistencia(convocatoriasSeleccionadas);

			convocatoriasSeleccionadas = new LinkedList<ConvocatoriaAsistencia>();

			dfView.closeResponsive();
		} catch (ServicesException e) {

			e.printStackTrace();
		}
	}

	public void updateListas() {
		eventos = persistenciaBean.listarEventos();
		modalidadesEvento = persistenciaBean.listarModadlidadesEvento();
		estadosEvento = persistenciaBean.listarEstadosEventos();
	}

//	public void fechaInicioListener(Timestamp fechaInicioEvento){
//		eventoSeleccionado.setFechaInicio(new java.sql.Timestamp
//				(fechaInicioEvento.getYear(), 
//						fechaInicioEvento.getMonth(), 
//						fechaInicioEvento.getDate(), 
//						fechaInicioEvento.getHours(), 
//						fechaInicioEvento.getMinutes(), 
//						fechaInicioEvento.getSeconds(), 
//						fechaInicioEvento.getNanos())
//				);
//		System.out.println("entro");
//	}

	
	
	
	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<TipoActividad> getTiposActividades() {
		return tiposActividades;
	}

	public void setTiposActividades(List<TipoActividad> tiposActividades) {
		this.tiposActividades = tiposActividades;
	}

	public List<ModalidadesEventos> getModalidadesEvento() {
		return modalidadesEvento;
	}

	public void setModalidadesEvento(List<ModalidadesEventos> modalidadesEvento) {
		this.modalidadesEvento = modalidadesEvento;
	}

	public Evento getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(Evento eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	public List<EstadosEventos> getEstadosEvento() {
		return estadosEvento;
	}

	public void setEstadosEvento(List<EstadosEventos> estadosEvento) {
		this.estadosEvento = estadosEvento;
	}

	public List<Tutor> getTutoresSeleccionados() {
		return tutoresSeleccionados;
	}

	public void setTutoresSeleccionados(List<Tutor> tutoresSeleccionados) {
		this.tutoresSeleccionados = tutoresSeleccionados;
	}

	public List<Tutor> getTutoresSeleccionadosMod() {
		return tutoresSeleccionadosMod;
	}

	public void setTutoresSeleccionadosMod(List<Tutor> tutoresSeleccionadosMod) {
		this.tutoresSeleccionadosMod = tutoresSeleccionadosMod;
	}

	public Evento getEventoSeleccionadoMod() {
		return eventoSeleccionadoMod;
	}

	public void setEventoSeleccionadoMod(Evento eventoSeleccionadoMod) {
		this.eventoSeleccionadoMod = eventoSeleccionadoMod;
	}

	public List<ConvocatoriaAsistencia> getConvocatoriasSeleccionadas() {
		return convocatoriasSeleccionadas;
	}

	public void setConvocatoriasSeleccionadas(List<ConvocatoriaAsistencia> convocatoriasSeleccionadas) {
		this.convocatoriasSeleccionadas = convocatoriasSeleccionadas;
	}

	public List<EstadoAsistencia> getEstadosAsistencia() {
		return estadosAsistencia;
	}

	public void setEstadosAsistencia(List<EstadoAsistencia> estadosAsistencia) {
		this.estadosAsistencia = estadosAsistencia;
	}

	public void setFechaInicioEvento(java.sql.Timestamp fechaInicioEvento) {
		if(fechaInicioEvento != null) {
			Timestamp sqlTimestamp = new Timestamp(fechaInicioEvento.getTime());
	        eventoSeleccionado.setFechaInicio(sqlTimestamp);
	        System.out.println("fecha inicio en GestionEventos: "+sqlTimestamp);
	        this.fechaInicioEvento = sqlTimestamp;
		}else {
			this.fechaInicioEvento = null;
		}
	}

	public Timestamp getFechaInicioEvento() {

		return eventoSeleccionado.getFechaInicio();
	}
	

	public EstadosEventos getEstadoAEditar() {
		return estadoAEditar;
	}

	public void setEstadoAEditar(EstadosEventos estadoAEditar) {
		this.estadoAEditar = estadoAEditar;
	}

	public void cargarEstadoAEditar(EstadosEventos estado) {
		estadoAEditar = estado;
	}

	public void cargarModalidadAEditar(ModalidadesEventos modalidad) {
		modalidadAEditar = modalidad;
	}

	public ModalidadesEventos getModalidadAEditar() {
		return modalidadAEditar;
	}

	public void setModalidadAEditar(ModalidadesEventos modalidadAEditar) {
		this.modalidadAEditar = modalidadAEditar;
	}


	public List<Evento> getEventosFiltrados() {
		return eventosFiltrados;
	}

	public void setEventosFiltrados(List<Evento> eventosFiltrados) {
		this.eventosFiltrados = eventosFiltrados;
	}

	public List<ModalidadesEventos> getModalidadesEventoActivos() {
		return modalidadesEventoActivos;
	}

	public void setModalidadesEventoActivos(List<ModalidadesEventos> modalidadesEventoActivos) {
		this.modalidadesEventoActivos = modalidadesEventoActivos;
	}
	
	

}
