package com.persistencia.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.*;



@Entity
@Table(name = "USUARIOS")
@Inheritance(strategy=InheritanceType.JOINED)
@SequenceGenerator(name = "USUARIO_SEC", initialValue = 1, allocationSize = 1)
public abstract class Usuario implements Serializable {

	public Usuario() {
		super();
	}
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEC" )
	@Column(name="ID_USUARIO")
	private Long id;
	
	@Column(name="DOCUMENTO",nullable=false,length=50)
	private String documento;
	
	@Column(name="NOMBRE_USUARIO",nullable=false,length=50,unique=true)
	private String nombreUsuario;
	
	@Column(name="CONTRASENA",nullable=false,length=50)
	private String contrasena;
	
	@Column(name="APELLIDO1",nullable=false,length=50)
	private String apellido1;
	
	@Column(name="APELLIDO2",length=50)
	private String apellido2;
	
	@Column(name="NOMBRE1",nullable=false,length=50)
	private String nombre1;
	
	@Column(name="NOMBRE2",length=50)
	private String nombre2;
	
	@Column(name="FEC_NAC",nullable=false)
	private Date fechaNacimiento;
	
	@ManyToOne
	@JoinColumn(name="ID_GENERO")
	private Genero genero;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "ID_DEPARTAMENTO")
	private Departamento departamento;
	
	@Column(name="LOCALIDAD",nullable=false,length=150)
	private String localidad;
	
	@Column(name="MAIL",nullable=false,length=50)
	private String mail;
	
	@Column(name="TELEFONO",nullable=false,length=50)
	private String telefono;
	
	@Column(name="MAIL_INSTITUCIONAL",nullable=false,length=50)
	private String mailInstitucional;
	
	
	@Column(name="VALIDADO",nullable=false)
	private Boolean validado;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "ID_ITR")
	private ITR itr;
	
	@Column(name="ACTIVO",nullable=false)
	private Boolean activo;
	
	

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getMailInstitucional() {
		return mailInstitucional;
	}

	public void setMailInstitucional(String mail) {
		this.mailInstitucional = mail;
	}


	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public ITR getItr() {
		return itr;
	}

	public void setItr(ITR itr) {
		this.itr = itr;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	

	public Boolean getValidado() {
		return validado;
	}

	public void setValidado(Boolean valido) {
		this.validado = valido;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", documento=" + documento + ", nombreUsuario=" + nombreUsuario + ", contrasena="
				+ contrasena + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", nombre1=" + nombre1
				+ ", nombre2=" + nombre2 + ", fechaNacimiento=" + fechaNacimiento + ", genero=" + genero
				+ ", departamento=" + departamento + ", localidad=" + localidad + ", mail=" + mail + ", telefono="
				+ telefono + ", itr=" + itr + ", activo=" + activo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	
	
	
	
	
	
   
}
