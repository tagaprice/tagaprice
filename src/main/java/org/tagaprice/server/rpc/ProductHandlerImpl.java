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
import org.tagaprice.server.dao.postgres.LoginDAO;
import org.tagaprice.server.dao.postgres.ProductDAO;
import org.tagaprice.shared.entities.Category;
import org.tagaprice.shared.entities.Product;
import org.tagaprice.shared.exception.InvalidLoginException;
import org.tagaprice.shared.exception.ServerException;
import org.tagaprice.shared.rpc.ProductHandler;
import org.tagaprice.shared.utility.PropertyValidator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProductHandlerImpl extends RemoteServiceServlet implements ProductHandler{
	ProductDAO productDao;
	LoginDAO loginDao; 

	public ProductHandlerImpl() {
		DBConnection db;
		try {
			db = new DBConnection();
			productDao = new ProductDAO(db);
			loginDao = new LoginDAO(db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	
	@Override
	public Product get(long id) throws IllegalArgumentException, ServerException {
		return productDao.getById(id);
	}

	@Override
	public Product save(Product data) throws IllegalArgumentException, InvalidLoginException, ServerException {
		getSid();
		CategoryHandlerImpl th = new CategoryHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(new Category(data.getTypeId())), data.getProperties())){			
			
			
			try {
				data.setCreatorId(loginDao.getId(getSid()));
				productDao.save(data);
			} catch (SQLException e){
				throw new IllegalArgumentException("SQLException: "+e);
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
