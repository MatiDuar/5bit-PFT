package com.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import java.util.LinkedList;

import com.logicaNegocio.GestionEventoService;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.Evento;
import com.persistencia.entities.ITR;
import com.persistencia.entities.ModalidadesEventos;
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

	private List<Evento> eventos;
	
	private List<TipoActividad>tiposActividades;
	
	private List<ModalidadesEventos>modalidadesEvento;
	
	private List<EstadosEventos> estadosEvento;
	
	
	private List<Tutor>tutoresSeleccionados;
	
	
	
	
	

	@PostConstruct
	public void init() {
		eventos = persistenciaBean.listarEventos();
		tiposActividades = persistenciaBean.listarTiposActividad();
		modalidadesEvento = persistenciaBean.listarModadlidadesEvento();
		estadosEvento = persistenciaBean.listarEstadosEventos();
		eventoSeleccionado=new Evento();
		tutoresSeleccionados=new LinkedList<>();		
	
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
		persistenciaBean.crearEvento(eventoSeleccionado);
		eventoSeleccionado=new Evento();
	}

	public void onRowEdit(RowEditEvent<Evento> evento) {

		persistenciaBean.crearEvento(evento.getObject());

	}

	public void onRowCancel(RowEditEvent<Evento> evento) {

	}
	
	public void updateListas() {
		eventos=persistenciaBean.listarEventos();
		modalidadesEvento=persistenciaBean.listarModadlidadesEvento();
		estadosEvento=persistenciaBean.listarEstadosEventos();
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
	
	
	


	
	

}
