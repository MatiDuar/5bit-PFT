package com.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.logicaNegocio.GestionEventoService;
import com.persistencia.entities.Evento;
import com.persistencia.entities.ITR;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.TipoActividad;

@Named(value = "gestionEventos") // JEE8
@SessionScoped // JEE8
public class GestionEventos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	GestionEventoService persistenciaBean;

	private List<Evento> eventos;
	
	private List<TipoActividad>tiposActividades;
	
	private List<ModalidadesEventos>modalidadesEvento;
	
	

	@PostConstruct
	public void init() {
		eventos = persistenciaBean.listarEventos();
		tiposActividades = persistenciaBean.listarTiposActividad();
		modalidadesEvento = persistenciaBean.listarModadlidadesEvento();
	}

	public void onRowEdit(RowEditEvent<Evento> evento) {

		persistenciaBean.crearEvento(evento.getObject());

	}

	public void onRowCancel(RowEditEvent<Evento> evento) {

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

}
