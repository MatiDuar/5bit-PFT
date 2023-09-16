package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.EstadoAsistencia;
import com.persistencia.exception.ServicesException;


/**
 * Session Bean implementation class EstadoAsistenciaBean
 */
@Stateless
@LocalBean
public class EstadoAsistenciaDAO {


    public EstadoAsistenciaDAO() {
    }
    
    @PersistenceContext
	private EntityManager em;
    
    
   	public void crear(EstadoAsistencia ea) throws ServicesException {
   		
   		try {
   			
   			em.persist(ea);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR la Estado Asistencia"); 
   		}
   	}
    
    
	public void borrar(Long id) throws ServicesException {
		try {
			EstadoAsistencia e= em.find(EstadoAsistencia.class, id);
			
			em.remove(e);
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el estado Asistencia"); 
		}
	}
    
    
	public EstadoAsistencia buscarPorId(Long id) throws ServicesException {
		
		try {
			
			EstadoAsistencia c= em.find(EstadoAsistencia.class, id);
			
			return c;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro la estado Asistencia"); 
		}
	}
	
    
    
	public void modificar(EstadoAsistencia c) throws ServicesException{
		
		try {
			
			em.merge(c);
			
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Convocatoria Asistencia"); 
		}
	}
    
    
	public List<EstadoAsistencia> obtenerTodos() throws ServicesException {
		
		try {
		
			TypedQuery<EstadoAsistencia> query = em.createQuery("SELECT DISTINCT c FROM EstadoAsistencia c",EstadoAsistencia.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Convocatoria Asistencia"); 
		}
		
	}
    
    
	public EstadoAsistencia obtenerPorNombre(String nombre) throws ServicesException {
		
		try {
		
			TypedQuery<EstadoAsistencia> query = em.createQuery("SELECT e FROM EstadoAsistencia e WHERE e.nombre=:nombre",EstadoAsistencia.class)
					.setParameter("nombre", nombre);
		
			return query.getSingleResult();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener el Estado Asistencia"); 
		}
		
	}


}
