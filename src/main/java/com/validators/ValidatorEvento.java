package com.validators;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.beans.GestionEventos;
import com.beans.GestionPersona;
import com.logicaNegocio.GestionEventoService;
import com.logicaNegocio.GestionPersonaService;



@ManagedBean
@SessionScoped // JEE
@FacesValidator(value = "validatorEvento",managed=true) // JEE8
//@ConversationScoped
public class ValidatorEvento implements Validator<Timestamp>,Serializable{
	/*
	 * * *
	 * 
	 */

	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionEventoService persistenciaBean;

	@Inject
	GestionEventos gestionEventos;
	
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Timestamp fecFinal) throws ValidatorException {
		// TODO Auto-generated method stub


		
		
		UIComponent fechaInicioComponent = arg1.findComponent("fechaInicio");
		
		Timestamp fecIni = (Timestamp) ((javax.faces.component.UIInput) fechaInicioComponent).getValue();
		
		System.out.println("################################");
		System.err.println("Formato Timestamp: " + fecIni);		
		System.out.println("Fecha de Inicio " +fecIni);
		System.out.println("Fecha de Fin "+ fecFinal);
		

		if (fecFinal.before(fecIni)) {
			throw new ValidatorException(new FacesMessage("Fecha de inicio mayor a fecha de finalización",null));
		}

		if (fecFinal.equals(fecIni)) {;
			throw new ValidatorException(new FacesMessage("Las fechas de inicio y finalización no pueden coincidir"),null);

		}

	}
}
