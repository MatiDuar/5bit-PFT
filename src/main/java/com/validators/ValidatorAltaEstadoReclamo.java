package com.validators;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedList;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import com.beans.GestionMantenimientoEvento;
import com.logicaNegocio.GestionEventoService;
import com.logicaNegocio.GestionMantenimientoEventoService;
import com.logicaNegocio.GestionPersonaService;
import com.logicaNegocio.GestionReclamoService;
import com.persistencia.entities.Estado;

@ManagedBean
@SessionScoped // JEE
@FacesValidator(value = "validatorAltaEstadoReclamo", managed = true) // JEE8
//@ConversationScoped
public class ValidatorAltaEstadoReclamo implements Validator<String>, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	GestionReclamoService  persistenciaBean;

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, String arg2) throws ValidatorException {
		
		Pattern formatoNombre = null;
	    Matcher matcher = null;
		
		String nombreEstado = arg2;
		
		formatoNombre= Pattern.compile("^(?!\\s*$).+");
		matcher= formatoNombre.matcher(nombreEstado);
		
		if (!matcher.matches()) {
			throw new ValidatorException(new FacesMessage("No puede contener solo espacios vacíos"));
		}
		

		List<Estado> estadosReclamos = new LinkedList<>();
		try {
			estadosReclamos = persistenciaBean.listarEstados();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Estado estado : estadosReclamos) {
			if (estado.getNombre().toString().equalsIgnoreCase(arg2.toString())) {				
				FacesMessage message = new FacesMessage("No se pueden repetir nombres de Estado de Evento");
				throw new ValidatorException(message);
				
			}
		}
	}

}
