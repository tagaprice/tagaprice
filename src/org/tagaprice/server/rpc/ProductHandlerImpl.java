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
import org.tagaprice.server.dao.EntityDAO;
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.rpc.ProductHandler;

import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProductHandlerImpl extends RemoteServiceServlet implements ProductHandler{
	ProductData test;
	ProductDAO pDao;
	
	public ProductHandlerImpl() {
		try {
			pDao = ProductDAO.getInstance(new DBConnection());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated constructor stub
		//MockMock
		test = new ProductData(152, 3, "Mousse au Chocolat", 2, 15, 20, "logo.png", 20, 80, new Price(139, 23, 1, "€", 1), new Quantity(125, 23, 2, "g", 1),true);

		
		/*
		SearchResult<PropertyData> properties = new SearchResult<PropertyData>();
		
		//properties.add(new PropertyData("energy", "Brennwert", "2109", new Unit(1, 2, "kj", 3)));
		properties.add(new PropertyData("protein","Eiweiss", "5,3", new Unit(2, 2, "g", 3)));		
		properties.add(new PropertyData("carbohydrate", "Kohlenhydrate", "27,5", new Unit(2, 2, "g", 3)));
		properties.add(new PropertyData("fat", "Fett", "41,3", new Unit(2, 2, "g", 3)));
		properties.add(new PropertyData("sodium", "Ballaststoffe", "1,9", new Unit(2, 2, "g", 3)));
		properties.add(new PropertyData("natrum", "Natrium", "0,05", new Unit(2, 2, "g", 3)));
		properties.add(new PropertyData("url", "URL", "tagaprice.com", new Unit(5, 2, "fl", 3)));
		properties.add(new PropertyData("ean", "EAN", "14526486", new Unit(5, 2, "g", 3)));
		properties.add(new PropertyData("ean", "EAN", "24422", new Unit(5, 2, "g", 3)));
		properties.add(new PropertyData("ean", "EAN", "24422", new Unit(5, 2, "g", 3)));
		properties.add(new PropertyData("ean", "EAN", "24422", new Unit(5, 2, "g", 3)));
		properties.add(new PropertyData("url", "URL", "tagaprice.com", new Unit(5, 2, "fl", 3)));
		
		test.setProperties(properties);
		*/
	}
	
	@Override
	public ProductData get(Long id) throws IllegalArgumentException {
		ProductData pd = new ProductData();
		pd._setId(id);
		//pd._setId(63);
		
		try {
			pDao.get(pd);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.tagaprice.shared.exception.NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return pd;
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
