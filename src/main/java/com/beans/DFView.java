package com.beans;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import com.persistencia.entities.Usuario;

@Named("dfView")
@RequestScoped
public class DFView {
	
	@Inject
	GestionITR gestionITR;
	

	
	
	  public void viewResponsive() {
		  
		  
	        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
	                .modal(true)
	                .fitViewport(true)
	                .responsive(true)
	                .width("700px")
	                .contentWidth("100%")
	                .resizeObserver(true)
	                .resizeObserverCenter(true)
	                .resizable(false)
	                .styleClass("max-w-screen")
	                .iframeStyleClass("max-w-screen")
	                .build();

	        PrimeFaces.current().dialog().openDynamic("altaITR", options, null);
	    }
	  
	  public void viewMantenimientoITR() {
	        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
	                .modal(true)
	                .fitViewport(true)
	                .responsive(true)
	                .width("1100px")
	                .height("700px")
	                .contentWidth("100%")
	                .contentHeight("100%")
	                .resizeObserver(true)
	                .resizeObserverCenter(true)
	                .resizable(false)
	                .styleClass("max-w-screen")
	                .iframeStyleClass("max-w-screen")

	                .build();

	        PrimeFaces.current().dialog().openDynamic("mantenimientoITR", options, null);
	    }
	  
	  public void viewMantenimientoEstadosEventos() {
	        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
	                .modal(true)
	                .fitViewport(true)
	                .responsive(true)
	                .width("1100px")
	                .height("700px")
	                .contentWidth("100%")
	                .contentHeight("100%")
	                .resizeObserver(true)
	                .resizeObserverCenter(true)
	                .resizable(false)
	                .styleClass("max-w-screen")
	                .iframeStyleClass("max-w-screen")

	                .build();

	        PrimeFaces.current().dialog().openDynamic("mantenimientoEstadoEventos", options, null);
	    }
	  public void viewMantenimientoModalidadEventos() {
	        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
	                .modal(true)
	                .fitViewport(true)
	                .responsive(true)
	                .width("1100px")
	                .height("700px")
	                .contentWidth("100%")
	                .contentHeight("100%")
	                .resizeObserver(true)
	                .resizeObserverCenter(true)
	                .resizable(false)
	                .styleClass("max-w-screen")
	                .iframeStyleClass("max-w-screen")

	                .build();

	        PrimeFaces.current().dialog().openDynamic("mantenimientoModalidadEvento", options, null);
	    }
	  
	  public void viewEstadoUsuario() {
	        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
	                .modal(true)
	                .fitViewport(true)
	                .responsive(true)
	                .width("500px")
	                .contentWidth("100%")
	             
	                .resizeObserver(true)
	                .resizeObserverCenter(true)
	                .resizable(false)
	                .styleClass("max-w-screen")
	                .iframeStyleClass("max-w-screen")

	                .build();

	        PrimeFaces.current().dialog().openDynamic("estadoUsuario", options, null);
	    }
	  
	  
		public void viewAsignarTutores() {
		  
		  
	        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
	                .modal(true)
	                .fitViewport(true)
	                .responsive(true)
	                .width("1300px")
	                .height("80%")
	                .contentWidth("100%")
	                .resizeObserver(true)
	                .resizeObserverCenter(true)
	                .resizable(false)
	                .styleClass("max-w-screen")
	                .iframeStyleClass("max-w-screen")
	                .build();

	        PrimeFaces.current().dialog().openDynamic("asignarTutores", options, null);
	    }
		
		 public void viewAltaEstadoEvento() {
			  
			  
		        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
		                .modal(true)
		                .fitViewport(true)
		                .responsive(true)
		                .width("700px")
		                .contentWidth("100%")
		                .resizeObserver(true)
		                .resizeObserverCenter(true)
		                .resizable(false)
		                .styleClass("max-w-screen")
		                .iframeStyleClass("max-w-screen")
		                .build();

		        PrimeFaces.current().dialog().openDynamic("altaEstadoEvento", options, null);
		    }
		 
		 public void viewAltaModalidadEvento() {
			  
			  
		        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
		                .modal(true)
		                .fitViewport(true)
		                .responsive(true)
		                .width("700px")
		                .contentWidth("100%")
		                .resizeObserver(true)
		                .resizeObserverCenter(true)
		                .resizable(false)
		                .styleClass("max-w-screen")
		                .iframeStyleClass("max-w-screen")
		                .build();

		        PrimeFaces.current().dialog().openDynamic("altaModalidadEvento", options, null);
		    }
	  
	  
	  public void closeResponsive() {
		  PrimeFaces.current().dialog().closeDynamic(null);
	  }
	  
	  
	  public void updateMantenimientoITR() {
		  
		  PrimeFaces.current().dialog().closeDynamic(null);
		  gestionITR.mostrarMantenimientoITR();
	  }
}
