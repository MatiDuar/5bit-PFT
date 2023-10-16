package com.logicaNegocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.EstadoDAO;
import com.persistencia.dao.ReclamoDAO;
import com.persistencia.entities.Estado;
import com.persistencia.entities.Reclamo;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean
public class GestionReclamoService {

	@EJB
	ReclamoDAO reclamoDAO;
	
	@EJB
	EstadoDAO estadoDAO;

	
	
	public List<Reclamo>listarReclamo() throws ServicesException{
		return reclamoDAO.obtenerReclamos();
	}
	
	public void modificar(Reclamo reclamo) throws ServicesException{
		reclamoDAO.modificarReclamo(reclamo);
	}
	
	public List<Estado>listarEstados() throws ServicesException{
		return estadoDAO.obtenerEstados();
	}
	
	public Estado buscarEstadoReclamo(String nombre) throws ServicesException {
		return estadoDAO.buscarNombreEstado(nombre);
	}
}
