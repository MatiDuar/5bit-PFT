package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.exception.ServicesException;
import com.persistencia.entities.Materia;



/**
 * Session Bean implementation class MateriaBean
 */
@Stateless
@LocalBean

public class MateriaDAO {

    public MateriaDAO() {
    }
    
    @PersistenceContext
	private EntityManager em;

	
	public void crearMateria(String nombre) throws ServicesException {

		try {
			Materia materia=new Materia();
			materia.setNombre(nombre);
			em.merge(materia);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR la Materia");
		}
	}
	
	
	public void crearMateria(Materia materia) throws ServicesException {

		try {

			em.merge(materia);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR la Materia");
		}
	}

	
	public void borrar(Long id) throws ServicesException {
		try {
			Materia materia = em.find(Materia.class, id);

			em.remove(materia);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la Escolaridad");
		}
	}

	
	public Materia buscarMateriaPorId(Long id) throws ServicesException {

		try {

			Materia e = em.find(Materia.class, id);

			return e;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Materia");
		}
	}

	
	public void modificar(Materia materia) throws ServicesException {

		try {

			em.merge(materia);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Materia");
		}
	}

	
	public List<Materia> obtenerMaterias() throws ServicesException {

		try {

			TypedQuery<Materia> query = em.createQuery("SELECT DISTINCT m FROM Materia m", Materia.class);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Materias");
		}

	}
	
	
	public Materia obtenerMateriaPorNombre(String nombre) throws ServicesException {
		
		try {
		
			TypedQuery<Materia> query = em.createQuery("SELECT m FROM Materia m WHERE m.nombre=:nombre",Materia.class)
					.setParameter("nombre", nombre);
		
			return query.getSingleResult();
		
		}catch(PersistenceException e) {
			return null;
		}
		
	}

}
