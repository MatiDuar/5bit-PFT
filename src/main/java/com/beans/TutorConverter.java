package com.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.LocalBean;
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
public class TutorConverter implements Converter<Tutor> {

	@Inject
	PickListView service;

	@Override
	public Tutor getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		// TODO Auto-generated method stub

		if (string != null && !string.isEmpty()) {

			try {
				System.out.println(string);
				Long usuarioId = Long.parseLong(extractIdFromUsuarioString(string));
				System.out.println(PickListView.instance.buscarTutorPorId(usuarioId));
				
				return PickListView.instance.buscarTutorPorId(usuarioId); // Implement this method

			} catch (NumberFormatException e) {
				throw new ConverterException("Invalid format for Usuario.");
			} 
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Tutor object) {
		// TODO Auto-generated method stub
		if (object == null) {
            return "";
        }
		
		 return String.valueOf(object);
	}

	
	public String extractIdFromUsuarioString(String inputString) {
        // Define a regular expression pattern to match "id=x" where x is a number.
        Pattern pattern = Pattern.compile("id=(\\d+)");
        
        // Create a matcher to find the pattern in the input string.
        Matcher matcher = pattern.matcher(inputString);
        
        // Check if the pattern is found.
        if (matcher.find()) {
            // Extract and return the "id" value.
            return matcher.group(1);
        }
        
        // Return null if no "id" value is found.
        return null;
    }

	

}
