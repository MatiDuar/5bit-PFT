package com.validators;

import java.io.Serializable;
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
@FacesValidator(value = "validatorPersona",managed=true) // JEE8
//@ConversationScoped
public class ValidatorPersona implements Validator<String>,Serializable{
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
		
		Pattern formatoEmail = null;
	    Matcher matcher = null;
	    
	    String dominioAContener = "utec.edu.uy";
							  
		String usuario= arg2.split("@")[0];
		
		String dominio = arg2.substring(arg2.indexOf("@")+1);
		
		System.out.println(dominio);
		
		formatoEmail = Pattern.compile("^(?!\\s*$).+");
		matcher = formatoEmail.matcher(arg2);
		
		if (!matcher.matches()) {
			throw new ValidatorException(new FacesMessage("No puede contener solo espacios vacios"));
		}
		
		if(!dominio.contains(dominioAContener)) {
			throw new ValidatorException(new FacesMessage("Dominio del correo no pertenece a UTEC"));
		}
		
		formatoEmail = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
		matcher = formatoEmail.matcher(arg2);
		if (!matcher.matches())  {
			throw new ValidatorException(new FacesMessage("Formato de mail incorrecto."));
		}
		
		if (persistenciaBean.existeNombreUsuario(usuario)
				/*&& !gestionPersona.getUsuarioLogeado().getNombreUsuario().equals(usuario)*/) {
			throw new ValidatorException(new FacesMessage("Ya existe un usuario con ese Mail Institucional"));
		}
		
		
		
		

	}

	public void validateOnLista(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {

		String patternEmail = "^(?!\\s*$).+";
	

		if (!((String) arg2).matches(patternEmail)) {
			throw new ValidatorException(new FacesMessage("No puede contener solo espacios vacios "));
		}
		
	}
	



}
