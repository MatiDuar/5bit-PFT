package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Estado;
import com.persistencia.entities.EstadosEventos;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean
public class EstadoDAO {

	@PersistenceContext
	private EntityManager em;

	public void crearEstado(Estado estado) throws ServicesException {

		try {

			em.merge(estado);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR el estado");
		}
	}

	public void modificarEstado(Estado estado) throws ServicesException {

		try {

			em.merge(estado);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el estado");
		}
	}

	public void borrarEstado(Long id) throws ServicesException {

		try {

			Estado estado = em.find(Estado.class, id);

			em.remove(estado);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	public Estado buscarEstadoPorId(Long id) throws ServicesException {

		try {

			Estado estado = em.find(Estado.class, id);

			return estado;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro el estado");
		}
	}

	public List<Estado> obtenerEstados() throws ServicesException {

		try {

			TypedQuery<Estado> query = em.createQuery("SELECT DISTINCT e FROM Estado e",
					Estado.class);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de estados");
		}

	}

	public Estado buscarNombreEstado(String nombre) throws ServicesException {

		try {

			TypedQuery<Estado> query = em
					.createQuery("SELECT e FROM Estado e WHERE e.nombre = :nombre",
							Estado.class)
					.setParameter("nombre", nombre);

			return query.getSingleResult();

		} catch (PersistenceException e) {

			return null;

		}
	}
}
