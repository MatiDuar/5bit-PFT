package com.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.persistencia.entities.AccionReclamo;
import com.persistencia.entities.Analista;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Reclamo;
import com.persistencia.entities.Tutor;
import com.persistencia.entities.Usuario;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean
public class AccionReclamoDAO {
	public AccionReclamoDAO() {
	}

	@PersistenceContext
	private EntityManager em;

	public void crearAccionReclamo(AccionReclamo accion, Usuario usuario) throws ServicesException {

		try {
			
			
			System.out.println("accion: "+accion);
			System.out.println("analista: "+usuario);
			
			Analista analista = (Analista) usuario;
			
			Query query = em.createNativeQuery("INSERT INTO ACCIONES_RECLAMOS (ID_RECLAMO, ID_ANALISTA, FECHA_HORA, DETALLE) "
	                 + "VALUES (?, ?, ?, ?)");
	         query.setParameter(1, accion.getReclamo().getId());
	         query.setParameter(2, analista.getIdAnalista());
	         query.setParameter(3, accion.getFechaHoraReclamo());
	         query.setParameter(4, accion.getDetalleReclamo());
	    
	         
	         query.executeUpdate();
	         
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR la accion");
		}
	}

	public void borrarAccionReclamo(Long id) throws ServicesException {
		try {
			AccionReclamo accion = em.find(AccionReclamo.class, id);

			em.remove(accion);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la accion reclamo");
		}
	}

	public AccionReclamo buscarAccionReclamoPorId(Long id) throws ServicesException {

		try {

			AccionReclamo accion = em.find(AccionReclamo.class, id);

			return accion;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Accion Reclamo");
		}
	}

	public void modificarAccionReclamo(AccionReclamo accion) throws ServicesException {

		try {

			em.merge(accion);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Accion Reclamo ");
		}
	}

	public List<AccionReclamo> obtenerAccionReclamo() throws ServicesException {

		try {

			TypedQuery<AccionReclamo> query = em.createQuery("SELECT DISTINCT a FROM AccionReclamo a", AccionReclamo.class);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la lista de Acciones Reclamo");
		}

	}
	
	public List<AccionReclamo> obtenerAccionPorReclamo(Reclamo reclamo) throws ServicesException {

		try {

			
//			TypedQuery<AccionReclamo> query = em.createNativeQuery("SELECT DISTINCT a FROM AccionReclamo a WHERE a.reclamo = :reclamo")
//	                .setParameter("reclamo", reclamo);
			
			
			Query query = em.createNativeQuery("SELECT * FROM ACCIONES_RECLAMOS WHERE ID_RECLAMO = ?", AccionReclamo.class);
	        query.setParameter(1, reclamo.getId());

	        return query.getResultList();

		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ServicesException("No se pudo obtener la lista de Acciones Reclamo");
		}

	}
}
