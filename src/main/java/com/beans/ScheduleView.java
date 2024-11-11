package com.beans;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.persistencia.entities.ConvocatoriaAsistencia;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Usuario;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Named("scheduleView")
@ViewScoped
public class ScheduleView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScheduleModel eventModel;
	private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
	private List<Evento> eventos;
	private List<Evento> eventosUsuario = new ArrayList<>();;
	private List<ConvocatoriaAsistencia> convocatoriaEvento;
	private Usuario usuLogueado;

	@Inject
	private GestionPersona gestionPersona;

	@Inject
	private GestionEventos gestionEventos;

	@PostConstruct
	public void init() {
		
		gestionPersona.init();
		usuLogueado = gestionPersona.getUsuarioLogeado();
		String tipoUsuario = gestionPersona.tipoUsuario(usuLogueado);
		boolean asisteEvento = false;
		try {
			eventos = gestionEventos.getEventos();
			
			for (Evento eve : eventos) {
				convocatoriaEvento = gestionEventos.buscarRegistroAsistencia(eve);
				for (ConvocatoriaAsistencia conv : convocatoriaEvento) {
					if(tipoUsuario.equals("Estudiante")) {
						System.out.println(" ##################################### Paso por estudiante #####################################");
						if (usuLogueado.equals(conv.getEstudiante())) {
							// agrega evento si el estudiante existe en la convocatoria
							asisteEvento = true;
						}
					} else if (tipoUsuario.equals("Tutor")) {
						System.out.println(" ##################################### Paso por Tutor #####################################");
						if (!eve.getTutores().contains(usuLogueado)) {
							// agrega evento si el tutor esta asignado al evento
							asisteEvento = true; 
						}	 
					} else {
						System.out.println(" ##################################### Paso por Admin #####################################");
						// si es admin se agregan todos los eventos
						asisteEvento = true; 
					}
				}
				if(asisteEvento) {
					eventosUsuario.add(eve);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		eventModel = new DefaultScheduleModel();
		// Agregar eventos
		for(Evento eveUsu : eventosUsuario) {			
			LocalDateTime  startDate = eveUsu.getFechaInicio().toLocalDateTime();
			LocalDateTime  endDate = eveUsu.getFechaFin().toLocalDateTime();
			
			 // Personalización colores
		    String backgroundColor;
		    String borderColor;
		    String textColor;

		    if (eveUsu.getTipoActividad().getNombre().equals("Examen")) {
		        backgroundColor = "#e74c3c"; 
		        borderColor = "#c0392b";     
		        textColor = "#ffffff";       
		    } else if (eveUsu.getTipoActividad().getNombre().equals("Jornada Presencial")) {
		        backgroundColor = "#2ecc71"; 
		        borderColor = "#27ae60";     
		        textColor = "#ffffff";       
		    } else if(eveUsu.getTipoActividad().getNombre().equals("Prueba Final")){
		        backgroundColor = "#fc7b03"; 
		        borderColor = "#fc7b03";     
		        textColor = "#ffffff";       
		    }else if(eveUsu.getTipoActividad().getNombre().equals("Defensa de Proyecto")){
		    	backgroundColor = "#e303fc"; 
		        borderColor = "#e303fc";     
		        textColor = "#ffffff";       
		    }else { // otros 
		    	backgroundColor = "#ffffff"; 
		        borderColor = "#ffffff";     
		        textColor = "#ffffff";       
		    }
			
			
			eventModel.addEvent(DefaultScheduleEvent.builder()
					.title(eveUsu.getTitulo())
					.startDate(startDate)
					.endDate(endDate.plusHours(1))
					.backgroundColor(backgroundColor)
					.borderColor(borderColor)
					.textColor(textColor)
					.build());
		}

	}


	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent<?> getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent<?> event) {
		this.event = event;
	}

	public void addEvent() {
		if (event.getId() == null) {
			eventModel.addEvent(event);
		} else {
			eventModel.updateEvent(event);
		}
		event = new DefaultScheduleEvent<>();
	}

	public void onDateSelect(org.primefaces.event.SelectEvent<Date> selectEvent) {
		// Convertir Date a LocalDateTime
		//LocalDateTime startDate = selectEvent.getObject().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		for(Evento eveUsu : eventosUsuario) {
			LocalDateTime  startDate = eveUsu.getFechaInicio().toLocalDateTime();
			LocalDateTime  endDate = eveUsu.getFechaFin().toLocalDateTime();
			event = DefaultScheduleEvent.builder()
					.title(eveUsu.getTitulo())
					.startDate(startDate)
					.endDate(endDate.plusHours(1)) // Establecer una duración de ejemplo
					.build();
		}

//		event = DefaultScheduleEvent.builder()
//				.title("Nuevo Evento")
//				.startDate(startDate)
//				.endDate(startDate.plusHours(1)) // Establecer una duración de ejemplo
//				.build();
	}

	public void onEventSelect(org.primefaces.event.SelectEvent<ScheduleEvent<?>> selectEvent) {
		event = selectEvent.getObject();
	}
}
