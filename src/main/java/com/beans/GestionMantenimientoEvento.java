package com.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.logicaNegocio.GestionITRService;
import com.logicaNegocio.GestionMantenimientoEventoService;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.ITR;
import com.persistencia.entities.ModalidadesEventos;

@Named(value = "gestionMantenimientoEvento") // JEE8
@SessionScoped // JEE8
public class GestionMantenimientoEvento implements Serializable {

	@Inject
	GestionMantenimientoEventoService persistenciaBean;

	@Inject
	DFView dfView;
	
	@Inject
	FilterView filterView;
	
	
	@Inject
	GestionEventos gestionEventos;

	private EstadosEventos estadoEventoSeleccionado;

	private ModalidadesEventos modalidadEventoSeleccionado;

	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		estadoEventoSeleccionado = new EstadosEventos();
		modalidadEventoSeleccionado = new ModalidadesEventos();
	}

	public void onRowEdit(RowEditEvent<EstadosEventos> es) {

		persistenciaBean.crearEstadoEvento(es.getObject());

	}

	public void onRowEditModalidad(RowEditEvent<ModalidadesEventos> es) {

		persistenciaBean.crearModalidadEvento(es.getObject());

	}

	public void onRowCancel(RowEditEvent<ITR> itr) {

	}

	public String estadoToString(boolean es) {
		if (es) {
			return "Activo";
		} else {
			return "Inactivo";
		}

	}

	public void crearEstadoEvento() {
		estadoEventoSeleccionado.setActivo(true);
		persistenciaBean.crearEstadoEvento(estadoEventoSeleccionado);
		estadoEventoSeleccionado = new EstadosEventos();
		dfView.closeResponsive();
		filterView.updateEstadoEvento();
		gestionEventos.updateListas();

	}
	
	public void crearModalidadEvento() {
	
		modalidadEventoSeleccionado.setActivo(true);
		persistenciaBean.crearModalidadEvento(modalidadEventoSeleccionado);
		modalidadEventoSeleccionado = new ModalidadesEventos();
		dfView.closeResponsive();
		filterView.updateModalidadEvento();
		gestionEventos.updateListas();


	}

	public EstadosEventos getEstadoEventoSeleccionado() {
		return estadoEventoSeleccionado;
	}

	public void setEstadoEventoSeleccionado(EstadosEventos estadoEventoSeleccionado) {
		this.estadoEventoSeleccionado = estadoEventoSeleccionado;
	}

	public ModalidadesEventos getModalidadEventoSeleccionado() {
		return modalidadEventoSeleccionado;
	}

	public void setModalidadEventoSeleccionado(ModalidadesEventos modalidadEventoSeleccionado) {
		this.modalidadEventoSeleccionado = modalidadEventoSeleccionado;
	}

	

}
