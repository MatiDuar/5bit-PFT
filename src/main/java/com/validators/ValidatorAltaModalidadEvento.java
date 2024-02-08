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

import com.logicaNegocio.GestionMantenimientoEventoService;
import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.ModalidadesEventos;

@ManagedBean
@SessionScoped // JEE
@FacesValidator(value = "validatorAltaModalidadEvento", managed = true) // JEE8
//@ConversationScoped

public class ValidatorAltaModalidadEvento implements Validator<String>, Serializable{


	private static final long serialVersionUID = 1L;
	@Inject
	GestionMantenimientoEventoService persistenciaBean;

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, String arg2) throws ValidatorException {

		// ###### Control de espacios vacios ######
		Pattern formatoNombre = null;
	    Matcher matcher = null;
		
		String nombreITR = arg2;
		
		formatoNombre= Pattern.compile("^(?!\\s*$).+");
		matcher= formatoNombre.matcher(nombreITR);
		
		if (!matcher.matches()) {
			throw new ValidatorException(new FacesMessage("No puede contener solo espacios vacíos"));
		}
		// ##########################################
		
		
		// ###### control de nombre repetidos ######
		List<ModalidadesEventos> modalidades = new LinkedList<>();
		try {
			modalidades = persistenciaBean.obtenerModalidadesEventos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (ModalidadesEventos modalidad : modalidades) {
			if (modalidad.getNombre().toString().equalsIgnoreCase(arg2.toString())) {				
				FacesMessage message = new FacesMessage("No se pueden repetir nombres de Modalidades");
				throw new ValidatorException(message);
				
			}
		}
		// ##########################################
	}
	
}
