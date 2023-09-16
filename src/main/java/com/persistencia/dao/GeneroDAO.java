package com.persistencia.dao;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.exception.ServicesException;
import com.persistencia.entities.Genero;




/**
 * Session Bean implementation class GeneroBean
 */
@Stateless
@LocalBean

public class GeneroDAO {
	

	@PersistenceContext
	private EntityManager em;
	
    public GeneroDAO() {
    }
    
    
   	public void crearGenero(String nombre, Boolean activo) throws ServicesException {
   		
   		try {
   			
   			Genero gen= new Genero();
   			
   			gen.setNombre(nombre);
   			gen.setActivo(activo);
   			
   			em.persist(gen);
   			em.flush();		
   			
   		}catch(PersistenceException e) {
   			throw new ServicesException("No se pudo CREAR el usuario"); 
   		}
   	}
    
   
	public void borrarGenero(Long id) throws ServicesException {
		
		try {
			
			Genero gen= em.find(Genero.class, id);
			
			em.remove(gen);
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el usuario"); 
		}
	}
    
    
    public Genero buscarGeneroPorId(Long id) throws ServicesException {
		
		try {
			
			Genero gen= em.find(Genero.class, id);
			
			return gen;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el usuario"); 
		}
	}
	
	
	public List<Genero> obtenerGeneros() throws ServicesException {
		
		try {
		
			TypedQuery<Genero> query = em.createQuery("SELECT DISTINCT g FROM Genero g",Genero.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de Generos"); 
		}
		
	}
	
	  
    
    public Genero buscarGeneroPorNombre(String nombre) throws ServicesException {
		
		try {
			
			TypedQuery<Genero> query = em.createQuery("SELECT DISTINCT g FROM Genero g WHERE g.nombre=:nombre",Genero.class)
					.setParameter("nombre", nombre);

			
			return query.getSingleResult();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el usuario"); 
		}
	}

}
