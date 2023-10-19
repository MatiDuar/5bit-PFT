package com.logicaNegocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.AccionReclamoDAO;
import com.persistencia.dao.EstadoDAO;
import com.persistencia.dao.ReclamoDAO;
import com.persistencia.entities.AccionReclamo;
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
	
	@EJB
	AccionReclamoDAO accionReclamoDAO;

	
	
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
	
	public void crearAccionReclamo(AccionReclamo accion) throws ServicesException {
		accionReclamoDAO.crearAccionReclamo(accion);
	}
	
	public void crearEstadoReclamo(Estado estado) throws ServicesException {
		estadoDAO.crearEstado(estado);
	}
	
	public void darDeBajaReclamo(Long id) throws ServicesException {
		reclamoDAO.borrarReclamo(id);
	}
	
	public List<AccionReclamo>buscarAccionesPorReclamo(Reclamo reclamo) throws ServicesException{
		return accionReclamoDAO.obtenerAccionPorReclamo(reclamo);
	}
}
