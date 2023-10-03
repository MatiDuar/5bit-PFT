package com.beans;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

@FacesConverter("timestampConverter")
public class TimestampConverter implements Converter {
	private static final String DATE_FORMAT = "yyyy-dd-MMHH:mm:ss"; // Customize the format as needed

	@Override
    public Object getAsObject(FacesContext facesContext, 
                              UIComponent uIComponent, 
                              String string) {
    	 
		
		if (string != null && !string.isEmpty()) {
             try {
            	 SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                 Date date = sdf.parse(string);
                 return new Timestamp(date.getTime());
             } catch (Exception e) {
                 // Handle parsing or conversion errors
                 e.printStackTrace();
             }
         }
         return null;
    }

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {
		if (object == null) {
			return null;
		}

		((Timestamp) object).toString();
		return object.toString();
	}
}
