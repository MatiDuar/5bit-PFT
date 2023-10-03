package com.logicaNegocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.EstadosEventosDAO;
import com.persistencia.dao.EventoDAO;
import com.persistencia.dao.ModalidadesEventosDAO;
import com.persistencia.dao.TipoActividadDAO;
import com.persistencia.dao.TutorDAO;
import com.persistencia.dao.UsuarioDAO;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.Evento;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.TipoActividad;
import com.persistencia.entities.Tutor;
import com.persistencia.entities.Usuario;
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

	@EJB
	EstadosEventosDAO estadosEventosDAO;

	@EJB
	UsuarioDAO usuarioDAO;
	
	@EJB
	TutorDAO tutorDAO;

	public void crearEvento(Evento evento) {
		try {
			eventoDAO.crearEvento(evento);
		} catch (ServicesException e) {
			e.printStackTrace();
		}
	}

	public List<Evento> listarEventos() {
		try {
			return eventoDAO.obtenerEvento();
		} catch (ServicesException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<TipoActividad> listarTiposActividad() {
		try {
			return tipoActividadDAO.obtenerTipoActividad();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<ModalidadesEventos> listarModadlidadesEvento() {
		try {
			return modalidadesEventosDAO.obtenerModalidadesEventos();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<EstadosEventos> listarEstadosEventos() {
		try {
			return estadosEventosDAO.obtenerEstadosEventos();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Usuario buscarUsuario(String nombreUsuario) {
		try {
			return usuarioDAO.buscarNombre(nombreUsuario);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public EstadosEventos buscarEstadoEvento(String nombre) {
		try {
			return estadosEventosDAO.buscarNombreEstadoEvento(nombre);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Tutor>listarTutores()throws Exception{
		return tutorDAO.obtenerTutor();
	}
}
