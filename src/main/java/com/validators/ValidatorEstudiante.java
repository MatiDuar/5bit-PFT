package com.validators;

import java.io.Serializable;
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

import com.beans.GestionPersona;
import com.logicaNegocio.GestionPersonaService;



@ManagedBean
@SessionScoped // JEE
@FacesValidator(value = "validatorEstudiante",managed=true) // JEE8
//@ConversationScoped
public class ValidatorEstudiante implements Validator<Integer>,Serializable{
	/*
	 * * *
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@Inject
	GestionPersonaService persistenciaBean;

	@Inject
	GestionPersona gestionPersona;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Integer arg2) throws ValidatorException {
		
		Integer anio = arg2;
		
		Calendar c = Calendar.getInstance();
		Integer anioActual= c.get(Calendar.YEAR);
		
		System.out.println(anio);
		System.out.println(anioActual);
		System.out.println(anio>anioActual);
		if (anio>anioActual) {
			System.out.println("paso");
			throw new ValidatorException(new FacesMessage("El año de ingreso es mayor al año actual"));
		}
		
	
		
	}
	
}