/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: ProductHandlerImpl.java
 * Date: 27.05.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LoginDAO;
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;
import org.tagaprice.shared.rpc.ProductHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProductHandlerImpl extends RemoteServiceServlet implements ProductHandler{
	ProductDAO pDao;
	LoginDAO loginDao; 

	public ProductHandlerImpl() {
		DBConnection db;
		try {
			db = new DBConnection();
			pDao = new ProductDAO(db);
			loginDao = new LoginDAO(db);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public ProductData get(Long id) throws IllegalArgumentException {
		ProductData pd = new ProductData();
		pd._setId(id);
		
		
		//Get Product Data
		try {
			pDao.get(pd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 	pd;
	}

	@Override
	public ProductData save(ProductData data) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		TypeHandlerImpl th = new TypeHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(data.getTypeId()), data.getProperties())){			
			
			try {
				data._setCreatorId(loginDao.getId(getSid()));
				pDao.save(data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RevisionCheckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidLocaleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			
		}else{
			System.out.println("save InVALID");
		}
		
		return data;
	}

	private String getSid(){
		return loginDao.getSid(this.getThreadLocalRequest().getCookies());
	}
}
