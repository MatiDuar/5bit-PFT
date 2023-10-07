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
import com.persistencia.entities.Evento;
import com.persistencia.entities.Tutor;
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

	private Evento eventoSeleccionado;

	@PostConstruct
	public void init() {
		List<Tutor> tutoresSource=new ArrayList<>();
		List<Tutor> tutoresTarget = new ArrayList<>();
		tutores = new DualListModel<>(tutoresSource, tutoresTarget);
		instance=this;
		tutores = new DualListModel<>();
		if (eventoSeleccionado == null) {
			eventoSeleccionado = new Evento();
		}
		
		try {
			
			tutores.setSource(new ArrayList<>(service.listarTutores()));
			if (eventoSeleccionado.getTutores() != null) {
				tutores.setTarget(eventoSeleccionado.getTutores());
			}

			
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
//		StringBuilder builder = new StringBuilder();
//		for (Object item : event.getItems()) {
//			builder.append(((Tutor) item).getNombre1() + " " + ((Tutor) item).getApellido1()).append("<br />");
//		}
//
//		FacesMessage msg = new FacesMessage();
//		msg.setSeverity(FacesMessage.SEVERITY_INFO);
//		msg.setSummary("Items Transferred");
//		msg.setDetail(builder.toString());
//
//		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelect(SelectEvent<Tutor> event) {
//		FacesContext context = FacesContext.getCurrentInstance();
//		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected",
//				event.getObject().getNombre1() + " " + event.getObject().getApellido1()));
	}

	public void onUnselect(UnselectEvent<Tutor> event) {
//		FacesContext context = FacesContext.getCurrentInstance();
//		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected",
//				event.getObject().getNombre1() + " " + event.getObject().getApellido1()));
	}

	public void onReorder() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	}

	public String tutorToString(Tutor tutor) {
		return tutor.toString();
	}

	public void guardarCambios() {
		System.out.println(tutores.getTarget() + " tutores Get");
		eventoSeleccionado.setTutores(tutores.getTarget());
		gestionEventos.setTutoresSeleccionados(tutores.getTarget());
		System.out.println(gestionEventos.getTutoresSeleccionados() + " tutores gestion de eventos");
		System.out.println("///////////");
	

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

}