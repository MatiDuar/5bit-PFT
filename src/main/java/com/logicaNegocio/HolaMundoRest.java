package com.logicaNegocio;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.annotations.Expose;
import com.persistencia.dao.EventoDAO;
import com.persistencia.dao.ReclamoDAO;
import com.persistencia.dao.UsuarioDAO;
import com.persistencia.dto.UserDTO;

import com.persistencia.entities.Departamento;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.Evento;
import com.persistencia.entities.Reclamo;
import com.persistencia.entities.Usuario;
import com.persistencia.exception.ServicesException;

import java.util.List;

@Path("reclamo")
@Produces("application/json") // como voy a retornar texto plano pongo esta anotacion.
//Si no pongo nada retorna html
public class HolaMundoRest {

	@Inject
	GestionPersonaService gestionPersonas;

	@Inject
	ReclamoDAO reclamoDAO;

	@Inject
	UsuarioDAO usuarioDAO;

	@Inject
	EventoDAO eventoDAO;

	public HolaMundoRest() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Path("estudiante")
	@Produces("application/json")
	public Estudiante buscarEstudiante(@QueryParam("nombreUsuario") String nombreUsuario) {
		// Implement logic to fetch and return a list of Usuario objects
		try {
			Usuario usuario = usuarioDAO.buscarNombre(nombreUsuario);
			if (usuario instanceof Estudiante) {
				return (Estudiante) usuario;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@GET
	@Path("evento")
	@Produces("application/json")
	public Evento buscarEvento(@QueryParam("id") Long id) {
		// Implement logic to fetch and return a list of Usuario objects
		try {
			Evento evento = eventoDAO.buscarEventoPorId(id);

			return evento;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@GET
	@Path("usuarios")
	@Produces("application/json")
	public List<Usuario> getAllUsuarios() {
		// Implement logic to fetch and return a list of Usuario objects
		List<Usuario> usuarios;
		try {
			usuarios = gestionPersonas.listarPersonas();
			return usuarios;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("crearDepartamento")
	public String crearDepartamento(Departamento dep) {
		try {
			gestionPersonas.agregarDep(dep);
			return "paso";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("crearReclamo")
	public Response crearReclamo(Reclamo reclamo) {
		try {
			boolean result = reclamoDAO.crearReclamo(reclamo);
			if (result) {
				return Response.ok().status(Response.Status.ACCEPTED).build();
			} else {
				return Response.notModified().build();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.notModified().build();
		}

	}

	@GET
	@Path("listaReclamos")
	@Produces("application/json")

	public List<Reclamo> getAllReclamos() {

		List<Reclamo> reclamos;
		try {
			reclamos = reclamoDAO.obtenerReclamos();
			return reclamos;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@PUT
	@Path("modificarReclamo")
	@Produces("application/json")
	@Consumes("application/json")
	public Response modificarReclamo(Reclamo reclamo, @QueryParam("id") Long id) {
		try {

			Reclamo reclamoAux = reclamoDAO.buscarReclamoPorId(id);
			reclamoAux = reclamo;
			boolean result = reclamoDAO.modificarReclamo(reclamoAux);
			if (result) {
				return Response.ok().status(Response.Status.ACCEPTED).build();
			} else {
				return Response.notModified().build();
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@DELETE
	@Path("borrarReclamo")
	@Produces("application/json")
	public Response borrarReclamo(@QueryParam("idBorrar") Long id) {
		try {
			boolean result = reclamoDAO.borrarReclamo((long) id);

			if (result) {
				return Response.ok().status(Response.Status.ACCEPTED).build();
			} else {
				return Response.notModified().build();
			}
		} catch (Exception e) {

			e.printStackTrace();
			return Response.notModified().build();
		}

	}

	@GET
	@Path("obtenerReclamo")
	@Produces("application/json")
	public Reclamo buscarReclamo(@QueryParam("id") Long id) {
		// Implement logic to fetch and return a list of Usuario objects
		try {
			Reclamo reclamo = reclamoDAO.buscarReclamoPorId(id);

			return reclamo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@POST
	@Path("verificarUsuario")
	@Produces("application/json")
	@Consumes("application/json")
	public Usuario verificarUsuario(UserDTO userDTO) {
		try {
			Usuario usuario = usuarioDAO.verificarUsuario(userDTO.getNombreUsuario(), userDTO.getPassword());

			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
