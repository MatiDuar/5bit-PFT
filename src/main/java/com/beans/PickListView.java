package com.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;

import org.primefaces.model.DualListModel;

import com.logicaNegocio.GestionEventoService;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Tutor;
import com.persistencia.entities.Usuario;
import com.persistencia.entities.ConvocatoriaAsistencia;
import com.persistencia.exception.ServicesException;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

@Named
@RequestScoped
public class PickListView {

	@Inject
	private GestionEventoService service;

	@Inject
	private GestionEventos gestionEventos;

	@Inject
	private DFView dfView;

	public static PickListView instance;

	private DualListModel<Tutor> tutores;

	private DualListModel<Tutor> tutoresMod;

	private DualListModel<Estudiante> estudiantesConvocados;

	private Evento eventoSeleccionado;

	@PostConstruct
	public void init() {
		List<Tutor> tutoresSource = new ArrayList<>();
		List<Tutor> tutoresTarget = new ArrayList<>();
		tutores = new DualListModel<>(tutoresSource, tutoresTarget);

		List<Tutor> tutoresSourceMod = new ArrayList<>();
		List<Tutor> tutoresTargetMod = new ArrayList<>();
		tutoresMod = new DualListModel<>(tutoresSourceMod, tutoresTargetMod);
		instance = this;

		List<Estudiante> estudiantesSource = new ArrayList<>();
		List<Estudiante> estudiantesTarget = new ArrayList<>();

		estudiantesConvocados = new DualListModel<>(estudiantesSource, estudiantesTarget);

		if (eventoSeleccionado == null) {
			eventoSeleccionado = new Evento();
		}

		try {
			tutores.setSource(new ArrayList<>(service.listarTutores()));
			tutoresMod.setSource(new ArrayList<>(service.listarTutores()));

			estudiantesConvocados.setSource(new ArrayList<>(service.listarEstudiantes()));
			if (gestionEventos.getTutoresSeleccionados() != null) {
				tutores.setTarget(gestionEventos.getTutoresSeleccionados());
			}
			if (gestionEventos.getEventoSeleccionadoMod().getTutores() != null) {
				tutoresMod.setTarget(gestionEventos.getEventoSeleccionadoMod().getTutores());
			}

			filtrarTutores(tutores.getSource(), tutores.getTarget());

			filtrarTutores(tutoresMod.getSource(), tutoresMod.getTarget());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DualListModel<Tutor> getTutores() {
		return tutores;
	}

	public void setTutores(DualListModel<Tutor> tutores) {

		this.tutores = tutores;
	}

	public void onTransfer(TransferEvent event) {

	}

	public void onSelect(SelectEvent<Tutor> event) {

	}

	public void onUnselect(UnselectEvent<Tutor> event) {

	}

	public void onReorder() {

	}

	public String tutorToString(Tutor tutor) {
		return tutor.toString();
	}

	public String estudianteToString(Estudiante estudiante) {
		return estudiante.toString();
	}

	public void guardarCambios() {
		System.out.println(tutores.getTarget() + " tutores Get");
		eventoSeleccionado.setTutores(tutores.getTarget());
		gestionEventos.setTutoresSeleccionados(tutores.getTarget());
		System.out.println(gestionEventos.getTutoresSeleccionados() + " tutores gestion de eventos");
		System.out.println("///////////");

		dfView.closeResponsive();

	}

	public void guardarCambiosMod() {

		Evento evento = gestionEventos.getEventoSeleccionadoMod();
		evento.setTutores(tutoresMod.getTarget());
		System.out.println(evento + " en guardarCambiuos");

		gestionEventos.guardarCambios(evento);

		eventoSeleccionado = new Evento();
		dfView.closeResponsive();

	}

	public void guardarCambiosConvocatoria() {

		Evento evento = gestionEventos.getEventoSeleccionadoMod();
		for (Estudiante e : estudiantesConvocados.getTarget()) {
			try {
				ConvocatoriaAsistencia ca = new ConvocatoriaAsistencia();
				ca.setEstudiante(e);
				ca.setEvento(evento);
				ca.setCalificacion(0);

				ca.setEstadoAsistencia(service.buscarEstadoAsistenciaPorNombre("Sin Registrar"));
				
				service.crearConvocatoriaAsistencia(ca);
			} catch (ServicesException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(evento + " en guardarCambiuos");

		gestionEventos.guardarCambios(evento);

		eventoSeleccionado = new Evento();
		dfView.closeResponsive();

	}

	public Tutor buscarTutorPorId(long id) {
		try {
			return service.buscarTutorPorId(id);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Estudiante buscarEstudiantePorId(long id) {
		try {
			return service.buscarEstudiantePorId(id);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void filtrarTutores(List<Tutor> tutores, List<Tutor> tutoresSeleccionado) {

		tutores.removeAll(tutoresSeleccionado);

	}

	public Evento getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(Evento eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	public static PickListView getInstance() {
		return instance;
	}

	public static void setInstance(PickListView instance) {
		PickListView.instance = instance;
	}

	public DualListModel<Tutor> getTutoresMod() {
		return tutoresMod;
	}

	public void setTutoresMod(DualListModel<Tutor> tutoresMod) {
		this.tutoresMod = tutoresMod;
	}

	public DualListModel<Estudiante> getEstudiantesConvocados() {
		return estudiantesConvocados;
	}

	public void setEstudiantesConvocados(DualListModel<Estudiante> estudiantesConvocados) {
		this.estudiantesConvocados = estudiantesConvocados;
	}

}