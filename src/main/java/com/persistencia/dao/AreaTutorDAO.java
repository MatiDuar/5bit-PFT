package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.AreaTutor;
import com.persistencia.exception.ServicesException;



/**
 * Session Bean implementation class AreaTutorBean
 */
@Stateless
@LocalBean
public class AreaTutorDAO  {
	
    public AreaTutorDAO() {
    }
    @PersistenceContext
	private EntityManager em;
    
    
   	public void crearAreaTutor(AreaTutor areaTutor) throws ServicesException {
   		
   		try {
   			
   			em.persist(areaTutor);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR el area de tutor"); 
   		}
   	}
    
   	public void crearAreaTutor(String nombre) throws ServicesException {
   		
   		try {
   			AreaTutor areaTutor=new AreaTutor();
   			areaTutor.setNombre(nombre);
   			em.persist(areaTutor);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR el area de tutor"); 
   		}
   	}
    
    
	public void borrarAreaTutor(Long id) throws ServicesException {
		try {
			AreaTutor tipoTutor= em.find(AreaTutor.class, id);
			
			em.remove(tipoTutor);
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el area de tutor"); 
		}
	}
    
    
	public AreaTutor buscarAreaTutorPorId(Long id) throws ServicesException {
		
		try {
			
			AreaTutor at= em.find(AreaTutor.class, id);
			
			return at;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el area tutor"); 
		}
	}
    
    
	public void modificarAreaTutor(AreaTutor areaTutor) throws ServicesException{
		
		try {
			
			em.merge(areaTutor);
			
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el area tutor"); 
		}
	}
    
    
	public List<AreaTutor> obtenerAreaTutor() throws ServicesException {
		
		try {
		
			TypedQuery<AreaTutor> query = em.createQuery("SELECT DISTINCT t FROM AreaTutor t",AreaTutor.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Areas"); 
		}
		
	}
    
    
   	public AreaTutor buscarPorNombre(String nombre) throws ServicesException {
   		
   		try {
   		
   			TypedQuery<AreaTutor> query = em.createQuery("SELECT t FROM AreaTutor t WHERE t.nombre =:nombre",AreaTutor.class).setParameter("nombre", nombre);
   		
   			return query.getSingleResult();
   		
   		}catch(PersistenceException e) {
   			throw new ServicesException(e.getMessage()); 
   		}
   		
   	}

}
