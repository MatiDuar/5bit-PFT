package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Tutor;
import com.persistencia.exception.ServicesException;



/**
 * Session Bean implementation class EstudianteBean
 */
@Stateless
@LocalBean
public class EstudianteDAO {


    public EstudianteDAO() {
    }
    @PersistenceContext
	private EntityManager em;
    
    


	
	
	public List<Estudiante> obtenerEstudiantes() throws ServicesException {
		
		try {
		

			TypedQuery<Estudiante> query = em.createQuery("SELECT DISTINCT e FROM Estudiante e",Estudiante.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de estudiantesa"); 
		}
		
	}

	

	
	public Estudiante buscarNombre (String nombreUsuario) throws ServicesException {
		
		try {
			

			TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e WHERE e.nombreUsuario = :nombreUsuario",Estudiante.class)
					.setParameter("nombreUsuario", nombreUsuario);
					

		
			return query.getSingleResult();
			
		}catch(PersistenceException e) {

			return null;
			 
		}
	}
	
	public Estudiante buscarEstudiantePorId(Long id) throws ServicesException {
			
			try {
				
				Estudiante tp= em.find(Estudiante.class, id);
				
				return tp;
				
			}catch(PersistenceException e) {
				throw new ServicesException("No se encontro el Estudiante"); 
			}
		}

}
