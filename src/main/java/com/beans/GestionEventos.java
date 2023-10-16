package com.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
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

	private Evento eventoSeleccionado;

	private Evento eventoSeleccionadoMod;

	private List<Evento> eventos;

	private List<TipoActividad> tiposActividades;

	private List<ModalidadesEventos> modalidadesEvento;

	private List<EstadosEventos> estadosEvento;

	private List<Tutor> tutoresSeleccionados;

	private List<Tutor> tutoresSeleccionadosMod;

	private List<ConvocatoriaAsistencia> convocatoriasSeleccionadas;

	private List<EstadoAsistencia> estadosAsistencia;

	@PostConstruct
	public void init() {
		try {
			eventos = persistenciaBean.listarEventos();
			tiposActividades = persistenciaBean.listarTiposActividad();
			modalidadesEvento = persistenciaBean.listarModadlidadesEvento();
			estadosEvento = persistenciaBean.listarEstadosEventos();
			estadosAsistencia = persistenciaBean.listarEstadosAsistencia();
			eventoSeleccionado = new Evento();
			tutoresSeleccionados = new LinkedList<>();
			eventoSeleccionadoMod = new Evento();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void altaEvento() {

		eventoSeleccionado.setEstado(persistenciaBean.buscarEstadoEvento("Futuro"));
		eventoSeleccionado.setTutores(tutoresSeleccionados);
		persistenciaBean.crearEvento(eventoSeleccionado);
		eventoSeleccionado = new Evento();
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

		persistenciaBean.crearEvento(evento.getObject());

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

		persistenciaBean.crearEvento(evento);

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

}
