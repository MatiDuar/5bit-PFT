package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.persistencia.entities.Usuario;
import com.persistencia.exception.ServicesException;



/**

 * Session Bean implementation class UsuarioBean
 */
@Stateless
@LocalBean
public class UsuarioDAO {

	
	
    public UsuarioDAO() {
    }
    
    @PersistenceContext
	private EntityManager em;
    
	public void crearUsuario(Usuario user) throws ServicesException {
		
		try {
			
			em.merge(user);
			em.flush();		
			
		}catch(PersistenceException e) {
			
			throw new ServicesException(e.getMessage()); 

		}
	}
	
	
	
	
	public void borrarUsuario(Long id) throws ServicesException {
		
		try {
			
			Usuario user= em.find(Usuario.class, id);
			
			em.remove(user);
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR el usuario"); 
		}
	}
	
	
	
	public void modificarUsuario(Usuario user) throws ServicesException{
		
		try {
			
			em.merge(user);
			
			em.flush();
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR el usuario"); 
		}
	}
	

	public List<Usuario> obtenerUsuarios() throws ServicesException {
		
		try {
		

			TypedQuery<Usuario> query = em.createQuery("SELECT DISTINCT u FROM Usuario u",Usuario.class);
		
			return query.getResultList();
		
		}catch(PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de usuarios"); 
		}
		
	}
	
	
	public Usuario buscarUsuarioPorId(Long id) throws ServicesException {
		
		try {
			
			Usuario user= em.find(Usuario.class, id);
			
			return user;
			
		}catch(PersistenceException e) {
			throw new ServicesException("No se encontro el usuario"); 
		}
	}
	
	
	public Usuario verificarUsuario (String nombreUsuario, String contrasena) throws ServicesException {
		
		try {
			

			TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.contrasena=:contrasena",Usuario.class)
					.setParameter("nombreUsuario", nombreUsuario)
					.setParameter("contrasena", contrasena);

		
			return query.getSingleResult();
			
		}catch(PersistenceException e) {

			return null;
			 
		}
	}
	

	
	public Usuario buscarNombre (String nombreUsuario) throws ServicesException {
		
		try {
			

			TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario",Usuario.class)
					.setParameter("nombreUsuario", nombreUsuario);	
			return query.getSingleResult();
			
		}catch(PersistenceException e) {

			return null;
			 
		}
	}
	
	public Boolean existeNombreUsuario(String nombreUsuario) {
		try {
			TypedQuery<Usuario> query = em
					.createQuery("select u from Usuario u where u.nombreUsuario=:nombreUsuario", Usuario.class)
					.setParameter("nombreUsuario", nombreUsuario);
			query.getSingleResult();
			System.out.println("query base: "+query.getSingleResult().toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
