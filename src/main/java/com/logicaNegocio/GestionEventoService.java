package com.logicaNegocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

import com.persistencia.dao.ConvocatoriaAsistenciaDAO;
import com.persistencia.dao.EstadoAsistenciaDAO;
import com.persistencia.dao.EstadosEventosDAO;
import com.persistencia.dao.EstudianteDAO;
import com.persistencia.dao.EventoDAO;
import com.persistencia.dao.ModalidadesEventosDAO;
import com.persistencia.dao.ReclamoDAO;
import com.persistencia.dao.TipoActividadDAO;
import com.persistencia.dao.TutorDAO;
import com.persistencia.dao.UsuarioDAO;
import com.persistencia.entities.ConvocatoriaAsistencia;
import com.persistencia.entities.EstadoAsistencia;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.Reclamo;
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
	
	@EJB
	EstudianteDAO estudianteDAO;
	
	@EJB
	EstadoAsistenciaDAO estadoAsistenciaDAO;

	@EJB
	ConvocatoriaAsistenciaDAO convocatoriaAsistenciaDAO;
	
	@EJB
	ReclamoDAO reclamoDAO;
	
	public void crearEvento(Evento evento) {
		try {
			eventoDAO.crearEvento(evento);
		} catch (ServicesException e) {
			e.printStackTrace();
		}
	}
	
	public void modificarEvento(Evento evento) {
		try {
			eventoDAO.modificarEvento(evento);
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
	
	public List<ModalidadesEventos> listarModadlidadesEventoActivos() {
		try {
			return modalidadesEventosDAO.obtenerModalidadesEventosActivos();
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<EstadosEventos> listarEstadosEventos(){
		try {
			return estadosEventosDAO.obtenerEstadosEventos();
		} catch (ServicesException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public Tutor buscarTutorPorId(Long id) throws ServicesException {
		return tutorDAO.buscarTutorPorId(id);
	}
	
	public Evento buscarEventoPorId(Long id) throws ServicesException {
		return eventoDAO.buscarEventoPorId(id);
	}
	
	public Estudiante buscarEstudiantePorId(Long id) throws ServicesException {
		return estudianteDAO.buscarEstudiantePorId(id);
	}
	
	public boolean borrarEvento(Evento e) {
		try {
			return eventoDAO.borrarEvento(e.getId());
		} catch (ServicesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}

	
	public List<Estudiante> listarEstudiantes() {
		try {
			return estudianteDAO.obtenerEstudiantes();
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
	
	public EstadoAsistencia buscarEstadoAsistenciaPorNombre(String nombre) throws ServicesException {
		return estadoAsistenciaDAO.obtenerPorNombre(nombre);
	}
	
	public void crearConvocatoriaAsistencia(ConvocatoriaAsistencia ca) throws ServicesException {
		convocatoriaAsistenciaDAO.crear(ca);
	}
	
	
	public List<Estudiante>buscarEstudiantesPorEvento(Evento e) throws ServicesException{
		return convocatoriaAsistenciaDAO.buscarPorEvento(e);
	}
	
	public List<ConvocatoriaAsistencia>buscarConvocatoriaPorEvento(Evento e) throws ServicesException{
		return convocatoriaAsistenciaDAO.buscarConvocatoriasPorEvento(e);
	}
	
	public void borrarConvocatoria(Estudiante e,Evento evento) throws ServicesException {
		convocatoriaAsistenciaDAO.borrar(e, evento);
	}
	
	
	public List<EstadoAsistencia> listarEstadosAsistencia() throws ServicesException{
		return estadoAsistenciaDAO.obtenerTodos();
	}
	
	public void modificarConvocatoriaAsistencia(List<ConvocatoriaAsistencia>convocatorias) throws ServicesException {
		convocatoriaAsistenciaDAO.modificar(convocatorias);
	}
	
	public void modificarReclamo(Reclamo r) throws ServicesException {
		reclamoDAO.modificarReclamo(r);
	}
}
