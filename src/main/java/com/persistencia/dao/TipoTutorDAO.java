
package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.exception.ServicesException;
import com.persistencia.entities.TipoTutor;

/**
 * Session Bean implementation class TipoTutor
 */
@Stateless
@LocalBean

public class TipoTutorDAO {

	public TipoTutorDAO() {
	}

	@PersistenceContext
	private EntityManager em;

	public void crearTipoTutor(String nombre) throws ServicesException {

		try {
			TipoTutor tipoTutor = new TipoTutor();
			tipoTutor.setNombre(nombre);
			em.persist(tipoTutor);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR el Tipo de tutor");
		}
	}

	
	public void borrarTipoTutor(Long id) throws ServicesException {
		try {
			TipoTutor tipoTutor = em.find(TipoTutor.class, id);

			em.remove(tipoTutor);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el Tipo de tutor");
		}
	}

	
	public TipoTutor buscarTipoTutorPorId(Long id) throws ServicesException {

		try {

			TipoTutor ttp = em.find(TipoTutor.class, id);

			return ttp;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro el Tipo de tutor");
		}
	}

	
	public void modificarTipoTutor(TipoTutor tipoTutor) throws ServicesException {

		try {

			em.merge(tipoTutor);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el Tipo de tutor");
		}
	}

	
	public List<TipoTutor> obtenerTipoTutor() throws ServicesException {

		try {

			TypedQuery<TipoTutor> query = em.createQuery("SELECT DISTINCT t FROM TipoTutor t", TipoTutor.class);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de Tipos de tutor");
		}

	}

	
	public TipoTutor obtenerTipoTutorPorNombre(String nombre) throws ServicesException {

		try {

			TypedQuery<TipoTutor> query = em
					.createQuery("SELECT t FROM TipoTutor t WHERE t.nombre=:nombre", TipoTutor.class)
					.setParameter("nombre", nombre);

			return query.getSingleResult();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener el Tipo Tutor");
		}

	}

}
