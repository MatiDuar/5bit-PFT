package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.TipoAsignatura;
import com.persistencia.exception.ServicesException;



/**
 * Session Bean implementation class TipoAsignaturaBean
 */
@Stateless
@LocalBean

public class TipoAsignaturaDAO  {

    public TipoAsignaturaDAO() {
    }
    @PersistenceContext
	private EntityManager em;

	
	public void crearTipoAsignatura(TipoAsignatura tipoAsignatura) throws ServicesException {

		try {

			em.merge(tipoAsignatura);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR el Tipo Asignatura");
		}
	}
	
	
	public void crearTipoAsignatura(String nombre,boolean activo) throws ServicesException {

		try {
			TipoAsignatura ta=new TipoAsignatura();
			ta.setNombre(nombre);
			ta.setActivo(activo);
			em.merge(ta);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR el Tipo Asignatura");
		}
	}

	
	public void borrar(Long id) throws ServicesException {
		try {
			TipoAsignatura tipoAsignatura = em.find(TipoAsignatura.class, id);

			em.remove(tipoAsignatura);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el Tipo Asignatura");
		}
	}

	
	public TipoAsignatura buscarTipoAsignaturaPorId(Long id) throws ServicesException {

		try {

			TipoAsignatura i = em.find(TipoAsignatura.class, id);

			return i;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro el Tipo Asignatura");
		}
	}

	
	public void modificar(TipoAsignatura tipoAsignatura) throws ServicesException {

		try {

			em.merge(tipoAsignatura);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el Tipo Asignatura");
		}
	}

	
	public List<TipoAsignatura> obtenerInscripciones() throws ServicesException {

		try {

			TypedQuery<TipoAsignatura> query = em.createQuery("SELECT DISTINCT t FROM TipoAsignatura t", TipoAsignatura.class);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Tipos Asignaturas");
		}

	}
	
	
	public TipoAsignatura obtenerTipoAsignaturaPorNombre(String nombre) throws ServicesException {
		
		try {
		
			TypedQuery<TipoAsignatura> query = em.createQuery("SELECT t FROM TipoAsignatura t WHERE t.nombre=:nombre",TipoAsignatura.class)
					.setParameter("nombre", nombre);
		
			return query.getSingleResult();
		
		}catch(PersistenceException e) {
			return null;
		}
		
	}
    
    

}
