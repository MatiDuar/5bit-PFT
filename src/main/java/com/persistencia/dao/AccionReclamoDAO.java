package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.AccionReclamo;
import com.persistencia.entities.Analista;
import com.persistencia.entities.Reclamo;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean
public class AccionReclamoDAO {
	public AccionReclamoDAO() {
	}

	@PersistenceContext
	private EntityManager em;

	public void crearAccionReclamo(AccionReclamo accion) throws ServicesException {

		try {

			em.merge(accion);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR la accion");
		}
	}

	public void borrarAccionReclamo(Long id) throws ServicesException {
		try {
			AccionReclamo accion = em.find(AccionReclamo.class, id);

			em.remove(accion);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la accion reclamo");
		}
	}

	public AccionReclamo buscarAccionReclamoPorId(Long id) throws ServicesException {

		try {

			AccionReclamo accion = em.find(AccionReclamo.class, id);

			return accion;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Accion Reclamo");
		}
	}

	public void modificarAccionReclamo(AccionReclamo accion) throws ServicesException {

		try {

			em.merge(accion);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Accion Reclamo ");
		}
	}

	public List<AccionReclamo> obtenerAccionReclamo() throws ServicesException {

		try {

			TypedQuery<AccionReclamo> query = em.createQuery("SELECT DISTINCT a FROM AccionReclamo a", AccionReclamo.class);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de Acciones Reclamo");
		}

	}
	
	public List<AccionReclamo> obtenerAccionPorReclamo(Reclamo reclamo) throws ServicesException {

		try {

			TypedQuery<AccionReclamo> query = em.createQuery("SELECT DISTINCT a FROM AccionReclamo a where a.reclamo=:reclamo", AccionReclamo.class)
					.setParameter("reclamo", reclamo);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de Acciones Reclamo");
		}

	}
}
