package com.logicaNegocio;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.AreaTutorDAO;
import com.persistencia.dao.CarreraDAO;
import com.persistencia.dao.ConvocatoriaAsistenciaDAO;
import com.persistencia.dao.DepartamentoDAO;
import com.persistencia.dao.EstadoAsistenciaDAO;
import com.persistencia.dao.EstadoDAO;
import com.persistencia.dao.EstadosEventosDAO;
import com.persistencia.dao.EventoDAO;
import com.persistencia.dao.ItrDAO;
import com.persistencia.dao.ModalidadesEventosDAO;
import com.persistencia.dao.ReclamoDAO;
import com.persistencia.dao.TipoActividadDAO;
import com.persistencia.dao.TipoTutorDAO;
import com.persistencia.dao.TutorDAO;
import com.persistencia.dao.UsuarioDAO;
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
	EventoDAO eventoDAO;

	@EJB
	AreaTutorDAO areaTutorDAO;

	@EJB
	TipoTutorDAO tipoTutorDAO;

	@EJB
	DepartamentoDAO departamentoDAO;

	@EJB
	EstadosEventosDAO estadosEventoDAO;

	@EJB
	TipoActividadDAO tipoActividadDAO;

	@EJB
	ModalidadesEventosDAO modalidadEventosDAO;

	@EJB
	TutorDAO tutorDAO;

	@EJB
	ReclamoDAO reclamoDAO;

	@EJB
	EstadoDAO estadoDAO;

	@EJB
	EstadosEventosDAO estadosEventosDAO;

	@EJB
	ConvocatoriaAsistenciaDAO convocatoriaAsistenciaDAO;

	@EJB
	EstadoAsistenciaDAO estadoAsistenciaDAO;
	
	
	



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

	public List<ModalidadesEventos> listarModalidadesEventos() throws Exception {

		return modalidadEventosDAO.obtenerModalidadesEventos();
	}

	public List<EstadosEventos> listarEstadosEventos() throws Exception {
		return estadosEventosDAO.obtenerEstadosEventos();
	}

	public List<AreaTutor> listarAreaTutor() throws Exception {
		List<AreaTutor> listaAreaTutor = areaTutorDAO.obtenerAreaTutor();

		return listaAreaTutor;
	}

	public List<Departamento> listarDepartamento() throws Exception {
		List<Departamento> listaDepartamento = departamentoDAO.obtenerDepartamento();
		return listaDepartamento;
	}

	public List<Evento> listarEventos() throws Exception {
		return eventoDAO.obtenerEvento();
	}
	
	public List<Estado> listarEstadoReclamo() throws ServicesException{
		return estadoDAO.obtenerEstados();
	}

	
	public List<Reclamo>listarReclamo() throws ServicesException{
		return reclamoDAO.obtenerReclamos();
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

	public void agregarDep(Departamento dep) {
		try {
			departamentoDAO.crearDepartamento(dep.getNombre(), dep.getActivo());
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Tutor t = new Tutor();
			t.setActivo(true);
			t.setDocumento("50329199");
			t.setApellido1("demo");
			t.setNombre1("demo");
			t.setNombreUsuario("demo.tutor");
			t.setContrasena("demo");
			t.setDepartamento(depDAO.obtenerDepPorNombre("Durazno"));
			t.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
			t.setLocalidad("demo");
			t.setMail("demo@demo.com");
			t.setMailInstitucional("demo@demo.com");
			t.setTelefono("09999999");
			t.setValidado(true);
			t.setItr(itrDAO.obtenerItrPorNombre("Centro-sur"));
			t.setTipoTutor(tipoTutorDAO.buscarTipoTutorPorId((long) 1));
			t.setAreaTutor(areaTutorDAO.buscarAreaTutorPorId((long) 1));

			usuarioDAO.crearUsuario(t);

			Estudiante e = new Estudiante();
			e.setActivo(true);
			e.setDocumento("50329190");
			e.setApellido1("demo");
			e.setNombre1("demo");
			e.setNombreUsuario("demo.estudiante");
			e.setContrasena("demo");
			e.setDepartamento(depDAO.obtenerDepPorNombre("Durazno"));
			e.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
			e.setLocalidad("demo");
			e.setMail("demo@demo.com");
			e.setMailInstitucional("demo@demo.com");
			e.setTelefono("09999999");
			e.setValidado(true);
			e.setItr(itrDAO.obtenerItrPorNombre("Centro-sur"));
			e.setAnoIngreso(2022);
			usuarioDAO.crearUsuario(e);
			
			Estudiante e2 = new Estudiante();
			e2.setActivo(true);
			e2.setDocumento("50329190");
			e2.setApellido1("demo");
			e2.setNombre1("demo");
			e2.setNombreUsuario("demo.estudiante2");
			e2.setContrasena("demo");
			e2.setDepartamento(depDAO.obtenerDepPorNombre("Durazno"));
			e2.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
			e2.setLocalidad("demo");
			e2.setMail("demo@demo.com");
			e2.setMailInstitucional("demo@demo.com");
			e2.setTelefono("09999999");
			e2.setValidado(true);
			e2.setItr(itrDAO.obtenerItrPorNombre("Centro-sur"));
			e2.setAnoIngreso(2022);
			usuarioDAO.crearUsuario(e2);

			Tutor t2 = new Tutor();
			t2.setActivo(true);
			t2.setDocumento("50329199");
			t2.setApellido1("demo");
			t2.setNombre1("demoTutor2");
			t2.setNombreUsuario("demo.tutor2");
			t2.setContrasena("demo");
			t2.setDepartamento(depDAO.obtenerDepPorNombre("Durazno"));
			t2.setFechaNacimiento(new Date(2002 - 1900, 02, 04));
			t2.setLocalidad("demo");
			t2.setMail("demo@demo.com");
			t2.setMailInstitucional("demo@demo.com");
			t2.setTelefono("09999999");
			t2.setValidado(true);
			t2.setItr(itrDAO.obtenerItrPorNombre("Centro-sur"));
			t2.setTipoTutor(tipoTutorDAO.buscarTipoTutorPorId((long) 1));
			t2.setAreaTutor(areaTutorDAO.buscarAreaTutorPorId((long) 1));

			usuarioDAO.crearUsuario(t2);

			initEventos();
			initReclamo();
			initEstadosAsistencia();
		} catch (ServicesException e) {
			e.printStackTrace();
		}

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

	private void initEstadosAsistencia() {
//		Asistencia, Media Asistencia -
//		Matutina/Vespertina - Ausencia, Ausencia Justificada
		try {
			
			EstadoAsistencia ca4 = new EstadoAsistencia();
			ca4.setNombre("Sin Registrar");

			estadoAsistenciaDAO.crear(ca4);
			EstadoAsistencia ca = new EstadoAsistencia();
			ca.setNombre("Asistencia");

			estadoAsistenciaDAO.crear(ca);
			
			EstadoAsistencia ca1 = new EstadoAsistencia();
			ca1.setNombre("Media Asistencia");

			estadoAsistenciaDAO.crear(ca1);
			EstadoAsistencia ca2 = new EstadoAsistencia();
			ca2.setNombre("Ausencia");

			estadoAsistenciaDAO.crear(ca2);
			EstadoAsistencia ca3 = new EstadoAsistencia();
			ca3.setNombre("Ausencia Justificada");

			estadoAsistenciaDAO.crear(ca3);
			
			
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public void initEventos() {
		try {
			EstadosEventos ee = new EstadosEventos();
			ee.setActivo(true);
			ee.setNombre("Futuro");
			estadosEventoDAO.crearEstadoEvento(ee);

			ModalidadesEventos md = new ModalidadesEventos();

			md.setActivo(true);
			md.setNombre("Presencial");
			modalidadEventosDAO.crearModalidadEvento(md);

			ModalidadesEventos md1 = new ModalidadesEventos();
			md1.setActivo(true);
			md1.setNombre("Semipresencial");
			modalidadEventosDAO.crearModalidadEvento(md1);

			ModalidadesEventos md2 = new ModalidadesEventos();
			md2.setActivo(true);
			md2.setNombre("Virtual");
			modalidadEventosDAO.crearModalidadEvento(md2);

			tipoActividadDAO.crearTipoActividad("Examen", true, true);

			tipoActividadDAO.crearTipoActividad("Jornada presencial", true, false);

			tipoActividadDAO.crearTipoActividad("Prueba final", true, true);

			tipoActividadDAO.crearTipoActividad("Defensa de proyecto", true, true);

			Evento evento = new Evento();
			evento.setTitulo("Demo");
			evento.setCreditos("2");
			evento.setFechaInicio(new Timestamp(2023 - 1900, 10, 7, 9, 0, 0, 0));
			evento.setFechaFin(new Timestamp(2023 - 1900, 10, 7, 17, 0, 0, 0));
			evento.setItr(itrDAO.buscarItrPorId((long) 1));
			evento.setLocalizacion("Demo");
			evento.setSemestre("4");
			evento.addAnalista((Analista) usuarioDAO.buscarNombre("demo"));
			evento.addTutor((Tutor) usuarioDAO.buscarNombre("demo.tutor"));
			evento.setEstado(estadosEventoDAO.buscarNombreEstadoEvento("Futuro"));
			evento.setTipoActividad(tipoActividadDAO.buscarTipoActividadPorId((long) 1));
			evento.setModalidad(modalidadEventosDAO.buscarModalidadEventoPorId((long) 1));
			eventoDAO.crearEvento(evento);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initReclamo() {
		Reclamo reclamo = new Reclamo();
		Estado estado = new Estado();
		estado.setActivo(true);
		estado.setNombre("Ingresado");
		
		Estado estado2= new Estado();
		estado2.setActivo(true);
		estado2.setNombre("En Proceso");
		Estado estado3 = new Estado();
		estado3.setActivo(true);
		estado3.setNombre("Finalizado");
		

		try {
			estadoDAO.crearEstado(estado);
			estadoDAO.crearEstado(estado2);
			estadoDAO.crearEstado(estado3);
			reclamo.setTitulo("demo");
			reclamo.setDetalle("demo");
			reclamo.setFechaHora(new Timestamp(2022-1900, 7, 20, 15, 0, 0, 0));

			reclamo.setNombreEventoVME("demo");
			reclamo.setEstudiante((Estudiante) usuarioDAO.buscarNombre("demo.estudiante"));
			reclamo.setEstado(estadoDAO.buscarEstadoPorId((long) 1));
			reclamoDAO.crearReclamo(reclamo);

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
