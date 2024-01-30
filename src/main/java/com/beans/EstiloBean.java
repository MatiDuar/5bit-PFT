package com.beans;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("estiloBean")
@SessionScoped 
public class EstiloBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean mostrarFondo = true;

    public boolean isMostrarFondo() {
        return mostrarFondo;
    }

    public void setMostrarFondo(boolean mostrarFondo) {
        this.mostrarFondo = mostrarFondo;
    }

    public void agregarFondo() {
        this.mostrarFondo = true;
        System.out.println("MostrarFondo" + mostrarFondo);
    }
    
    public void sacarFondo() {
    	this.mostrarFondo = false;
        System.out.println("MostrarFondo"+ mostrarFondo);
    }
}