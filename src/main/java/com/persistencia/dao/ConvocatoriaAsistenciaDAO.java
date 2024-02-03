package com.persistencia.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.persistencia.dto.EscolaridadDTO;
import com.persistencia.entities.ConvocatoriaAsistencia;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Usuario;
import com.persistencia.exception.ServicesException;
import java.text.SimpleDateFormat;  

@Stateless
@LocalBean

public class ConvocatoriaAsistenciaDAO {

	public ConvocatoriaAsistenciaDAO() {

	}

	@PersistenceContext
	private EntityManager em;

	public void crear(ConvocatoriaAsistencia convocatoriaAsistencia) throws ServicesException {

		try {

			em.persist(convocatoriaAsistencia);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo CREAR la Convocatoria Asistencia");
		}
	}

	public void borrar(Long id) throws ServicesException {
		try {
			ConvocatoriaAsistencia c = em.find(ConvocatoriaAsistencia.class, id);

			em.remove(c);
			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo BORRAR la Convocatoria Asistencia");
		}
	}

	public ConvocatoriaAsistencia buscarPorId(Long id) throws ServicesException {

		try {

			ConvocatoriaAsistencia c = em.find(ConvocatoriaAsistencia.class, id);

			return c;

		} catch (PersistenceException e) {
			throw new ServicesException("No se encontro la Convocatoria Asistencia");
		}
	}

	public void modificar(ConvocatoriaAsistencia c) throws ServicesException {

		try {

			em.merge(c);

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Convocatoria Asistencia");
		}
	}

	public void modificar(List<ConvocatoriaAsistencia> convocatorias) throws ServicesException {

		try {

			for(ConvocatoriaAsistencia ca:convocatorias) {
				em.merge(ca);
			}

			em.flush();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo MODIFICAR la Convocatoria Asistencia");
		}
	}

	public List<ConvocatoriaAsistencia> obtenerTodos() throws ServicesException {

		try {

			TypedQuery<ConvocatoriaAsistencia> query = em.createQuery("SELECT DISTINCT c FROM ConvocatoriaAsistencia c",
					ConvocatoriaAsistencia.class);

			return query.getResultList();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo obtener la lista de Convocatoria Asistencia");
		}

	}

	public List<Estudiante> buscarPorEvento(Evento evento) throws ServicesException {

		try {

			TypedQuery<Estudiante> query = em
					.createQuery("SELECT c.estudiante FROM ConvocatoriaAsistencia c WHERE c.evento =:evento",
							Estudiante.class)
					.setParameter("evento", evento);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la de estudiantes asignados a la Convocatoria Asistencia");
		}

	}

	public List<ConvocatoriaAsistencia> buscarConvocatoriasPorEvento(Evento evento) throws ServicesException {

		try {

			TypedQuery<ConvocatoriaAsistencia> query = em
					.createQuery("SELECT c FROM ConvocatoriaAsistencia c WHERE c.evento.id =:evento",
							ConvocatoriaAsistencia.class)
					.setParameter("evento", evento.getId());

			return query.getResultList();

		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo obtener la de estudiantes asignados a la Convocatoria Asistencia");
		}

	}

	public ConvocatoriaAsistencia buscarPorEstudianteEvento(Estudiante estudiante, Evento evento)
			throws ServicesException {
		try {
			TypedQuery<ConvocatoriaAsistencia> query = em.createQuery(
					"SELECT e FROM ConvocatoriaAsistencia e WHERE e.estudiante = :estudiante AND e.evento=:evento",
					ConvocatoriaAsistencia.class).setParameter("estudiante", estudiante).setParameter("evento", evento);
			return query.getSingleResult();
		} catch (PersistenceException e) {

			return null;

		}
	}
	
