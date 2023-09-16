package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Matricula;
import com.persistencia.exception.ServicesException;



/**
 * Session Bean implementation class MatriculaBean
 */
@Stateless
@LocalBean

public class MatriculaDAO {

 
    public MatriculaDAO() {
    }
    @PersistenceContext
	private EntityManager em;

	
	public void crearMatricula(Matricula matricula) throws ServicesException {

		try {

			em.merge(matricula);
			em.flush();
			

		} catch (PersistenceException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	
	public void borrar(Long id) throws ServicesException {
		try {
			Matricula matricula = em.find(Matricula.class, id);

			em.remove(matricula);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la Matricula");
		}
	}

	
	public Matricula buscarMatriculaPorId(Long id) throws ServicesException {

		try {

			Matricula m = em.find(Matricula.class, id);

			return m;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Matricula");
		}
	}

	
	public void modificar(Matricula matricula) throws ServicesException {

		try {

			em.merge(matricula);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Matricula");
		}
	}

	
	public List<Matricula> obtenerMatriculas() throws ServicesException {

		try {

			TypedQuery<Matricula> query = em.createQuery("SELECT DISTINCT m FROM Matricula m", Matricula.class);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Matriculas");
		}

	}

	

}
