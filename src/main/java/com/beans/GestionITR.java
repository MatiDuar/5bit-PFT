package com.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.logicaNegocio.GestionITRService;
import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.ITR;
import com.persistencia.entities.Usuario;
import com.persistencia.exception.ServicesException;

@Named(value = "gestionITR") // JEE8
@SessionScoped // JEE8
public class GestionITR implements Serializable {

	@Inject
	GestionITRService persistenciaBean;
	@Inject
	GestionPersona gestionPersona;
	
	@Inject
	DFView dfView;
	
	
	private ITR itrSeleccionado;
	
	private ITR itrAeditar;
	
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init() {
		itrSeleccionado=new ITR();
	}
	
	public void onRowEdit(RowEditEvent<ITR> itr) {
		
		persistenciaBean.crearITR(itr.getObject());
		gestionPersona.updateITRsActivos();
	
			
	}
	
	
	public void onRowCancel(RowEditEvent<ITR> itr) {
		
	}
	
	
	public String estadoToString(ITR itr) {
		if(itr.getActivo()) {
			return "Activo";
		}else {
			return "Inactivo";
		}
		
	}
	
	public void crearITR() {
		try {
			itrSeleccionado.setActivo(true);
			persistenciaBean.crearITR(itrSeleccionado);
			itrSeleccionado=new ITR();
			gestionPersona.updateITRs();
			closeVentana();
			closeVentana();
			
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void modificarITR() {
		try {
			itrSeleccionado.setActivo(true);
			persistenciaBean.crearITR(itrSeleccionado);
			itrSeleccionado=new ITR();
			gestionPersona.updateITRsActivos();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void cargarITRAEditar(ITR itr) {
		itrAeditar = itr;
	}
	
	
	public void mostrarMantenimientoITR() {
		dfView.viewMantenimientoITR();
	}
	
	public void closeVentana() {
		dfView.closeResponsive();
	}

	public ITR getItrSeleccionado() {
		return itrSeleccionado;
	}

	public void setItrSeleccionado(ITR itrSeleccionado) {
		this.itrSeleccionado = itrSeleccionado;
	}

	public ITR getItrAeditar() {
		return itrAeditar;
	}

	public void setItrAeditar(ITR itrAeditar) {
		this.itrAeditar = itrAeditar;
	}
	
	

}
