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
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.data.ProductData;
import org.tagaprice.shared.data.Type;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.InvalidLoginException;
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
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	
	@Override
	public ProductData get(long id) throws IllegalArgumentException {
		ProductData pd = new ProductData();
		pd._setId(id);
		
		
		//Get Product Data
		try {
			pDao.get(pd);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (NotFoundException e) {
			throw new IllegalArgumentException(e);
		}

		return 	pd;
	}

	@Override
	public ProductData save(ProductData data) throws IllegalArgumentException, InvalidLoginException {
		getSid();
		TypeHandlerImpl th = new TypeHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(new Type(data.getTypeId())), data.getProperties())){			
			
			
			try {
				data._setCreatorId(loginDao.getId(getSid()));
				pDao.save(data);
			} catch (SQLException e){
				throw new IllegalArgumentException("SQLException: "+e);
			} catch (NotFoundException e) {
				throw new IllegalArgumentException("NotFoundException: "+e);		
			} catch (RevisionCheckException e) {
				throw new IllegalArgumentException("RevisionCheckException: "+e);
			} catch (InvalidLocaleException e) {
				throw new IllegalArgumentException("InvalidLocaleException: "+e);
			} 		
			
			
		}else{
			System.out.println("save InVALID");
		}
		
		return data;
	}

	private String getSid() throws InvalidLoginException{
		return loginDao.getSid(this.getThreadLocalRequest().getCookies());	
	}
}
