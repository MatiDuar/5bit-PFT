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
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm"; // Customize the format as needed
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	
	@Override
    public Object getAsObject(FacesContext facesContext, 
                              UIComponent uIComponent, 
                              String string) {
    	 
		
		if (string != null && !string.isEmpty()) {
             try {
            	 
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

		Timestamp objetoTimeStamp = ((Timestamp) object);
		//format.format(utilDate)
		return sdf.format(objetoTimeStamp);
	}
}
