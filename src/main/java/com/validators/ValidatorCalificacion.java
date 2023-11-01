package com.validators;

import java.io.Serializable;


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



@ManagedBean
@SessionScoped // JEE
@FacesValidator(value = "validatorCalificacion",managed=true) // JEE8
//@ConversationScoped
public class ValidatorCalificacion implements Validator<Float>,Serializable{
	/*
	 * * *
	 * 
	 */

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Float arg2) throws ValidatorException {
		// TODO Auto-generated method stub
		
		if(!(arg2>=1.00 && arg2<=5.00)) {
			throw new ValidatorException(new FacesMessage("Calificación debe estar comprendida entre 1.00 y 5.00"));
		}
		
		
	}
	

	
}
