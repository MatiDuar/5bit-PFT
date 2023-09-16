package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Carrera;
import com.persistencia.exception.ServicesException;


/**
 * Session Bean implementation class CarreraBean
 */
@Stateless
@LocalBean
public class CarreraDAO{

    public CarreraDAO() {
    }
    @PersistenceContext
	private EntityManager em;

	
	public void crearCarrera(Carrera carrera) throws ServicesException {

		try {

			em.merge(carrera);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	
	public void borrar(Long id) throws ServicesException {
		try {
			Carrera carrera = em.find(Carrera.class, id);

			em.remove(carrera);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la Carrera");
		}
	}

	
	public Carrera buscarCarreraPorId(Long id) throws ServicesException {

		try {

			Carrera c = em.find(Carrera.class, id);

			return c;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Carrera");
		}
	}

	
	public void modificar(Carrera carrera) throws ServicesException {

		try {

			em.merge(carrera);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Carrera");
		}
	}

	
	public List<Carrera> obtenerCarreras() throws ServicesException {

		try {

			TypedQuery<Carrera> query = em.createQuery("SELECT DISTINCT c FROM Carrera c", Carrera.class);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Carreras");
		}

	}
	
	
	public Carrera obtenerCarreraPorNombre(String nombre) throws ServicesException {
		
		try {
		
			TypedQuery<Carrera> query = em.createQuery("SELECT c FROM Carrera c WHERE c.nombre=:nombre",Carrera.class)
					.setParameter("nombre", nombre);
		
			return query.getSingleResult();
		
		}catch(PersistenceException e) {
			return null;
		}
		
	}

}
