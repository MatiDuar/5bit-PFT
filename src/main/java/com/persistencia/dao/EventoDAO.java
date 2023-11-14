package com.persistencia.dao;

import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Analista;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Tutor;
import com.persistencia.entities.Reclamo;
import com.persistencia.exception.ServicesException;



/**
 * Session Bean implementation class EventoBean
 */
@Stateless
@LocalBean

public class EventoDAO {
    public EventoDAO() {
    }
    
    @PersistenceContext
	private EntityManager em;
    
    
   	public void crearEvento(Evento evento) throws ServicesException {
   		
   		try {
   			

   			em.merge(evento);

   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException(e.getMessage()); 
   		}
   	}
    
   
	public boolean borrarEvento(Long id) throws ServicesException {
		try {
			Evento ev= em.find(Evento.class, id);
			ev.setAnalistas(new HashSet<Analista>());
			ev.setTutores(new LinkedList<Tutor>());
			
			

			em.merge(ev);
			em.flush();
			em.remove(ev);
			em.flush();
			return true;
		}catch(PersistenceException e) {
			throw new ServicesException(e.getMessage()); 
			
		}
	}
    
    
	public Evento buscarEventoPorId(Long id) throws ServicesException {
		
		try {
			
			Evento ev= em.find(Evento.class, id);
			
			return ev;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el Evento"); 
		}
	}
    
    
	public void modificarEvento(Evento evento) throws ServicesException{
		
		try {
			
			em.merge(evento);
			
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el evento"); 
		}
	}
    
   
	public List<Evento> obtenerEvento() throws ServicesException {
		
		try {
		
			TypedQuery<Evento> query = em.createQuery("SELECT DISTINCT a FROM Evento a",Evento.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de eventos"); 
		}
		
	}
	
	public void borrarReclamosPorEvento(Evento evento) throws ServicesException{
		try {
			
			TypedQuery<Reclamo> query = em.createQuery("SELECT DISTINCT r FROM Reclamo r WHERE r.evento=:evento",Reclamo.class)
					.setParameter("evento", evento);
			if(query.getResultList().size()!=0) {
				for(Reclamo r:query.getResultList()) {
					em.remove(r);
				}
				em.flush();
			}
			
			
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de eventos"); 
		}
	}

}
