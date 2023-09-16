package com.logicaNegocio;

import java.io.Serializable;
import java.sql.Date;

import java.util.List;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.AreaTutorDAO;
import com.persistencia.dao.CarreraDAO;
import com.persistencia.dao.DepartamentoDAO;
import com.persistencia.dao.ItrDAO;
import com.persistencia.dao.TipoTutorDAO;
import com.persistencia.dao.UsuarioDAO;
import com.persistencia.dto.PersonaAlumnoDTO;
import com.persistencia.entities.*;
import com.persistencia.exception.ServicesException;

@Stateless
@LocalBean

public class GestionPersonaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	UsuarioDAO usuarioDAO;

	@EJB
	CarreraDAO carreraDAO;

	@EJB
	DepartamentoDAO depDAO;

	@EJB
	ItrDAO itrDAO;
	
	@EJB
	AreaTutorDAO areaTutorDAO;
	
	@EJB
	TipoTutorDAO tipoTutorDAO;
	
	@EJB
	DepartamentoDAO departamentoDAO;
	
	
	
	

	/**
	 * Lista de todas las personas en la base de datos
	 * 
	 * @return Lista de Personas
	 * @throws Exception
	 */
	public List<Usuario> listarPersonas() throws Exception {

		List<Usuario> listaPersonas = usuarioDAO.obtenerUsuarios();

		return listaPersonas;
	}

	
	public List<ITR> listarITRs() throws Exception {

		List<ITR> listaITR = itrDAO.obtenerItrs();

		return listaITR;
	}
	
	public List<TipoTutor> listarTipoTutor() throws Exception {

		List<TipoTutor> listaTipoTutor = tipoTutorDAO.obtenerTipoTutor();

		return listaTipoTutor;
	}
	
	
	public List<AreaTutor>listarAreaTutor() throws Exception{
		List<AreaTutor> listaAreaTutor = areaTutorDAO.obtenerAreaTutor();

		return listaAreaTutor;
	}
	
	public List<Departamento>listarDepartamento() throws Exception{
		List<Departamento> listaDepartamento = departamentoDAO.obtenerDepartamento();
		return listaDepartamento;
	}
	
	
	public Departamento buscarDepartamento(String nombre) {
		try {
			return departamentoDAO.obtenerDepPorNombre(nombre);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ITR buscarItr(String nombre) {
		try {
			return itrDAO.obtenerItrPorNombre(nombre);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public Usuario buscarUsuario(Long id) {
		try {
			return usuarioDAO.buscarUsuarioPorId(id);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Boolean modificarUsuario(Usuario usuario) {
		try {
			usuarioDAO.modificarUsuario(usuario);
			return true;
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
			return false;
		}
	}
	/**
	 * Lista de todas las personas y alumnos en la base de datos
	 * 
	 * @return Lista de PersonasAlumnoDTO
	 * @throws Exception
	 */
//	public List<PersonaAlumnoDTO> listarPersonasDTO() throws Exception {
//
//		List<PersonaAlumnoDTO> listaPersonas = usuarioDAO.listarPersonasDTO();
//
//		return listaPersonas;
//	}

	/**
	 * Lista de todos los ITRs
	 * 
	 * @return lista de ITR
	 */
//	public List<ITR> listarITRs() {
//
//		List<ITR> listaPItrs = itrDAO.listarITRs();
//
//		return listaPItrs;
//	}

	/**
	 * Verifica si el nombreUsuario y contraseña coinciden en la base de datos
	 * 
	 * @param nombreUsuario
	 * @param contra
	 * @return El objeto de la persona encontrada en la base de datos
	 */
	public Usuario verificarUsuario(String nombreUsuario, String contra) {
		try {
			return usuarioDAO.verificarUsuario(nombreUsuario, contra);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * se agrega Persona en la base de datos
	 * 
	 * @param p
	 */
	public void agregarUsuario(Usuario p) {

		try {
			usuarioDAO.crearUsuario(p);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public AreaTutor buscarAreaTutor(String nombre) {
		try {
			return areaTutorDAO.buscarPorNombre(nombre);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public TipoTutor buscarTipoTutor(String nombre) {
		try {
			return tipoTutorDAO.obtenerTipoTutorPorNombre(nombre);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Modifica los de datos en la base de datos
	 * 
	 * @param p Persona a modificar los datos
	 */
//	public void modificarUsuario(Persona p) {
//		personaDAO.modificarPersona(p);
//	}

	/**
	 * Borra a un Usuario en la base de datos
	 * 
	 * @param id Id de el usuario a borrar
	 */
//	public void borrarUsuario(long id) {
//		personaDAO.borrarPersona(id);
//	}

	/**
	 * Lista de Carreras en la base de datos
	 * 
	 * @return Lista de Carrera
	 */
//	public List<Carrera> listarCarreras() {
//		return carreraDAO.listarCarreras();
//	}

	/**
	 * retorna true si existe un usuario con el nombre ingresado
	 * 
	 * @param nombre
	 * @return
	 */
	public Boolean existeNombreUsuario(String nombre) {

		return usuarioDAO.existeNombreUsuario(nombre);
	}

	public void initPersona() {

		initDepartamentos();
		initItrs();
		initAreaTutor();
//		initCarreras();
		Analista p = new Analista();
		try {
			p.setActivo(true);
			p.setDocumento("50329199");
			p.setApellido1("demo");
			p.setNombre1("demo");
			p.setNombreUsuario("demo");
			p.setContrasena("demo");
			p.setDepartamento(depDAO.obtenerDepPorNombre("Durazno"));
			p.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
			p.setLocalidad("demo");
			p.setMail("demo@demo.com");
			p.setMailInstitucional("demo@demo.com");
			p.setTelefono("09999999");
			p.setValidado(true);
			p.setItr(itrDAO.obtenerItrPorNombre("Centro-sur"));

			usuarioDAO.crearUsuario(p);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Alumno a = new Alumno();
//		a.setAdmin(false);
//		a.setActivo(true);
//		a.setApellido1("Marshall");
//		a.setNombre1("Jean");
//		a.setNombreUsuario("jean.marshall");
//		a.setContrasena("demo");
//		a.setDireccion("demo");
//		a.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
//		a.setMail("jean.marshall@estudiantes.utec.edu.uy");
//		a.setIdEstudiantil((long) 2222);
//
//		a.setItr(itrDAO.buscarITR("Centro-sur"));
//		a.setCarrera(carreraDAO.buscarCarrera("LTI"));
//		a.setIdEstudiantil((long) 2222);
//		personaDAO.agregarPersona(a);
	}

	private void initDepartamentos() {

		try {
			depDAO.crearDepartamento("Rivera", true);
			depDAO.crearDepartamento("Durazno", true);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ArrayList<Departamento> deps = new ArrayList<>();
//		deps.add(new Departamento("Artigas", true));
//		deps.add(new Departamento("Canelones", true));
//		deps.add(new Departamento("Cerro Largo", true));
//		deps.add(new Departamento("Colonia", true));
//		deps.add(new Departamento("Durazno", true));
//		deps.add(new Departamento("Flores", true));
//		deps.add(new Departamento("Florida", true));
//		deps.add(new Departamento("Lavalleja", true));
//		deps.add(new Departamento("Maldonado", true));
//		deps.add(new Departamento("Montevideo", true));
//		deps.add(new Departamento("Paysandú", true));
//		deps.add(new Departamento("Rio Negro", true));
//		deps.add(new Departamento("Rivera", true));
//		deps.add(new Departamento("Rocha", true));
//		deps.add(new Departamento("Salto", true));
//		deps.add(new Departamento("San josé", true));
//		deps.add(new Departamento("Soriano", true));
//		deps.add(new Departamento("Tacuarembó", true));
//		deps.add(new Departamento("Treinta y Tres", true));
//
//		depDAO.agregarDepartamento(deps);
	}

	private void initItrs() {
		try {
			itrDAO.crearITR("Norte", depDAO.obtenerDepPorNombre("Rivera"), true);
			itrDAO.crearITR("Centro-sur", depDAO.obtenerDepPorNombre("Durazno"), true);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initAreaTutor() {
		try {
			areaTutorDAO.crearAreaTutor("Programacion");
			
			tipoTutorDAO.crearTipoTutor("Tutor");
			tipoTutorDAO.crearTipoTutor("Encargado");

		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private void initCarreras() {
//		carreraDAO.agregarCarrera(new Carrera("LTI", true));
//		carreraDAO.agregarCarrera(new Carrera("Mecatronica", true));
//	}

//	public Alumno buscarAlumno(long id) {
//		return alumnoDAO.buscar(id);
//	}
//
//	public Carrera buscarCarrera(String nombre) {
//		return carreraDAO.buscarCarrera(nombre);
//	}
//
//	public Carrera buscarCarrera(Long id) {
//		return carreraDAO.buscarCarrera(id);
//	}
//
//	public ITR buscarITR(String nombre) {
//		return itrDAO.buscarITR(nombre);
//	}
//
//	public Persona buscarPersona(long id) {
//		return personaDAO.buscarPersona(id);
//	}
}
