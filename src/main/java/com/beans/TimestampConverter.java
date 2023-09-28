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

    @Override
    public Object getAsObject(FacesContext facesContext, 
                              UIComponent uIComponent, 
                              String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:KK:ss");
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(string);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, now.get(Calendar.SECOND));
        Timestamp result = new Timestamp(calendar.getTime().getTime());
        return result;
    }

    @Override
    public String getAsString(FacesContext facesContext, 
                              UIComponent uIComponent, 
                              Object object) {
        if (object == null) {
            return null;
        }    
        
        ((Timestamp )object).toString();
        return object.toString();
    }
}
