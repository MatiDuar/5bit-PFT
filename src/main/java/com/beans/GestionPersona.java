package com.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.dao.UsuarioDAO;
import com.persistencia.entities.Analista;
import com.persistencia.entities.AreaTutor;
import com.persistencia.entities.Carrera;
import com.persistencia.entities.Departamento;
import com.persistencia.entities.Estudiante;
import com.persistencia.entities.ITR;
import com.persistencia.entities.TipoTutor;
import com.persistencia.entities.Tutor;
import com.persistencia.entities.Usuario;
import com.persistencia.exception.ServicesException;

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
	DFView dfView;
	
	@Inject
	LoginBeanJWT jwt;
	
   

//	private List<Alumno> personasMod;
	private Usuario usuarioLogeado;
	private Usuario personaSeleccionada;
	
	private Usuario usuarioModificar;
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
	
	private boolean editando;
	
	private String mailInst;

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
		editando=false;
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
	public void verificarUsuario() {
		try {

			// Generar JSON Web Token
			token = jwt.generarToken(personaSeleccionada.getNombreUsuario(), personaSeleccionada.getContrasena());

			datosToken = jwt.obtenerClaim(token);

			// traemos los datos de la persona logeada a partir del Id Persona del token
			// generado
			Long idPersona = ((Double) datosToken.get("id")).longValue();
			
			usuarioLogeado = persistenciaBean.buscarUsuario(idPersona);
			
			String msg1="";
			if (!(Boolean) datosToken.get("activo")) {
				// Mensaje si el usuario esta inactivo
				msg1 = "Usuario dado de baja del sistema";
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				
			}else
			
			if(!(Boolean) datosToken.get("validado")) {
				// Mensaje si el usuario esta invalidado
				msg1 = "Usuario no está validado, a la espera de validación";
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg1, "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				
			}else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

			}

			

		} catch (Exception e) {
		
			String msg1 = "Usuario o Contrseña errónea";
			// mensaje autenticación incorrecta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			
		}
	}
	public void verificarUsuarioAD(String nombreUsuario) {
		try {

			// Generar JSON Web Token
			token = jwt.generarTokenAD(nombreUsuario);

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
			
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

			

		} catch (Exception e) {
		
			String msg1 = "Usuario o Contrseña errónea";
			// mensaje autenticación incorrecta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			
		}
	}


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
		String msg1 = "Se creo correctamente el usuario. En espera por validación";
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

	
	
	public Estudiante usuarioToEstudiante(Usuario u) {
		if(u instanceof Estudiante) {
			return (Estudiante) u;
		}
		return  null;
	}
	
	public Tutor usuarioToTutor(Usuario u) {
		if(u instanceof Tutor) {
			return (Tutor) u;
		}
		return  null;
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

	
	public void modificarUsuarioEstado() {
		persistenciaBean.modificarUsuario(usuarioModificar);
		dfView.closeResponsive();
	}
	
	public void abrirModificarEstado(Usuario usuario) {
		usuarioModificar=usuario;
		dfView.viewEstadoUsuario();
	}

	/**
	 * Funcion que al terminar de editar la persona en la tabla realiza los cambios
	 * realizados
	 * 
	 * @param Usuario Persona con los datos a modificar
	 */
	public void onRowEdit(RowEditEvent<Usuario> persona) {

		
		persistenciaBean.modificarUsuario(persona.getObject());
			
	}



	

	public void onRowCancel(RowEditEvent<Usuario> persona) {

		FacesMessage msg = new FacesMessage("Edit Cancelled", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String toggleModContrasena() {
		isModContraseña = !isModContraseña;
		return "";
	}



	public java.util.Date getFechaNacSel() {
		return fechaNacSel;
	}


	public void cerrarSesion() {
		reset();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void toLogin() {
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	
	
	public String getEstado(Usuario u) {
		if (u.getActivo() && u.getValidado()) {
			return "Validado";
		}else if(u.getActivo() && !u.getValidado()) {
			return "Sin Validar";
		}else {
			return "Eliminado";
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
	
	
	public boolean esTutorMod(Usuario usuario) {
		return usuario instanceof Tutor;
	}
	public void setFechaNacSel(java.util.Date fechaNacSel) {
		personaSeleccionada.setFechaNacimiento(new java.sql.Date(fechaNacSel.getTime()));
		this.fechaNacSel = fechaNacSel;
	}
	
	public String tipoUsuario(Usuario u) {
		if(u instanceof Estudiante) {
			return "Estudiante";
		}
		if(u instanceof Tutor) {
			return "Tutor";
		}
		if(u instanceof Analista) {
			return "Analista";
		}
		
		return null;
	}
	
	public void darDeBaja(Usuario u) {
		u.setActivo(false);
		persistenciaBean.modificarUsuario(u);
	}
	
	
	public String getToRegistro() {
		reset();
		return toRegistro;
	}

	
	public void updateITRs()  {
		try {
			itrs=persistenciaBean.listarITRs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setToRegistro(String toRegistro) {
		this.toRegistro = toRegistro;
	}


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

		if(viewId.equals("/index.xhtml")  || viewId.equals("/editarPerfil.xhtml") || viewId.equals("/listarPersonas.xhtml")) {
			bottonesMenu=new LinkedList<>();
		}else if((viewId.equals("/eventos.xhtml") || viewId.contains("altaEvento.xhtml")) && esAnalistaLogeado()) {
			
			bottonesMenu=new LinkedList<>();
			bottonesMenu.add("Listado de Eventos");
			bottonesMenu.add("Alta Evento");
			bottonesMenu.add("Mantenimiento Estados");
			bottonesMenu.add("Mantenimiento Modalidades");
			
		}else{
			bottonesMenu=new LinkedList<>();
					
		}
		return bottonesMenu;
	}
	
	public String getActionBottonesMenu(String boton) {
		
		switch(boton) {
		case "Alta Evento":
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("altaEvento.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
			
			
		case "Listado de Eventos":
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		case "Mantenimiento Estados":
			dfView.viewMantenimientoEstadosEventos();
			return "";
		case "Mantenimiento Modalidades":
			dfView.viewMantenimientoModalidadEventos();
			return "";
		default:
			return "";		
		}
	}
	

		   


	
	public Date fecMayoriaEdad(){
		
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		c.add(Calendar.YEAR, -18);
		Date newDate = c.getTime();
		
		return newDate;
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

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public Usuario getUsuarioModificar() {
		return usuarioModificar;
	}

	public void setUsuarioModificar(Usuario usuarioModificar) {
		this.usuarioModificar = usuarioModificar;
	}


	
	
	
	
	

}


