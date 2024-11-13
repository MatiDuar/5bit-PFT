package com.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import com.logicaNegocio.GestionEventoService;
import com.persistencia.entities.ConvocatoriaAsistencia;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Tutor;
import com.persistencia.exception.ServicesException;

@Named
@RequestScoped
public class PickListView {

	@Inject
	private GestionEventoService service;

	@Inject
	private GestionEventos gestionEventos;

	@Inject
	private DFView dfView;
	
	@Inject
	EmailSender emailSender;

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
			System.out.println("######################### Inicio #################################\n");
			System.out.println("Evento Seleccionado Mod: \n" + gestionEventos.getEventoSeleccionadoMod().getTutores());
			
			if (gestionEventos.getEventoSeleccionadoMod().getTutores() != null) {
				System.out.println("Tutores No Seleccionados: \n" + tutoresMod.getSource().toString());
				System.out.println("Tutores Seleccionados: \n" + tutoresMod.getTarget().toString());
				tutoresMod.setTarget(gestionEventos.getEventoSeleccionadoMod().getTutores());
				estudiantesConvocados.setTarget(service.buscarEstudiantesPorEvento(gestionEventos.getEventoSeleccionadoMod()));
			}
			
			System.out.println("######################### Fin #################################\n");
			filtrarTutor(tutores.getSource(), tutores.getTarget());

			filtrarTutor(tutoresMod.getSource(), tutoresMod.getTarget());

			estudiantesConvocados.getSource().removeAll(estudiantesConvocados.getTarget());

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

		StringBuilder builder = new StringBuilder();
		List<Tutor> listTutores = new ArrayList<>();
        for (Object item : event.getItems()) {
            builder.append(((Tutor) item)); 
            listTutores.add((Tutor) item);
        }
        
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Tutor Transferido");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
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
		return estudiante.toString()+ " " + estudiante.getAnoIngreso();
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
		System.out.println(evento + " en guardar Cambios  ");

		Evento eventoOriginal = null;
		try {
			eventoOriginal = service.buscarEventoPorId(evento.getId());
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Tutor> tutoresYaAsignados = eventoOriginal.getTutores();
		
		
		gestionEventos.guardarCambios(evento);

		
		// #####################  Envio de Mails #####################			
				for( Tutor tutor: evento.getTutores()) {	
					if(!tutoresYaAsignados.contains(tutor)) {
					emailSender.enviarMail("Asignación a Evento: " + gestionEventos.getEventoSeleccionado().getTitulo(), 
						    "Estimado/a " + tutor.getNombre1() + " " + tutor.getApellido1() +",\n\n" +
						    "Le informamos que ha sido asignado como tutor al siguiente evento:\n" +
						    "Nombre del evento: " + gestionEventos.getEventoSeleccionado().getTitulo() + "\n" +
						    "Fecha Inicio del evento: " + gestionEventos.getEventoSeleccionado().getFechaInicio() + "\n\n" +
						    "Atentamente,\n" +
						    "El equipo de gestión de eventos",
						    tutor.getMailInstitucional());
					}
					
				}
		// ##############################################################
		
		String msg1 = "";
				
		System.out.println("tutores en evento a modificar: "+evento.getTutores().isEmpty());
			
			msg1= "Se ha notificado a los tutores correspondientes.";
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null,facesMsg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		
				
		
		
		eventoSeleccionado = new Evento();
		dfView.closeResponsive();

	}
	
	
	public void guardarCambiosConvocatoria() {
		Evento evento = gestionEventos.getEventoSeleccionadoMod();
		
		try {
			List<Estudiante>currentConvocados=service.buscarEstudiantesPorEvento(evento);
				
			
		
		for (Estudiante e : estudiantesConvocados.getTarget()) {				
				if(!currentConvocados.contains(e)) {
					ConvocatoriaAsistencia ca = new ConvocatoriaAsistencia();
					ca.setEstudiante(e);
					ca.setEvento(evento);
					ca.setCalificacion(0);
					ca.setEstadoAsistencia(service.buscarEstadoAsistenciaPorNombre("Sin Registrar"));		
					service.crearConvocatoriaAsistencia(ca);
				}		
		}
		
		currentConvocados.removeAll(estudiantesConvocados.getTarget());
		
		for(Estudiante e:currentConvocados) {
			service.borrarConvocatoria(e, evento);
		}
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void filtrarTutor(List<Tutor> tutores, List<Tutor> tutoresSeleccionado) {

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