	public List<EscolaridadDTO> buscarEscolaridadPorEstudiante(Usuario estudiante)
			throws ServicesException {
		try {
			
			String queryStr = "select c.estudiante_id, u.nombre1, u.apellido1, c.evento_id, c.calificacion, eve.titulo, eve.creditos, eve.fechainicio, eve.fechafin, eve.semestre, mo.nombremodalidadevento,i.nombre\r\n"
					+ "from convocatorias_asistencias c, usuarios u , estudiantes e, eventos eve, modalidades_eventos mo, itr i\r\n"
					+ "where u.id = e.id\r\n"
					+ "and c.estudiante_id = e.id\r\n"
					+ "and c.evento_id = eve.id\r\n"
					+ "and mo.id_modalidad = eve.modalidad_id_modalidad\r\n"
					+ "and i.id = eve.itr_id\r\n"
					+ "and c.estudiante_id = :estudiante";
			
			Long idEstudiante = estudiante.getId();
			
			System.out.println("ID del estudiante: " + idEstudiante);
			
			Query q = em.createNativeQuery(queryStr).setParameter("estudiante", idEstudiante);

			List<Object[]> resultados = q.getResultList();
			
			System.out.println("Resultado de Query: " + resultados.toString());
			
			
			List<EscolaridadDTO> escolaridades = new ArrayList<>();
			EscolaridadDTO aux;
			
			String strDato;
			int contador;
			for (Object[] tupla : resultados) {
	            contador = 0;
	            aux = new EscolaridadDTO();
	            for (Object dato : tupla) {
	            	
	                if (dato != null) {
	                	strDato = dato.toString();
	                }else {	
	                	strDato = "";
	                }
	                
	                
	                switch (contador) {
	                    case 0:
	                        aux.setIdEstudiante(Integer.parseInt(strDato));
	                        break;
	                    case 1:
	                        aux.setNombre1(strDato);
	                        break;
	                    case 2:
	                        aux.setApellido1(strDato);
	                        break;
	                    case 3:
	                        aux.setEventoId(Integer.parseInt(strDato));
	                        break;
	                    case 4:
	                        aux.setCalificacion(Integer.parseInt(strDato));
	                        break;
	                    case 5:
	                        aux.setTituloEvento(strDato);
	                        break;
	                    case 6:
	                        aux.setCreditos(Integer.parseInt(strDato));
	                        break;
	                    case 7:
	                    	try {
	                    		System.out.println(strDato.toString());
	                    	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	                    	    java.util.Date parsedDate =  dateFormat.parse(strDato.toString());
	                    	    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	                    		aux.setFechaInicio(timestamp);
	                    		System.out.println(timestamp.toString());
	                    	} catch(Exception e) { //this generic but you can control another types of exception
	                    	    // look the origin of excption 
	                    		e.printStackTrace();
	                    	} 
	                        break;
	                    case 8:
	                    	try {
	                    	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	                    	    java.util.Date parsedDate = dateFormat.parse(strDato.toString());
	                    	    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	                    	    aux.setFechaFin(timestamp); 
	                    	} catch(Exception e) { //this generic but you can control another types of exception
	                    	    // look the origin of excption 
	                    		e.printStackTrace();
	                    	}
	                    	break;
	                    case 9:
	                    	aux.setSemestre(strDato); 
	                    	break;
	                    case 10:
	                    	aux.setModalidad(strDato);
                    		break;
	                    case 11:
	                    aux.setNombreItr(strDato);
                    		break;
    	
	                }
	                contador++;
	            }
	            
	            //Si la calificación es 0, la escolaridad no se mostrará.
	            if(aux.getCalificacion()>0) {
	            	 escolaridades.add(aux);
	            }
	           
			}   
			
			
			
			return escolaridades;
		} catch (PersistenceException e) {
			return null;

		}
	}

	public void borrar(Estudiante estudiante, Evento evento) throws ServicesException {

		try {

			em.remove(buscarPorEstudianteEvento(estudiante, evento));
			em.flush();

		} catch (PersistenceException e) {

			throw new ServicesException("No se pudo borrar la Convocatoria Asistencia");

		}
	}

}
