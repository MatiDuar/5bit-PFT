package com.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.logicaNegocio.GestionEventoService;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.Evento;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.TipoActividad;
import com.persistencia.exception.ServicesException;

@Named(value = "gestionEventosWeb") // JEE8
@ViewScoped // JEE8
public class GestionEventosWeb implements Serializable {

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
	
	private List<EstadosEventos> estadosEventoFiltradas;

	@PostConstruct
	public void init() throws ServicesException {
		eventos = persistenciaBean.listarEventos();
		tiposActividades = persistenciaBean.listarTiposActividad();
		modalidadesEvento = persistenciaBean.listarModadlidadesEvento();
		modalidadesEventoActivos = persistenciaBean.listarModadlidadesEvento();
		estadosEvento = persistenciaBean.listarEstadosEventos();

		eventoSeleccionado = new Evento();

		eventoSeleccionadoMod = new Evento();
		
		filtrarCategoriasActivas();
	}
	
	public void filtrarCategoriasActivas() {
    	estadosEventoFiltradas = estadosEvento.stream()
                .filter(EstadosEventos::getActivo)  // Filtra solo los activos
                .collect(Collectors.toList());
    	
    	modalidadesEventoActivos = modalidadesEvento.stream()
                .filter(ModalidadesEventos::getActivo)  // Filtra solo los activos
                .collect(Collectors.toList());
    	
    }


	public void guardarCambios(Evento evento) {
		persistenciaBean.modificarEvento(evento);

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

	

	public Evento getEventoSeleccionadoMod() {
		return eventoSeleccionadoMod;
	}

	public void setEventoSeleccionadoMod(Evento eventoSeleccionadoMod) {
		this.eventoSeleccionadoMod = eventoSeleccionadoMod;
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

	public List<EstadosEventos> getEstadosEventoFiltradas() {
		return estadosEventoFiltradas;
	}

	public void setEstadosEventoFiltradas(List<EstadosEventos> estadosEventoFiltradas) {
		this.estadosEventoFiltradas = estadosEventoFiltradas;
	}
	
	

}
