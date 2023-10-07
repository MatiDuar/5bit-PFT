package com.logicaNegocio;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.EstadosEventosDAO;
import com.persistencia.dao.ModalidadesEventosDAO;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.ITR;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean
public class GestionMantenimientoEventoService {

	@EJB
	EstadosEventosDAO persistenciaBean;
	
	@EJB
	ModalidadesEventosDAO modalidadesDAO;

	public void crearEstadoEvento(EstadosEventos es) {

		try {

			persistenciaBean.crearEstadoEvento(es);
		} catch (ServicesException e) {
			e.printStackTrace();
		}
	}
	
	public void crearModalidadEvento(ModalidadesEventos me) {

		try {

			modalidadesDAO.crearModalidadEvento(me);
		} catch (ServicesException e) {
			e.printStackTrace();
		}
	}
}
