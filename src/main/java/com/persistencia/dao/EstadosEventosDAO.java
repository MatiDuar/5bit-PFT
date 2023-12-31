package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.EstadosEventos;
import com.persistencia.exception.ServicesException;


/**
 * Session Bean implementation class EstadosEventosBean
 */
@Stateless
@LocalBean
public class EstadosEventosDAO {

    public EstadosEventosDAO() {
    }
    
    @PersistenceContext
	private EntityManager em;
	
    
   	public void crearEstadoEvento(EstadosEventos estado) throws ServicesException {
   		
   		try {
   			
   			em.merge(estado);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR el estado del evento"); 
   		}
   	}
   	
   	
	public void modificarEstadoEvento(EstadosEventos estado) throws ServicesException{
		
		try {
			
			em.merge(estado);
			
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el estado del evento"); 
		}
	}
   	
   
   	public void borrarEstadoEvento(Long id) throws ServicesException {
   		
   		try {
            
            EstadosEventos estado= em.find(EstadosEventos.class, id);
   			
   			em.remove(estado);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo BORRAR el estado del evento"); 
   		}
   	}
   	
   	
	public EstadosEventos buscarEstadoEventoPorId(Long id) throws ServicesException {
		
		try {
			
			EstadosEventos estado= em.find(EstadosEventos.class, id);
			
			return estado;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el estado del evento"); 
		}
	}
	
	 
	public List<EstadosEventos> obtenerEstadosEventos() throws ServicesException {
		
		try {
			
			TypedQuery<EstadosEventos> query = em.createQuery("SELECT DISTINCT e FROM EstadosEventos e",EstadosEventos.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de estados de eventos"); 
		}
		
	}
	
	
	public EstadosEventos buscarNombreEstadoEvento (String nombreEstadoEvento) throws ServicesException {
		
		try {
			

			TypedQuery<EstadosEventos> query = em.createQuery("SELECT e FROM EstadosEventos e WHERE e.nombreEstadoEvento = :nombreEstadoEvento",EstadosEventos.class)
					.setParameter("nombreEstadoEvento", nombreEstadoEvento);
					

		
			return query.getSingleResult();
			
		}catch(PersistenceException e) {
            
			return null;
			 
		}
	}
   	

}
