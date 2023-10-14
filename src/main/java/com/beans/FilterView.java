package com.beans;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.util.LangUtils;

import com.logicaNegocio.GestionPersonaService;

import com.persistencia.entities.Carrera;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Usuario;
import com.persistencia.entities.ITR;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.Tutor;

@Named("dtFilterView")
@ViewScoped
public class FilterView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private GestionPersonaService service;
	
	@Inject
	private GestionPersona gestionPersona;

	// filtros para Usuarios
	private String carreraSel;

	private String itrSeleccionado;

	private String tipoUsuarioSeleccionado;

	private String anoIngresoSeleccionado;

	private String estadoSeleccionado;

	private List<Usuario> personas;

	private List<Usuario> filteredPersonas;

	private List<ITR> itrs;

	private List<ITR> filteredItrs;

	private List<FilterMeta> filterBy;

	private boolean globalFilterOnly;

	private int currentYear;

	// filtros Evento
	private String tipoEventoSelccionado;

	private String estadoEvento;
	private String itrEventoSelccionado;

	private String modalidadEventoSeleccionada;

	private List<Evento> eventos;

	private List<Evento> filteredEventos;

	// filtros Estado Evento

	private List<EstadosEventos> estadoEventos;

	private List<EstadosEventos> filteredestadoEventos;

	// filtros Modalidades Evento

	private List<ModalidadesEventos> modalidadEventos;

	private List<ModalidadesEventos> filteredModalidadEventos;

	// filtros para tutores

	@PostConstruct
	public void init() {
		globalFilterOnly = true;
		try {
			personas = service.listarPersonas();
			itrs = service.listarITRs();
			eventos = service.listarEventos();
			estadoEventos = service.listarEstadosEventos();
			modalidadEventos=service.listarModalidadesEventos();

			// filtros para Usuario
			itrSeleccionado = "";
			tipoUsuarioSeleccionado = "";
			anoIngresoSeleccionado = "";
			estadoSeleccionado = "";

			// filtros para Eventos

			tipoEventoSelccionado = "";
			modalidadEventoSeleccionada = "";
			estadoEvento = "";
			itrEventoSelccionado = "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		currentYear = new Date(System.currentTimeMillis()).getYear() + 1900;
		filterBy = new ArrayList<>();

		filterBy.add(FilterMeta.builder().field("date")
				.filterValue(
						new ArrayList<>(Arrays.asList(LocalDate.now().minusDays(28), LocalDate.now().plusDays(28))))
				.matchMode(MatchMode.BETWEEN).build());

	}

	/**
	 * Esta funcion se encarga de filtrar la tabla,
	 * 
	 * @param value  El objeto al cual se le aplicaran los filtros
	 * @param filter Los filtros a aplicar
	 * @param locale
	 * @return retorna verdadero si el objeto coincide con los filtros sino retorna
	 *         falso
	 */
	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (LangUtils.isBlank(filterText) && itrSeleccionado.isBlank() && tipoUsuarioSeleccionado.isBlank()
				&& anoIngresoSeleccionado.isBlank() && estadoSeleccionado.isBlank()) {
			return true;
		}

		Usuario persona = (Usuario) value;

		if (!persona.getItr().getNombre().equalsIgnoreCase(itrSeleccionado) && !itrSeleccionado.isBlank()) {
			return false;
		}

		if (!persona.getClass().toString().toLowerCase().contains(tipoUsuarioSeleccionado)
				&& !tipoUsuarioSeleccionado.isBlank()) {
			return false;
		}
		if (!(persona instanceof Estudiante) && !anoIngresoSeleccionado.isBlank()) {
			return false;
		}
		if (!anoIngresoSeleccionado.isBlank()) {
			return (((Estudiante) persona).getAnoIngreso() + "").equals(anoIngresoSeleccionado);
		}

		if (estadoSeleccionado.equalsIgnoreCase("Sin Validar") && !(persona.getActivo() && !persona.getValidado())) {
			return false;
		}

		if (estadoSeleccionado.equalsIgnoreCase("Eliminado") && persona.getActivo()) {
			return false;
		}
		if (estadoSeleccionado.equalsIgnoreCase("Validados") && !(persona.getValidado() && persona.getActivo())) {
			return false;
		}

		return (persona.getNombre1().toLowerCase().contains(filterText)
				|| persona.getApellido1().toLowerCase().contains(filterText)
				|| persona.getNombreUsuario().toLowerCase().contains(filterText)
				|| persona.getMail().toString().toLowerCase().contains(filterText)
				|| persona.getLocalidad().toLowerCase().contains(filterText));

	}

	public boolean globalFilterFunctionITR(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (LangUtils.isBlank(filterText)) {
			return true;
		}

		ITR itr = (ITR) value;

		return (itr.getNombre().toLowerCase().contains(filterText)
				|| itr.getDepartamento().getNombre().toLowerCase().contains(filterText));

	}

	public boolean globalFilterFunctionEventos(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		
		Evento evento = (Evento) value;
		if(!gestionPersona.esAnalistaLogeado()) {
			if(!evento.getTutores().contains(gestionPersona.getUsuarioLogeado())) {
				return false;
			}
		}
		if (LangUtils.isBlank(filterText) && tipoEventoSelccionado.isBlank() && estadoEvento.isBlank()
				&& itrEventoSelccionado.isBlank() && modalidadEventoSeleccionada.isBlank()) {
			
			return true;
		}

		
		
		

		if (!evento.getTipoActividad().getNombre().equalsIgnoreCase(tipoEventoSelccionado)
				&& !tipoEventoSelccionado.isBlank()) {
			return false;
		}

		if (!evento.getEstado().getNombre().equalsIgnoreCase(estadoEvento) && !estadoEvento.isBlank()) {
			return false;
		}

		if (!evento.getItr().getNombre().equalsIgnoreCase(itrEventoSelccionado) && !itrEventoSelccionado.isBlank()) {
			return false;
		}

		if (!evento.getModalidad().getNombre().equalsIgnoreCase(modalidadEventoSeleccionada)
				&& !modalidadEventoSeleccionada.isBlank()) {
			return false;
		}

		return (evento.getTitulo().toLowerCase().contains(filterText))
				|| evento.getLocalizacion().toLowerCase().contains(filterText);

	}

	public boolean globalFilterFunctionEstadoEventos(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (LangUtils.isBlank(filterText)) {
			return true;
		}

		EstadosEventos es = (EstadosEventos) value;

		return (es.getNombre().toLowerCase().contains(filterText));

	}
	
	public boolean globalFilterFunctionModalidadEventos(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (LangUtils.isBlank(filterText)) {
			return true;
		}

		ModalidadesEventos me = (ModalidadesEventos) value;

		return (me.getNombre().toLowerCase().contains(filterText));

	}
	
	
	public void updateModalidadEvento() {
		try {
			modalidadEventos=service.listarModalidadesEventos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateEstadoEvento() {
		try {
			estadoEventos=service.listarEstadosEventos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void updateItr() {
		try {
			itrs=service.listarITRs();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void toggleGlobalFilter() {
		setGlobalFilterOnly(!isGlobalFilterOnly());
	}

	public Usuario buscar(long id) {
		return service.buscarUsuario(id);
	}

	public int getEdad(String fecha) {
		String str[] = fecha.split("-");

		return currentYear - Integer.parseInt(str[0]);
	}

	public void setFilterBy(List<FilterMeta> filterBy) {
		this.filterBy = filterBy;
	}

	public List<FilterMeta> getFilterBy() {
		return filterBy;
	}

	public boolean isGlobalFilterOnly() {
		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly) {
		this.globalFilterOnly = globalFilterOnly;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public String getCarreraSel() {
		return carreraSel;
	}

	public void setCarreraSel(String carreraSel) {
		this.carreraSel = carreraSel;
	}

	public List<Usuario> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Usuario> personas) {
		this.personas = personas;
	}

	public List<Usuario> getFilteredPersonas() {
		return filteredPersonas;
	}

	public void setFilteredPersonas(List<Usuario> filteredPersonas) {
		this.filteredPersonas = filteredPersonas;
	}

	public List<ITR> getItrs() {
		return itrs;
	}

	public void setItrs(List<ITR> itrs) {
		this.itrs = itrs;
	}

	public List<ITR> getFilteredItrs() {
		return filteredItrs;
	}

	public void setFilteredItrs(List<ITR> filteredItrs) {
		this.filteredItrs = filteredItrs;
	}

	public String getItrSeleccionado() {
		return itrSeleccionado;
	}

	public void setItrSeleccionado(String itrSeleccionado) {
		this.itrSeleccionado = itrSeleccionado;
	}

	public String getTipoUsuarioSeleccionado() {
		return tipoUsuarioSeleccionado;
	}

	public void setTipoUsuarioSeleccionado(String tipoUsuarioSeleccionado) {
		this.tipoUsuarioSeleccionado = tipoUsuarioSeleccionado;
	}

	public String getAnoIngresoSeleccionado() {
		return anoIngresoSeleccionado;
	}

	public void setAnoIngresoSeleccionado(String anoIngresoSeleccionado) {
		this.anoIngresoSeleccionado = anoIngresoSeleccionado;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Evento> getFilteredEventos() {
		return filteredEventos;
	}

	public void setFilteredEventos(List<Evento> filteredEventos) {
		this.filteredEventos = filteredEventos;
	}

	public String getTipoEventoSelccionado() {
		return tipoEventoSelccionado;
	}

	public void setTipoEventoSelccionado(String tipoEventoSelccionado) {
		this.tipoEventoSelccionado = tipoEventoSelccionado;
	}

	public String getEstadoEvento() {
		return estadoEvento;
	}

	public void setEstadoEvento(String estadoEvento) {
		this.estadoEvento = estadoEvento;
	}

	public String getItrEventoSelccionado() {
		return itrEventoSelccionado;
	}

	public void setItrEventoSelccionado(String itrEventoSelccionado) {
		this.itrEventoSelccionado = itrEventoSelccionado;
	}

	public String getModalidadEventoSeleccionada() {
		return modalidadEventoSeleccionada;
	}

	public void setModalidadEventoSeleccionada(String modalidadEventoSeleccinada) {
		this.modalidadEventoSeleccionada = modalidadEventoSeleccinada;
	}

	public List<EstadosEventos> getEstadoEventos() {
		return estadoEventos;
	}

	public void setEstadoEventos(List<EstadosEventos> estadoEventos) {
		this.estadoEventos = estadoEventos;
	}

	public List<EstadosEventos> getFilteredestadoEventos() {
		return filteredestadoEventos;
	}

	public void setFilteredestadoEventos(List<EstadosEventos> filteredestadoEventos) {
		this.filteredestadoEventos = filteredestadoEventos;
	}

	public List<ModalidadesEventos> getModalidadEventos() {
		return modalidadEventos;
	}

	public void setModalidadEventos(List<ModalidadesEventos> modalidadEventos) {
		this.modalidadEventos = modalidadEventos;
	}

	public List<ModalidadesEventos> getFilteredModalidadEventos() {
		return filteredModalidadEventos;
	}

	public void setFilteredModalidadEventos(List<ModalidadesEventos> filteredModalidadEventos) {
		this.filteredModalidadEventos = filteredModalidadEventos;
	}

	

}