package com.logicaNegocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.EventoDAO;
import com.persistencia.dao.ModalidadesEventosDAO;
import com.persistencia.dao.TipoActividadDAO;
import com.persistencia.entities.Evento;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.TipoActividad;
import com.persistencia.exception.ServicesException;


@Stateless
@LocalBean
public class GestionEventoService {
	
	
	@EJB
	EventoDAO eventoDAO;
	
	@EJB
	TipoActividadDAO tipoActividadDAO;
	
	@EJB
	ModalidadesEventosDAO modalidadesEventosDAO;
	
	public void crearEvento(Evento evento) {
		try {
			eventoDAO.crearEvento(evento);
		} catch (ServicesException e) {
			e.printStackTrace();
		}
	}
	
	public List<Evento>listarEventos(){
		try {
			return eventoDAO.obtenerEvento();
		} catch (ServicesException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<TipoActividad>listarTiposActividad(){
		try {
			return tipoActividadDAO.obtenerTipoActividad();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<ModalidadesEventos>listarModadlidadesEvento(){
		try {
			return modalidadesEventosDAO.obtenerModalidadesEventos();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
