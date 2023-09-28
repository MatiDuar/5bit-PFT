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

@Named(value = "gestionEventos") // JEE8
@SessionScoped // JEE8
public class GestionEventos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	GestionEventoService persistenciaBean;

	private List<Evento> eventos;

	@PostConstruct
	public void init() {
		eventos = persistenciaBean.listarEventos();
	}

	public void onRowEdit(RowEditEvent<Evento> evento) {

		persistenciaBean.crearEvento(evento.getObject());

	}

	public void onRowCancel(RowEditEvent<Evento> evento) {

	}

}
