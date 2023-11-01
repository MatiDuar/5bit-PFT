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
@FacesValidator(value = "validatorContrasena",managed=true) // JEE8
//@ConversationScoped
public class ValidatorContrasena implements Validator<String>,Serializable{
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
	public void validate(FacesContext arg0, UIComponent arg1, String arg2) throws ValidatorException {
		
		Pattern formatoPassword = null;
	    Matcher matcher = null;
		
		String password = arg2;
		
		formatoPassword= Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).+$");
		matcher= formatoPassword.matcher(password);
		
		if(!(password.length()>= 8 && password.length()<= 16)) {
			throw new ValidatorException(new FacesMessage("Entre 8 y 16 caracteres"));
		}
		
		if (!matcher.matches()) {
			throw new ValidatorException(new FacesMessage("Debe contener letras y números"));
		}
		
	
		
	}
	
}