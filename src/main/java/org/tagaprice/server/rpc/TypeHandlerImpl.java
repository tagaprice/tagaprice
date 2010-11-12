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
 * Filename: TypeDraftServiceImpl.java
 * Date: 27.05.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.CategoryDAO;
import org.tagaprice.shared.Category;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.ServerException;
import org.tagaprice.shared.rpc.TypeHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TypeHandlerImpl extends RemoteServiceServlet implements TypeHandler {
	CategoryDAO typeDAO;
	
	
	public TypeHandlerImpl(){
		DBConnection db;
		
			try {
				db = new DBConnection();
				typeDAO = new CategoryDAO(db);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		
	}
	
	
	@Override
	public Category get(Category type) throws IllegalArgumentException, ServerException {
		return typeDAO.getById(typeDAO.getRootCategoryId());
	}

	
	
	
	@Override
	public ArrayList<Category> getTypeList(Category type) throws IllegalArgumentException, ServerException {
		return typeDAO.getCategoryList(typeDAO.getRootCategoryId());
	}
	
	

}
