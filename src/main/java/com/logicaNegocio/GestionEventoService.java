package com.logicaNegocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.EventoDAO;

import com.persistencia.entities.Evento;
import com.persistencia.exception.ServicesException;


@Stateless
@LocalBean
public class GestionEventoService {
	
	
	@EJB
	EventoDAO eventoDAO;
	
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
}
