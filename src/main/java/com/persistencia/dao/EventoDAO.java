package com.persistencia.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Analista;
import com.persistencia.entities.EstadosEventos;
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
   		Evento eventoInsertado = null;
   		try {   			
   	        
//   			Evento eventoPersistido = em.merge(evento);
//   			System.out.println("EventoPersistido "+ eventoPersistido);
//   			em.flush();
//   			
   		 Query query = em.createNativeQuery("INSERT INTO EVENTOS (FECHA_INIC, FECHA_FIN, TITULO, ID_TIPO_ACTIVIDAD, SEMESTRE, CREDITOS, LOCACION, ID_ITR, ID_MODALIDAD, ID_ESTADO) "
                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
         query.setParameter(1, evento.getFechaInicio());
         query.setParameter(2, evento.getFechaFin());
         query.setParameter(3, evento.getTitulo());
         query.setParameter(4, evento.getTipoActividad().getId());
         query.setParameter(5, evento.getSemestre());
         query.setParameter(6, evento.getCreditos());
         query.setParameter(7, evento.getLocalizacion());
         query.setParameter(8, evento.getItr().getId());
         query.setParameter(9, evento.getModalidad().getId());
         query.setParameter(10, evento.getEstado().getId());
         
         query.executeUpdate();
         
      // Obtener el ID generado automáticamente
         Query idQuery = em.createNativeQuery("SELECT EVENTO_SEC.CURRVAL FROM DUAL");
         Long id = ((Number) idQuery.getSingleResult()).longValue();

         // Recuperar el evento insertado
         eventoInsertado = em.find(Evento.class, id);
         
   		    
   		    for(Tutor tut :evento.getTutores()) {
   		    	eventoInsertado.addTutor(tut);
   		    	
   		    }
   		 em.merge(eventoInsertado);
//   		 em.flush();
  
   		    
   		    //FECHA_INIC, FECHA_FIN, TITULO, ID_TIPO_ACTIVIDAD, SEMESTRE, CREDITOS, LOCACION, ID_ITR, ID_MODALIDAD, ID_ESTADO
	
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
