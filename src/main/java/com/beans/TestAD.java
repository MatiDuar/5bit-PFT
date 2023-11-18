package com.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.AccionReclamo;
import com.persistencia.entities.Estado;
import com.persistencia.entities.Reclamo;
import com.persistencia.exception.ServicesException;

import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.io.Serializable;

@Named(value = "gestionUsuariosAD") // JEE8
@SessionScoped // JEE8
public class TestAD implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static DirContext ldapContext;
	@Inject
	GestionPersona gestionPersonaBean;

	String mailLogin;
	String password;

	@PostConstruct
	void init() {

	}

	public void verfificarUsuarioAD() {
		try {

			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

			ldapEnv.put(Context.PROVIDER_URL, "ldap://5Bit.com.uy:389");

			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");

//			aquí va el nombre del usuario por lo general antecedido de un dominio pj EMPRESA//nombre_usuario");

			ldapEnv.put(Context.SECURITY_PRINCIPAL, mailLogin);

			ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "ssl");
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");
			ldapContext = new InitialDirContext(ldapEnv);

//			// Create the search controls
//			SearchControls searchCtls = new SearchControls();
//
//			// Specify the attributes to return
//			String returnedAtts[] = { "sn", "givenName", "samAccountName" ,"mail","distinguishedName","logonName"};
//			searchCtls.setReturningAttributes(returnedAtts);
//
//			// Specify the search scope
//			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//
//			// specify the LDAP search filter
////			String searchFilter = "(&(objectClass=user)(samAccountName=" +  + "))"; 
//		
////			String searchFilter = "(&(objectClass))";
//			
//			String searchFilter ="(&(objectClass=user)(sAMAccountName=" + mailLogin.split("@")[0] +"))"; 
//			// Specify the Base for the search
//			String searchBase = "dc=5Bit,dc=com,dc=uy";
//			// initialize counter to total the results
//			int totalResults = 0;
//
//			// Search for objects using the filter
//			NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
////			System.out.println(ldapContext.getNameInNamespace());
//			// Loop through the search results
//			

			gestionPersonaBean.verificarUsuarioAD(mailLogin.split("@")[0]);
			mailLogin = "";
			password = "";

			ldapContext.close();

		}  catch (AuthenticationException e) {

			String msg1 = "Usuario o Contrseña errónea";
			// mensaje autenticación incorrecta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			password = "";

		}catch (ServicesException e1) {

			String msg1 = e1.getMessage();
			// mensaje autenticación incorrecta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			password = "";

		}

		catch (Exception e) {

			String msg1 = "Error no esperado";

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			System.out.println(" Search error: " + e);

		}
	}

	public String getMailLogin() {
		return mailLogin;
	}

	public void setMailLogin(String mailLogin) {
		this.mailLogin = mailLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
