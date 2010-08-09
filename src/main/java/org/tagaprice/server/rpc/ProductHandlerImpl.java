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
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.server.dao.PropertyDAO;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.rpc.ProductHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProductHandlerImpl extends RemoteServiceServlet implements ProductHandler{
	ProductData test;
	ProductDAO pDao;
	PropertyDAO proDao;
	
	public ProductHandlerImpl() {
		try {
			DBConnection dbConn = new DBConnection();
			pDao = ProductDAO.getInstance(dbConn);
			proDao = PropertyDAO.getInstance(dbConn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated constructor stub
		//MockMock
		test = new ProductData(152L, 3, "Mousse au Chocolat", 2, 15L, 20L, "logo.png", 20, 80, new Price(139, 23, 1, "€", 1), new Quantity(125, 23, 2, "g", 1),true);

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
		
		
		//Get Properties
		try {
			proDao.get(pd);
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
		TypeHandlerImpl th = new TypeHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(data.getTypeId()), data.getProperties())){
			System.out.println("save VALID");
		}else{
			System.out.println("save InVALID");
		}
		
		
		if(data.getId()==0){
			System.out.println("new");
		}else{
			test=data;
		}
		return test;
	}

}
