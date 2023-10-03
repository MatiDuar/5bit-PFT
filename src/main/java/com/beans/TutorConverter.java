package com.beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.persistencia.dao.TutorDAO;
import com.persistencia.entities.Tutor;
import com.persistencia.exception.ServicesException;

@FacesConverter("tutorConverter")
public class TutorConverter implements Converter {

	@Inject
	TutorDAO tutorDAO;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		// TODO Auto-generated method stub

		if (string != null && !string.isEmpty()) {

			try {
				Long usuarioId = Long.parseLong(string);
				return tutorDAO.buscarTutorPorId(usuarioId); // Implement this method

			} catch (NumberFormatException e) {
				throw new ConverterException("Invalid format for Usuario.");
			} catch (ServicesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		// TODO Auto-generated method stub
		if (object == null) {
            return "";
        }
		 return String.valueOf(object);
	}

}
