package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.beans.GestionPersona;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Tutor;
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
    
    @Inject
    GestionPersona gestionPersona;
    
	public void crearUsuario(Usuario user) throws ServicesException {
		
		try {
			
			Query query = em.createNativeQuery("INSERT INTO USUARIOS (DOCUMENTO,NOMBRE_USUARIO,CONTRASENA,APELLIDO1,APELLIDO2,NOMBRE1,NOMBRE2,FEC_NAC,ID_DEPARTAMENTO,LOCALIDAD,MAIL,TELEFONO,ID_ITR,MAIL_INSTITUCIONAL,ACTIVO,VALIDADO) "
	                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	         query.setParameter(1, user.getDocumento());
	         query.setParameter(2, user.getNombreUsuario());
	         query.setParameter(3, user.getContrasena());
	         query.setParameter(4, user.getApellido1());
	         query.setParameter(5, user.getApellido2());
	         query.setParameter(6, user.getNombre1());
	         query.setParameter(7, user.getNombre2());
	         query.setParameter(8, user.getFechaNacimiento());
	         query.setParameter(9, user.getDepartamento().getId());
	         query.setParameter(10, user.getLocalidad());
	         query.setParameter(11, user.getMail());
	         query.setParameter(12, user.getTelefono());
	         query.setParameter(13, user.getItr().getId());
	         query.setParameter(14, user.getMailInstitucional());
	         query.setParameter(15, user.getActivo());
	         query.setParameter(16, user.getValidado());
	         
	         query.executeUpdate();
	         
	         
	         // Obtener el ID generado automáticamente
	         Query idQuery = em.createNativeQuery("SELECT USUARIO_SEC.CURRVAL FROM DUAL");
	         Long id = ((Number) idQuery.getSingleResult()).longValue();

         
	         if(gestionPersona.esAnalista()) {
	        	 Query QueryAnalista = em.createNativeQuery("INSERT INTO ANALISTA (ID_USUARIO) VALUES(?)");
	        	QueryAnalista.setParameter(1, id);
	        	QueryAnalista.executeUpdate();
	         }
	         
	         if(gestionPersona.esDocente()) {
	        	 Tutor userTutor= (Tutor) user;
	        	 Query QueryDocente  = em.createNativeQuery("INSERT INTO TUTORES (ID_USUARIO,ID_TIPO_TUTOR,ID_AREA_TUTOR) VALUES (?,?,?)");
	        	 QueryDocente.setParameter(1, id);
	        	 QueryDocente.setParameter(2, userTutor.getTipoTutor().getId());
	        	 QueryDocente.setParameter(3, userTutor.getAreaTutor().getId());
	        	 QueryDocente.executeUpdate();
	         }
	         
	         if(gestionPersona.esEstudiante()) {
	        	 Estudiante userEstudiante= (Estudiante) user;
	        	 Query QueryEstudiante  = em.createNativeQuery("INSERT INTO ESTUDIANTES (ID_USUARIO,ANO_INGRESO) VALUES (?,?)");
	        	 QueryEstudiante.setParameter(1, id);
	        	 QueryEstudiante.setParameter(2, userEstudiante.getAnoIngreso());
	        	 QueryEstudiante.executeUpdate();
	         }
	         
	        
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
			
			System.out.println("Nombre de usuario en buscarNombre: "+nombreUsuario);
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
