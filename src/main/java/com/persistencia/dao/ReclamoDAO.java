package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.ModalidadesEventos;
import com.persistencia.entities.Reclamo;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean
public class ReclamoDAO {

	public ReclamoDAO() {
	}

	@PersistenceContext
	private EntityManager em;

	public boolean crearReclamo(Reclamo reclamo) throws ServicesException {

		try {

			em.merge(reclamo);
			em.flush();
			return true;

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR el Reclamo");
		}
	}

	public boolean modificarReclamo(Reclamo reclamo) throws ServicesException {

		try {

			em.merge(reclamo);

			em.flush();
			return true;

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el Reclamo");
		}
	}

	public boolean borrarReclamo(Long id) throws ServicesException {

		try {

			Reclamo mod = em.find(Reclamo.class, id);

			em.remove(mod);
			em.flush();
			return true;

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el Reclamo");
		
		}
	}

	public Reclamo buscarReclamoPorId(Long id) throws ServicesException {

		try {

			Reclamo mod = em.find(Reclamo.class, id);

			return mod;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro el Reclamo");
		}
	}

	public List<Reclamo> obtenerReclamos() throws ServicesException {

		try {

			TypedQuery<Reclamo> query = em.createQuery("SELECT DISTINCT m FROM Reclamo m",
					Reclamo.class);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de Reclamos");
		}

	}


}
