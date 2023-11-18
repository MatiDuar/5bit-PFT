package com.validators;

import java.io.Serializable;
import java.util.Arrays;
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
@FacesValidator(value = "validatorCedula",managed=true) // JEE8
//@ConversationScoped
public class ValidatorCedula implements Validator<String>,Serializable{
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
		
		String cedula = arg2;
		
		if(cedula.length()!=8) {
			throw new ValidatorException(new FacesMessage("Debe tener 8 digitos"));
		}
		
		char[] digitos = cedula.toCharArray();
		
		char digitoVerificador = digitos[7];
		
		
			
		char[] sinVerificador= new char[7];
		System.arraycopy(digitos, 0, sinVerificador, 0, 7);
		
		
		int[] constantes = {2,9,8,7,6,3,4};
		
		Integer suma = 0;
		
		for(int i = 0;i<sinVerificador.length;i++) {
			suma+=Integer.parseInt(String.valueOf(sinVerificador[i]))*constantes[i];
		}
		
		int formula = (10 - (suma%10))%10;
		
		
		if(Integer.parseInt(String.valueOf(digitoVerificador))!=formula) {
			throw new ValidatorException(new FacesMessage("Cédula no es válida"));
		}
		
		
	}
	
}
