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
import com.persistencia.dto.PersonaAlumnoDTO;
import com.persistencia.entities.Carrera;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Usuario;

@Named("dtFilterView")
@ViewScoped
public class FilterView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private GestionPersonaService service;

	private String carreraSel;

	private String itrSeleccionado;

	private String tipoUsuarioSeleccionado;
	
	private String anoIngresoSeleccionado;

	private List<Usuario> personas;

	private List<Usuario> filteredPersonas;

	private List<FilterMeta> filterBy;

	private boolean globalFilterOnly;

	private int currentYear;

	@PostConstruct
	public void init() {
		globalFilterOnly = true;
		try {
			personas = service.listarPersonas();
			itrSeleccionado = "";
			tipoUsuarioSeleccionado = "";
			anoIngresoSeleccionado= "";
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
		if (LangUtils.isBlank(filterText) && itrSeleccionado.isBlank() && tipoUsuarioSeleccionado.isBlank() && anoIngresoSeleccionado.isBlank()) {
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
		if(!(persona instanceof Estudiante) && !anoIngresoSeleccionado.isBlank()) {
			return false;
		}
		
			System.out.println(anoIngresoSeleccionado);
			return (persona.getNombre1().toLowerCase().contains(filterText)
					|| persona.getApellido1().toLowerCase().contains(filterText)
					|| persona.getNombreUsuario().toLowerCase().contains(filterText)
					|| persona.getMail().toString().toLowerCase().contains(filterText)
					|| persona.getLocalidad().toLowerCase().contains(filterText))
					|| (((Estudiante) persona).getAnoIngreso()+"").equals(anoIngresoSeleccionado);

	}

	public void toggleGlobalFilter() {
		setGlobalFilterOnly(!isGlobalFilterOnly());
	}

//	public boolean esAlumno(Persona p) {
//		return service.buscarAlumno(p.getId())!=null;
//	}

	private int getInteger(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

//	public void buscarCarrera(Alumno a,String carrera) {
//		a.setCarrera(service.buscarCarrera(carrera));
//	}
//	
//	public Carrera buscarCarrera(String nombre) {
//		return service.buscarCarrera(nombre);
//	}

	public Usuario buscar(long id) {
		return service.buscarUsuario(id);
	}

	public int getEdad(String fecha) {
		String str[] = fecha.split("-");

		return currentYear - Integer.parseInt(str[0]);
	}

//	public List<PersonaAlumnoDTO> getPersonas() {
//		try {
//			personas=service.listarPersonasDTO();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return personas;
//	}
//
//	public void setPersonas(List<PersonaAlumnoDTO> personas) {
//		this.personas = personas;
//	}
//
//	public List<PersonaAlumnoDTO> getFilteredPersonas() {
//		return filteredPersonas;
//	}
//
//	public void setFilteredPersonas(List<PersonaAlumnoDTO> filteredPersonas) {
//		this.filteredPersonas = filteredPersonas;
//	}

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
	
	

}