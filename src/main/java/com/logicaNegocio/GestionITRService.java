package com.logicaNegocio;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.persistencia.dao.ItrDAO;
import com.persistencia.entities.ITR;
import com.persistencia.exception.ServicesException;


@Stateless
@LocalBean
public class GestionITRService {
	@EJB
	ItrDAO itrDAO;
	
	
	public void crearITR(ITR itr) {
		
		try {
			
			// se utiliza actualizar por que al no estar creado se crea el objeto en la base
			itrDAO.actualizarITR(itr);
		} catch (ServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
