package com.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.dto.PersonaAlumnoDTO;
import com.persistencia.dto.PersonaLogeadaDTO;

import com.persistencia.entities.Analista;
import com.persistencia.entities.AreaTutor;
import com.persistencia.entities.Carrera;
import com.persistencia.entities.Departamento;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.ITR;
import com.persistencia.entities.TipoTutor;
import com.persistencia.entities.Tutor;
import com.persistencia.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;

@Named(value = "gestionPersona") // JEE8
@SessionScoped // JEE8
public class GestionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id = 1;
	@Inject
	GestionPersonaService persistenciaBean;
	@Inject
	LoginBeanJWT jwt;

//	private List<Alumno> personasMod;
	private Usuario usuarioLogeado;
	private Usuario personaSeleccionada;
//

//	private Usuario alumnoLogeado;


	private String carreraSeleccionada;
	private String itrSeleccionado;

	private String depSeleccionadoLog;
	private String itrSeleccionadoLog;
	private int anoIngresoLog;
	private String rolTutorLog;
	private String areaTutorLog;
	
	
	private String contrasenaModificar;
	
	private String tipoUsuario;
	private String departamentoSeleccionado;

	private String toRegistro;
	
	private List<Carrera> carreras;
	private List<ITR> itrs;
	private List<AreaTutor> areasTutor;
	private List<Departamento> departamentos;
	private List<TipoTutor>rolesTutor;

	
	private List<String> bottonesMenu;

	private java.util.Date fechaNacSel;
	private java.util.Date fechaNacLog;
	
	
	private String areaTutorSeleccionado;
	private String rolTutorSeleccionado;
	
	private int anoIngresoSeleccionado;

	private boolean isAlumno;

	private boolean isModContraseña;

	// Para saber si entro sin logearse al sistema
	private boolean isKicked;

	// Token de jwt
	private String token;

	private Claims datosToken;

	@PostConstruct
	public void init() {
		persistenciaBean.initPersona();
//		carreras = persistenciaBean.listarCarreras();

		try {
			itrs = persistenciaBean.listarITRs();
			areasTutor=persistenciaBean.listarAreaTutor();
			departamentos=persistenciaBean.listarDepartamento();
			rolesTutor=persistenciaBean.listarTipoTutor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tipoUsuario="";
		personaSeleccionada=new Analista();
		fechaNacSel = new java.util.Date();
		isAlumno = true;
		isModContraseña = false;
		toRegistro = "registro.xhtml?facesRedirect=true";
//		personasMod = new LinkedList<>();
		bottonesMenu=new LinkedList<>();

	}

	/**
	 * este es el metodo que verifica si el usuario esta en la BD y si esta activo,
	 * si esta activo se redirije a index
	 * 
	 * @return devuelve un sting con el path hacia la pagina donde hay que dirigirse
	 */
	public String verificarUsuario() {
		try {

			// Generar JSON Web Token
			token = jwt.generarToken(personaSeleccionada.getNombreUsuario(), personaSeleccionada.getContrasena());

			datosToken = jwt.obtenerClaim(token);

			// traemos los datos de la persona logeada a partir del Id Persona del token
			// generado
			Long idPersona = ((Double) datosToken.get("id")).longValue();
			
			usuarioLogeado = persistenciaBean.buscarUsuario(idPersona);
			
			if (!(Boolean) datosToken.get("activo")) {
				// Mensaje si el usuario esta inactivo
				String msg1 = "Usuario dado de baja del sistema";
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				return "";
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

			return "";

		} catch (Exception e) {
			e.printStackTrace();
			String msg1 = "Usuario o Contrseña errónea";
			// mensaje autenticación incorrecta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			return "";
		}
	}
	
//	public String verificarUsuario() {
//		
//		persistenciaBean.verificarUsuario(personaSeleccionada.getNombreUsuario(), personaSeleccionada.getContrasena());
//		return "index.xhtml";
//	}

	/**
	 * este metodo se encarga de crear una persona en la base de datos
	 * 
	 * @return devuelve la pagian en la que se tiene que redirigir
	 */
	public String agregarUsuario() {
		personaSeleccionada.setActivo(true);
		personaSeleccionada.setValidado(false);
		String nombreUsuario=personaSeleccionada.getMailInstitucional().split("@")[0];
		
		personaSeleccionada.setNombreUsuario(nombreUsuario);
		personaSeleccionada.setDepartamento(persistenciaBean.buscarDepartamento(departamentoSeleccionado));
		personaSeleccionada.setItr(persistenciaBean.buscarItr(itrSeleccionado));
		
		if (esAnalista()) {
			Analista analista=(Analista) personaSeleccionada;
			persistenciaBean.agregarUsuario(analista);
			
		} else if(esDocente()){
			Tutor tutor=new Tutor();
			
			copiarDatos(personaSeleccionada, tutor);
			
			tutor.setAreaTutor(persistenciaBean.buscarAreaTutor(areaTutorSeleccionado));
			tutor.setTipoTutor(persistenciaBean.buscarTipoTutor(rolTutorSeleccionado));
			persistenciaBean.agregarUsuario(tutor);
			

		}else if(esEstudiante()) {
			Estudiante estudiante=new Estudiante();
			copiarDatos(personaSeleccionada, estudiante);
			estudiante.setAnoIngreso(anoIngresoSeleccionado);
			
			persistenciaBean.agregarUsuario(estudiante);

		}
		reset();
		String msg1 = "Se creo correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		String url = "login.xhtml";
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public void copiarDatos(Usuario a, Usuario b) {
		b.setActivo(a.getActivo());
		b.setApellido1(a.getApellido1());
		b.setApellido2(a.getApellido2());
		b.setContrasena(a.getContrasena());
		b.setDepartamento(a.getDepartamento());
		b.setDocumento(a.getDocumento());
		b.setFechaNacimiento(a.getFechaNacimiento());
		b.setGenero(a.getGenero());
		b.setItr(a.getItr());
		b.setLocalidad(a.getLocalidad());
		b.setMail(a.getMail());
		b.setMailInstitucional(a.getMailInstitucional());
		b.setNombre1(a.getNombre1());
		b.setNombre2(a.getNombre2());
		b.setNombreUsuario(a.getNombreUsuario());
		b.setTelefono(a.getTelefono());
		b.setValidado(a.getValidado());
	}

	/**
	 * Esta funcion modifica al usuario logeado con los cambios realizados
	 * 
	 * @return
	 */
	public String modificarUsuario() {
		
		usuarioLogeado.setItr(persistenciaBean.buscarItr(itrSeleccionadoLog));
		usuarioLogeado.setDepartamento(persistenciaBean.buscarDepartamento(depSeleccionadoLog));
		
		if(esEstudianteLogeado()) {
			((Estudiante) usuarioLogeado).setAnoIngreso(anoIngresoLog);;
		}
		
		if(esTutorLogeado()) {
			((Tutor) usuarioLogeado).setAreaTutor(persistenciaBean.buscarAreaTutor(areaTutorLog));
			((Tutor) usuarioLogeado).setTipoTutor(persistenciaBean.buscarTipoTutor(rolTutorLog));
		}
		persistenciaBean.modificarUsuario(usuarioLogeado);

		String msg1 = "Se modifico correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * La funcion se encarga de modificar una persona en la lista
	 * 
	 * @param p recibe la persona a modificar
	 * @return
	 */

//	public String modificarPersonaOnLista(Persona p) {
//
//		persistenciaBean.modificarUsuario(p);
//
//		String msg1 = "Se modifico correctamente el usuario";
//		// mensaje de actualizacion correcta
//		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
//		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//		return "";
//	}

	/**
	 * La funcion se encarga de dar de baja a una persona en la base de datos
	 * 
	 * @param pDto Objeto que almacena la tabla
	 * @return
	 */

	public String bajaPersonaOnLista(Usuario usuario) {

		usuario.setActivo(false);
		persistenciaBean.modificarUsuario(usuario);
		String msg1 = "Se elimino correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * La funcion se encarga de activar a una persona en la base de datos
	 * 
	 * @param pDto Objeto que almacena la tabla
	 * @return
	 */

	public String activarPersonaOnLista(Usuario usuario) {
		usuario.setActivo(true);
		persistenciaBean.modificarUsuario(usuario);
		String msg1 = "Se activo correctamente el usuario";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * La funcion modifica la contraseña del usuario logeado
	 * 
	 * @return
	 */
	public String modificarContrasena() {
		
		
		
		usuarioLogeado.setContrasena(contrasenaModificar);

		persistenciaBean.modificarUsuario(usuarioLogeado);

		String msg1 = "Se modifico correctamente la Contraseña";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		isModContraseña = false;
		return "";
	}

	/**
	 * Mensaje cuando se cierra el modificar contraseña
	 * 
	 * @return
	 */
	public String cerrarModificarContrasena() {
		String msg1 = "Se cancelo la modificacion de la Contraseña";
		// mensaje de actualizacion correcta
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		return "";
	}

	/**
	 * Reinicia todos la mayoria de datos en el bean
	 */
	private void reset() {
		personaSeleccionada = new Analista();
		fechaNacSel = null;
		anoIngresoSeleccionado=0;
		areaTutorSeleccionado="";
		departamentoSeleccionado="";
		itrSeleccionado="";
		tipoUsuario="";
		carreraSeleccionada = "";
		itrSeleccionado = "";
		token = "";
		datosToken = null;
	}

	/**
	 * Copia todos los datos de la Persona al Alumno
	 * 
	 * @param p Persona con los datos originales
	 * @param a Alumno a modificar los datos
	 */
//	public void parsePersona(Persona p, Alumno a) {
//		a.setId(p.getId());
//		a.setActivo(p.getActivo());
//		a.setNombre1(p.getNombre1());
//		a.setNombre2(p.getApellido2());
//		a.setApellido1(p.getApellido1());
//		a.setApellido2(p.getApellido2());
//		a.setNombreUsuario(p.getNombreUsuario());
//		a.setContrasena(p.getContrasena());
//		a.setMail(p.getMail());
//		a.setDireccion(p.getDireccion());
//		a.setFechaNacimiento(p.getFechaNacimiento());
//	}

	/**
	 * Funcion que al terminar de editar la persona en la tabla realiza los cambios
	 * realizados
	 * 
	 * @param persona Persona con los datos a modificar
	 */
	public void onRowEdit(RowEditEvent<Usuario> persona) {

//		if (persona.getObject().getCarrera() == null) {
//			Persona modPersona = parsePersonaFromDTO(persona.getObject());
//			modificarPersonaOnLista(modPersona);
//		} else {
//			Alumno modAlumno = parseAlumnoFromDTO(persona.getObject());
//			modificarPersonaOnLista(modAlumno);
//		}
		persistenciaBean.modificarUsuario(persona.getObject());
			
	}

	/**
	 * Funcion que permite parsear un objeto PersonaAlumnoDTO a un objeto Persona
	 * 
	 * @param pdto Objeto que tiene los datos
	 * @return Persona con todos los datos de pdto
	 */
//	public Persona parsePersonaFromDTO(PersonaAlumnoDTO pdto) {
//		Persona p = persistenciaBean.buscarPersona(pdto.getId());
//		p.setActivo(pdto.getActivo());
//		p.setNombre1(pdto.getNombre1());
//		p.setApellido1(pdto.getApellido1());
//		p.setMail(pdto.getMail());
//		p.setNombreUsuario(pdto.getNombreUsuario());
//		p.setDireccion(pdto.getDireccion());
//
//		return p;
//
//	}

	/**
	 * Funcion que permite parsear un objeto PersonaAlumnoDTO a un objeto Alumno
	 * 
	 * @param pdto Objeto que tiene los datos
	 * @return Alumno con todos los datos de pdto
	 */
//	public Alumno parseAlumnoFromDTO(PersonaAlumnoDTO pdto) {
//		Alumno a = persistenciaBean.buscarAlumno(pdto.getId());
//		a.setActivo(pdto.getActivo());
//		a.setNombre1(pdto.getNombre1());
//		a.setApellido1(pdto.getApellido1());
//		a.setMail(pdto.getMail());
//		a.setNombreUsuario(pdto.getNombreUsuario());
//		a.setDireccion(pdto.getDireccion());
//		a.setIdEstudiantil(pdto.getIdEstudiantil());
//		a.setCarrera(persistenciaBean.buscarCarrera(pdto.getCarrera()));
//
//		return a;
//
//	}

	public void onRowCancel(RowEditEvent<PersonaAlumnoDTO> persona) {

		FacesMessage msg = new FacesMessage("Edit Cancelled", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String toggleModContrasena() {
		isModContraseña = !isModContraseña;
		return "";
	}

//	public Alumno esAlumnoLogeado() {
//		return persistenciaBean.buscarAlumno(personaLogeada.getId());
//	}

	public java.util.Date getFechaNacSel() {
		return fechaNacSel;
	}

	/**
	 * Funcion que da de baja al usuario Logeado
	 * 
	 * @return
	 */
//	public String darDeBaja() {
//
//		personaLogeada.setActivo(false);
//		persistenciaBean.modificarUsuario(personaLogeada);
//		reset();
//		String msg1 = "Se dio de baja al Usuario";
//		// mensaje de actualizacion correcta
//		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg1, "");
//		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//		return "login.xhtml?facesRedirect=true";
//	}

	public String cerrarSesion() {
		reset();
		return "login.xhtml?facesRedirect=true";

	}

	/**
	 * Este metodo valida si el usuario esta logeado
	 */
	public void checkUserIsLogin() {
		if (usuarioLogeado.getId()==null || usuarioLogeado == null) {
			try {

				isKicked = true;
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public void msjKick() {
		if (isKicked) {
			String msg1 = "Debes estar ingresado para esa funcionalidad";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			isKicked = false;
		}
	}
	public boolean esEstudiante() {
		return tipoUsuario.equals("estudiante");
	}
	public boolean esDocente() {
		return tipoUsuario.equals("docente");
	}
	public boolean esAnalista() {
		return tipoUsuario.equals("analista");
	}
	
	public boolean esAnalistaLogeado() {
		return usuarioLogeado instanceof Analista;
	}
	
	public boolean esEstudianteLogeado() {
		return usuarioLogeado instanceof Estudiante;
	}
	
	public boolean esTutorLogeado() {
		return usuarioLogeado instanceof Tutor;
	}

	public boolean esEstudianteMod(Usuario usuario) {
		return usuario instanceof Estudiante;
	}
	public void setFechaNacSel(java.util.Date fechaNacSel) {
		personaSeleccionada.setFechaNacimiento(new java.sql.Date(fechaNacSel.getTime()));
		this.fechaNacSel = fechaNacSel;
	}

	public String getToRegistro() {
		reset();
		return toRegistro;
	}

	public void setToRegistro(String toRegistro) {
		this.toRegistro = toRegistro;
	}

//	public Persona getPersonaLogeada() {
//		return personaLogeada;
//	}
//
//	public void setPersonaLogeada(Persona personaLogeada) {
//		this.personaLogeada = personaLogeada;
//	}
//
	public Usuario getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Usuario personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public boolean isAlumno() {
		return isAlumno;
	}

	public void setAlumno(boolean isAlumno) {
		this.isAlumno = isAlumno;
	}

//	public Alumno getAlumnoSeleccionado() {
//		return alumnoSeleccionado;
//	}
//
//	public void setAlumnoSeleccionado(Alumno alumnoSeleccionado) {
//		this.alumnoSeleccionado = alumnoSeleccionado;
//	}
//
//	public String getCarreraSeleccionada() {
//		return carreraSeleccionada;
//	}
//
//	public void setCarreraSeleccionada(String carreraSeleccionada) {
//		alumnoSeleccionado.setCarrera(persistenciaBean.buscarCarrera(carreraSeleccionada));
//		this.carreraSeleccionada = carreraSeleccionada;
//	}

	public List<ITR> getItrs() {
		return itrs;
	}

	public void setItrs(List<ITR> itrs) {
		this.itrs = itrs;
	}

	public String getItrSeleccionado() {
		return itrSeleccionado;
	}

	public void setItrSeleccionado(String itrSeleccionado) {
		this.itrSeleccionado = itrSeleccionado;
	}

	public java.util.Date getFechaNacLog() {
		fechaNacLog = usuarioLogeado.getFechaNacimiento();
		return fechaNacLog;
	}

	public void setFechaNacLog(java.util.Date fechaNacLog) {
		usuarioLogeado.setFechaNacimiento(new java.sql.Date(fechaNacLog.getTime()));

		this.fechaNacLog = fechaNacLog;
	}
//
//	public String getCarreraSeleccionadaLog() {
//		carreraSeleccionadaLog = alumnoLogeado.getCarrera().getNombre();
//		return carreraSeleccionadaLog;
//	}
//
//	public void setCarreraSeleccionadaLog(String carreraSeleccionadaLog) {
//		alumnoLogeado.setCarrera(persistenciaBean.buscarCarrera(carreraSeleccionadaLog));
//		this.carreraSeleccionadaLog = carreraSeleccionadaLog;
//	}
//
//	public String getItrSeleccionadoLog() {
//		itrSeleccionadoLog = alumnoLogeado.getItr().getNombre();
//		return itrSeleccionadoLog;
//	}
//
//	public void setItrSeleccionadoLog(String itrSeleccionadoLog) {
//
//		alumnoLogeado.setItr(persistenciaBean.buscarITR(itrSeleccionadoLog));
//
//		this.itrSeleccionadoLog = itrSeleccionadoLog;
//	}
//
//	public Alumno getAlumnoLogeado() {
//		return alumnoLogeado;
//	}
//
//	public void setAlumnoLogeado(Alumno alumnoLogeada) {
//		this.alumnoLogeado = alumnoLogeada;
//	}

	public String getContrasenaModificar() {
		return contrasenaModificar;
	}

	public void setContrasenaModificar(String contrasenaModificar) {
		this.contrasenaModificar = contrasenaModificar;
	}

	public boolean isModContraseña() {
		return isModContraseña;
	}

	public void setModContraseña(boolean isModContraseña) {
		this.isModContraseña = isModContraseña;
	}



//	public List<Alumno> getPersonasMod() {
//		return personasMod;
//	}
//
//	public void setPersonasMod(List<Alumno> personasMod) {
//		this.personasMod = personasMod;
//	}

	public boolean isKicked() {
		return isKicked;
	}

	public void setKicked(boolean isKicked) {
		this.isKicked = isKicked;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getBottonesMenu() {


		// Obtener la ruta de la vista actual
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

		if(viewId.equals("/index.xhtml") || viewId.equals("/listarPersonas.xhtml") || viewId.equals("/editarPerfil.xhtml")) {
			bottonesMenu=new LinkedList<>();
		}else{
			bottonesMenu=new LinkedList<>();
			bottonesMenu.add("Boton 1");
			bottonesMenu.add("Boton 2");
			bottonesMenu.add("Boton 3");
			bottonesMenu.add("Boton 4");			
		}
		return bottonesMenu;
	}

	public void setBottonesMenu(List<String> bottonesMenu) {
		this.bottonesMenu = bottonesMenu;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}



	public String getAreaTutorSeleccionado() {
		return areaTutorSeleccionado;
	}

	public void setAreaTutorSeleccionado(String areaTutorSeleccionado) {
		this.areaTutorSeleccionado = areaTutorSeleccionado;
	}

	public List<AreaTutor> getAreasTutor() {
		return areasTutor;
	}

	public void setAreasTutor(List<AreaTutor> areasTutor) {
		this.areasTutor = areasTutor;
	}

	public String getRolTutorSeleccionado() {
		return rolTutorSeleccionado;
	}

	public void setRolTutorSeleccionado(String rolTutorSeleccionado) {
		this.rolTutorSeleccionado = rolTutorSeleccionado;
	}

	public int getAnoIngresoSeleccionado() {
		return anoIngresoSeleccionado;
	}

	public void setAnoIngresoSeleccionado(int anoIngresoSeleccionado) {
		this.anoIngresoSeleccionado = anoIngresoSeleccionado;
	}

	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public List<TipoTutor> getRolesTutor() {
		return rolesTutor;
	}

	public void setRolesTutor(List<TipoTutor> rolesTutor) {
		this.rolesTutor = rolesTutor;
	}

	public String getDepSeleccionadoLog() {
		depSeleccionadoLog=usuarioLogeado.getDepartamento().getNombre();
		return depSeleccionadoLog;
	}

	public void setDepSeleccionadoLog(String depSeleccionadaLog) {
		this.depSeleccionadoLog = depSeleccionadaLog;
	}

	public String getItrSeleccionadoLog() {
		itrSeleccionadoLog=usuarioLogeado.getItr().getNombre();
		return itrSeleccionadoLog;
	}

	public void setItrSeleccionadoLog(String itrSeleccionadoLog) {
		this.itrSeleccionadoLog = itrSeleccionadoLog;
	}

	public int getAnoIngresoLog() {
		anoIngresoLog=((Estudiante) usuarioLogeado).getAnoIngreso();
		return anoIngresoLog;
	}

	public void setAnoIngresoLog(int anoIngresoLog) {
		this.anoIngresoLog = anoIngresoLog;
	}

	public String getRolTutorLog() {
		rolTutorLog=((Tutor) usuarioLogeado).getTipoTutor().getNombre();
		return rolTutorLog;
	}

	public void setRolTutorLog(String rolTutorLog) {
		this.rolTutorLog = rolTutorLog;
	}

	public String getAreaTutorLog() {
		areaTutorLog=((Tutor) usuarioLogeado).getAreaTutor().getNombre();
		return areaTutorLog;
	}

	public void setAreaTutorLog(String areaTutorLog) {
		this.areaTutorLog = areaTutorLog;
	}
	
	
	
	

}
