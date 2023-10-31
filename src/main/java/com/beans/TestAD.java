package com.beans;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class TestAD {
	static DirContext ldapContext;

	public static void main(String[] args) throws NamingException {
		try {
			System.out.println("Début du test Active Directory");

			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			// ldapEnv.put(Context.PROVIDER_URL, "ldap://societe.fr:389");
			ldapEnv.put(Context.PROVIDER_URL, "ldap://5Bit.com.uy:389");
//			env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			// ldapEnv.put(Context.SECURITY_PRINCIPAL,
			// "cn=administrateur,cn=users,dc=societe,dc=fr");
//			env.put(Context.SECURITY_PRINCIPAL, "cn=S. User, ou=NewHires, o=JNDITutorial");

//			ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=Admin Admin,ou=Users,dc=5Bit,dc=com.uy");
//			ldapEnv.put(Context.SECURITY_PRINCIPAL, "demo@5Bit.com.uy");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, "mduarte@5Bit.com.uy");
//			aquí va el nombre del usuario por lo general antecedido de un dominio pj EMPRESA//nombre_usuario");

			ldapEnv.put(Context.SECURITY_CREDENTIALS, "5Bitadmin");
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "ssl");
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");
			ldapContext = new InitialDirContext(ldapEnv);

			// Create the search controls
			SearchControls searchCtls = new SearchControls();

			// Specify the attributes to return
			String returnedAtts[] = { "sn", "givenName", "samAccountName" };
			searchCtls.setReturningAttributes(returnedAtts);

			// Specify the search scope
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// specify the LDAP search filter
//			String searchFilter = "(&(objectClass=user)(samAccountName=" +  + "))"; 
		
			String searchFilter = "(&(objectClass=user))";
			
			
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
				System.out.println(">>>>>>" + attrs.get("samAccountName"));
			}

			System.out.println("Total results: " + totalResults);
			ldapContext.close();
		} catch (Exception e) {
			System.out.println(" Search error: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
