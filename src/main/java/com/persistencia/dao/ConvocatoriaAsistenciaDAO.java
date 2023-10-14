package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.ConvocatoriaAsistencia;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean

public class ConvocatoriaAsistenciaDAO {

	public ConvocatoriaAsistenciaDAO() {

	}

	@PersistenceContext
	private EntityManager em;

	public void crear(ConvocatoriaAsistencia convocatoriaAsistencia) throws ServicesException {

		try {

			em.persist(convocatoriaAsistencia);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR la Convocatoria Asistencia");
		}
	}

	public void borrar(Long id) throws ServicesException {
		try {
			ConvocatoriaAsistencia c = em.find(ConvocatoriaAsistencia.class, id);

			em.remove(c);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la Convocatoria Asistencia");
		}
	}

	public ConvocatoriaAsistencia buscarPorId(Long id) throws ServicesException {

		try {

			ConvocatoriaAsistencia c = em.find(ConvocatoriaAsistencia.class, id);

			return c;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Convocatoria Asistencia");
		}
	}

	public void modificar(ConvocatoriaAsistencia c) throws ServicesException {

		try {

			em.merge(c);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Convocatoria Asistencia");
		}
	}

	public void modificar(List<ConvocatoriaAsistencia> convocatorias) throws ServicesException {

		try {

			for(ConvocatoriaAsistencia ca:convocatorias) {
				em.merge(ca);
			}

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Convocatoria Asistencia");
		}
	}

	public List<ConvocatoriaAsistencia> obtenerTodos() throws ServicesException {

		try {

			TypedQuery<ConvocatoriaAsistencia> query = em.createQuery("SELECT DISTINCT c FROM ConvocatoriaAsistencia c",
					ConvocatoriaAsistencia.class);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Convocatoria Asistencia");
		}

	}

	public List<Estudiante> buscarPorEvento(Evento evento) throws ServicesException {

		try {

			TypedQuery<Estudiante> query = em
					.createQuery("SELECT c.estudiante FROM ConvocatoriaAsistencia c WHERE c.evento =:evento",
							Estudiante.class)
					.setParameter("evento", evento);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la de estudiantes asignados a la Convocatoria Asistencia");
		}

	}

	public List<ConvocatoriaAsistencia> buscarConvocatoriasPorEvento(Evento evento) throws ServicesException {

		try {

			TypedQuery<ConvocatoriaAsistencia> query = em
					.createQuery("SELECT c FROM ConvocatoriaAsistencia c WHERE c.evento =:evento",
							ConvocatoriaAsistencia.class)
					.setParameter("evento", evento);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la de estudiantes asignados a la Convocatoria Asistencia");
		}

	}

	public ConvocatoriaAsistencia buscarPorEstudianteEvento(Estudiante estudiante, Evento evento)
			throws ServicesException {
		try {
			TypedQuery<ConvocatoriaAsistencia> query = em.createQuery(
					"SELECT e FROM ConvocatoriaAsistencia e WHERE e.estudiante = :estudiante AND e.evento=:evento",
					ConvocatoriaAsistencia.class).setParameter("estudiante", estudiante).setParameter("evento", evento);
			return query.getSingleResult();
		} catch (PersistenceException e) {

			return null;

		}
	}

	public void borrar(Estudiante estudiante, Evento evento) throws ServicesException {

		try {

			em.remove(buscarPorEstudianteEvento(estudiante, evento));
			em.flush();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo borrar la Convocatoria Asistencia");

		}
	}

}
