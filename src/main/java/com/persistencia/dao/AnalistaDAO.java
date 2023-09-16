package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Analista;
import com.persistencia.exception.ServicesException;

/**
 * Session Bean implementation class AnalistaBean
 */
@Stateless
@LocalBean
public class AnalistaDAO  {
    public AnalistaDAO() {
    }
    @PersistenceContext
	private EntityManager em;
    
    
   	public void crearAnalista(Analista analista) throws ServicesException {
   		
   		try {
   			
   			em.persist(analista);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR el analista"); 
   		}
   	}
    
    
	public void borrarAnalista(Long id) throws ServicesException {
		try {
			Analista analista= em.find(Analista.class, id);
			
			em.remove(analista);
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el analista"); 
		}
	}
    
    
	public Analista buscarAnalistaPorId(Long id) throws ServicesException {
		
		try {
			
			Analista analista= em.find(Analista.class, id);
			
			return analista;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el analista"); 
		}
	}
    
    
	public void modificarAnalista(Analista analista) throws ServicesException{
		
		try {
			
			em.merge(analista);
			
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el analista"); 
		}
	}
    
    
	public List<Analista> obtenerAnalista() throws ServicesException {
		
		try {
		
			TypedQuery<Analista> query = em.createQuery("SELECT DISTINCT a FROM Analista a",Analista.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de analista"); 
		}
		
	}
}
