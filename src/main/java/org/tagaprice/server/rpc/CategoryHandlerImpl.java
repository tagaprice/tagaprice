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
 * Filename: PriceHandlerImpl.java
 * Date: 02.06.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.widgets.PriceMapWidget.PriceMapType;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.CategoryDAO;
import org.tagaprice.server.dao.postgres.ProductDAO;
import org.tagaprice.server.dao.postgres.ShopDAO;
import org.tagaprice.shared.entities.Category;
import org.tagaprice.shared.entities.PriceData;
import org.tagaprice.shared.exception.ServerException;
import org.tagaprice.shared.rpc.CategoryHandler;
import org.tagaprice.shared.rpc.PriceHandler;
import org.tagaprice.shared.utility.BoundingBox;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CategoryHandlerImpl extends RemoteServiceServlet implements CategoryHandler {

CategoryDAO typeDAO;
	
	
	public CategoryHandlerImpl(){
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
	public List<Category> getTypeList(Category type) throws IllegalArgumentException, ServerException {
		return typeDAO.getCategoryList(typeDAO.getRootCategoryId());
	}
	

}
