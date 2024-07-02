package com.persistencia.dao;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import javax.persistence.TypedQuery;

import com.persistencia.entities.Departamento;
import com.persistencia.exception.ServicesException;
import com.persistencia.entities.ITR;



/**
 * Session Bean implementation class ITRBean
 */
@Stateless
@LocalBean

public class ItrDAO {

	@PersistenceContext
	private EntityManager em;
	
    public ItrDAO() {
    }
    
    
   	public void crearITR(String nombre, Departamento dep,Boolean activo) throws ServicesException {
   	
   		try {
   			
   			ITR itr= new ITR();
   			
   			itr.setNombre(nombre);
   			itr.setDepartamento(dep);
   			itr.setActivo(activo);
   			
   			em.merge(itr);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo crear el ITR"); 
   		}
   	}
    
    
   	public void actualizarITR(ITR itr) throws ServicesException {
   	
   		try {
   			
   			em.merge(itr);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR el itr"); 
   		}
   	}
    
   
	public void borrarITR(Long id) throws ServicesException {
		
		try {
			
			ITR itr= em.find(ITR.class, id);
			
			em.remove(itr);
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el itr"); 
		}
	}
    
    
    public ITR buscarItrPorId(Long id) throws ServicesException {
		
		try {
			
			ITR itr= em.find(ITR.class, id);
			
			return itr;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el itr"); 
		}
	}

	
    public List<ITR> obtenerItrsActivos() throws ServicesException{
    	try {
    		TypedQuery<ITR> query = em.createQuery("SELECT DISTINCT i FROM ITR i WHERE ACTIVO = 1",ITR.class);
    		
			return query.getResultList();
    		
			
		} catch (Exception e) {
			throw new ServicesException("No se pudo obtener la lista de ITRs activos."); 
		}
    }
    
	public List<ITR> obtenerItrs() throws ServicesException {
		
		try {
		
			TypedQuery<ITR> query = em.createQuery("SELECT DISTINCT i FROM ITR i",ITR.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de ITRs"); 
		}
		
	}
	
	
	public ITR obtenerItrPorNombre(String nombre) throws ServicesException {
		
		try {
		
			TypedQuery<ITR> query = em.createQuery("SELECT i FROM ITR i WHERE i.nombre=:nombre",ITR.class)
					.setParameter("nombre", nombre);
		
			return query.getSingleResult();
		
		}catch(PersistenceException e) {
			return null;
		}
		
	}


	
	
}
