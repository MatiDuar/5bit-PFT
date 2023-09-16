package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Inscripcion;
import com.persistencia.exception.ServicesException;



/**
 * Session Bean implementation class InscripcionBean
 */
@Stateless
@LocalBean

public class InscripcionDAO {


    public InscripcionDAO() {
    }
    @PersistenceContext
	private EntityManager em;

	
	public void crearInscripcion(Inscripcion inscripcion) throws ServicesException {

		try {

			em.merge(inscripcion);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR la Inscripcion");
		}
	}

	
	public void borrar(Long id) throws ServicesException {
		try {
			Inscripcion inscripcion = em.find(Inscripcion.class, id);

			em.remove(inscripcion);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la Inscripcion");
		}
	}

	
	public Inscripcion buscarInscripcionPorId(Long id) throws ServicesException {

		try {

			Inscripcion i = em.find(Inscripcion.class, id);

			return i;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Inscripcion");
		}
	}

	
	public void modificar(Inscripcion inscripcion) throws ServicesException {

		try {

			em.merge(inscripcion);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Inscripcion");
		}
	}

	
	public List<Inscripcion> obtenerInscripciones() throws ServicesException {

		try {

			TypedQuery<Inscripcion> query = em.createQuery("SELECT DISTINCT i FROM Inscripcion i", Inscripcion.class);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Inscripciones");
		}

	}
	
	
	public List<Inscripcion> obtenerInscripcionesPorEstudiante(Estudiante es) throws ServicesException {

		try {

			TypedQuery<Inscripcion> query = em.createQuery("SELECT  i FROM Inscripcion i WHERE i.estudiante=:estudiante", Inscripcion.class)
					.setParameter("estudiante", es);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Inscripciones");
		}

	}

}
