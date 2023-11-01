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
	public String verfificarUsuarioAD() {
		try {

			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

			ldapEnv.put(Context.PROVIDER_URL, "ldap://5Bit.com.uy:389");

			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		

//			ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=Admin Admin,ou=Users,dc=5Bit,dc=com.uy");
//			ldapEnv.put(Context.SECURITY_PRINCIPAL, "demo@5Bit.com.uy");
			String email="administrator@5Bit.com.uy";
			
//			String prueba="CN=Paul Marshall,OU=Estudiantes,DC=5Bit,DC=com,DC=uy";
//			ldapEnv.put(Context.SECURITY_PRINCIPAL, email);
			ldapEnv.put(Context.SECURITY_PRINCIPAL, mailLogin);
//			aquí va el nombre del usuario por lo general antecedido de un dominio pj EMPRESA//nombre_usuario");

//			ldapEnv.put(Context.SECURITY_CREDENTIALS, "5Bitadmin");
			
			ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "ssl");
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");
			ldapContext = new InitialDirContext(ldapEnv);

			// Create the search controls
			SearchControls searchCtls = new SearchControls();

			// Specify the attributes to return
			String returnedAtts[] = { "sn", "givenName", "samAccountName" ,"mail","distinguishedName","logonName"};
			searchCtls.setReturningAttributes(returnedAtts);

			// Specify the search scope
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// specify the LDAP search filter
//			String searchFilter = "(&(objectClass=user)(samAccountName=" +  + "))"; 
		
//			String searchFilter = "(&(objectClass))";
			
			String searchFilter ="(&(objectClass=user)(sAMAccountName=" + mailLogin.split("@")[0] +"))"; 
			// Specify the Base for the search
			String searchBase = "dc=5Bit,dc=com,dc=uy";
			// initialize counter to total the results
			int totalResults = 0;

			// Search for objects using the filter
			NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
//			System.out.println(ldapContext.getNameInNamespace());
			// Loop through the search results
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();

				totalResults++;

				System.out.println(">>>" + sr.getName());
				Attributes attrs = sr.getAttributes();
				System.out.println( attrs.get("samAccountName"));
				System.out.println( attrs.get("sn"));
				System.out.println( attrs.get("givenName"));
				System.out.println( attrs.get("mail"));
				System.out.println( attrs.get("distinguishedName"));
				System.out.println( attrs.get("logonName"));

				
			}
			
			gestionPersonaBean.verificarUsuarioAD(mailLogin.split("@")[0]);
			mailLogin="";
			password="";
			System.out.println("Total results: " + totalResults);
			ldapContext.close();
			return "";
		}
		catch (AuthenticationException e) {
            
            
			String msg1 = "Usuario o Contrseña errónea";
			// mensaje autenticación incorrecta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "";
			
		}
		
		catch (Exception e) {
			System.out.println(" Search error: " + e);
			
			return "";
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
