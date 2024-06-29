package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Tutor;
import com.persistencia.exception.ServicesException;


/**
 * Session Bean implementation class TutorBean
 */
@Stateless
@LocalBean

public class TutorDAO {

    public TutorDAO() {
        // TODO Auto-generated constructor stub
    }
    
    @PersistenceContext
	private EntityManager em;
    
    
   	public void crearTutor(Tutor tutor) throws ServicesException {
   		
   		try {
   			
   			em.persist(tutor);
   			em.flush();		
   			   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR el tutor"); 
   		}
   	}
    
	public void borrarTutor(Long id) throws ServicesException {
		try {
			Tutor tutor= em.find(Tutor.class, id);
			
			em.remove(tutor);
			em.flush();
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el tutor"); 
		}
	}
    
    
	public Tutor buscarTutorPorId(Long id) throws ServicesException {
		
		try {
			
			Tutor tp= em.find(Tutor.class, id);
			
			return tp;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el tutor"); 
		}
	}

	public void modificarTutor(Tutor tutor) throws ServicesException{
		
		try {
			
			em.merge(tutor);
			em.flush();
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el tutor"); 
		}
	}
  
	public List<Tutor> obtenerTutor() throws ServicesException {
		
		try {
		
			TypedQuery<Tutor> query = em.createQuery("SELECT DISTINCT t FROM Tutor t",Tutor.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de tutor"); 
		}
		
	}

}
