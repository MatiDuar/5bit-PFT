package com.validators;

import java.io.Serializable;
import java.util.List;
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

import com.beans.GestionITR;
import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.ITR;

@ManagedBean
@SessionScoped // JEE
@FacesValidator(value = "validatorModificarITR", managed = true) // JEE8
//@ConversationScoped
public class ValidatorModificarITR implements Validator<String>, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	GestionPersonaService persistenciaBean;
	
	@Inject
	GestionITR gestionITR;

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, String arg2) throws ValidatorException {

		List<ITR> itrs = new LinkedList<>();
		ITR itrAModificar = new ITR();
		try {
			itrAModificar = gestionITR.getItrAeditar();
			itrs = persistenciaBean.listarITRs();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (ITR itr : itrs) {
			if (itr.getNombre().toString().equalsIgnoreCase(arg2.toString()) 
					&& itrAModificar.getId() != itr.getId()) {				
				FacesMessage message = new FacesMessage("No se pueden repetir nombres de ITR");
				throw new ValidatorException(message);
				
			}
		}
	}

}