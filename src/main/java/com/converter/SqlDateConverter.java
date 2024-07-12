package com.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("sqlDateConverter")
public class SqlDateConverter implements Converter<Object> {

    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Date utilDate = format.parse(value);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date Conversion Error", "Invalid date format"));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof java.sql.Date) {
            java.sql.Date sqlDate = (java.sql.Date) value;
            return format.format(new Date(sqlDate.getTime()));
        } else if (value instanceof java.util.Date) {
            java.util.Date utilDate = (java.util.Date) value;
            return format.format(utilDate);
        } else {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date Conversion Error", "Invalid date type"));
        }
    }
}
