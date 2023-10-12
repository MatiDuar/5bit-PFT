package com.persistencia.dto;

public class UserDTO {

	
	private String nombreUsuario;
	private String password;
	
	
	
	
	public UserDTO() {
		super();
	}
	public UserDTO(String nombreUsuario, String password) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.password = password;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
		
}
