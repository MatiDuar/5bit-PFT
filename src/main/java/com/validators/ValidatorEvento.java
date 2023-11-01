package com.validators;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
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
	public void validate(FacesContext arg0, UIComponent arg1, Timestamp arg2) throws ValidatorException {
		// TODO Auto-generated method stub
		
	}
	
	public void validateFechaFinal(FacesContext arg0, UIComponent arg1, Timestamp arg2) throws ValidatorException {
			
		Timestamp fechaInicioEvento = gestionEventos.getFechaInicioEvento();
		
		System.out.println(arg2);
		System.out.println(fechaInicioEvento);
		
		if(arg2.before(fechaInicioEvento)) {
			throw new ValidatorException(new FacesMessage("Fecha de finalización mayor a fecha de inico"));
		}
		
		
		if(arg2.equals(fechaInicioEvento)) {
			
			throw new ValidatorException(new FacesMessage("Las fechas de inicio y finalización no pueden coincidir"));
			
		}
		
		if(arg2.getYear() == fechaInicioEvento.getYear() && arg2.getMonth() == fechaInicioEvento.getMonth() && arg2.getDay() == fechaInicioEvento.getDay()){
			if(arg2.getHours()<= fechaInicioEvento.getHours()) {
				throw new ValidatorException(new FacesMessage("La hora de finalización no puede ser menor a la hora de inicio (Horas)"));
			}else if(arg2.getMinutes()<= fechaInicioEvento.getMinutes()) {
				throw new ValidatorException(new FacesMessage("La hora de finalización no puede ser menor a la hora de inicio (Minutos)"));
			}
		}
			
		
		
	
		
	}

	
	
}
