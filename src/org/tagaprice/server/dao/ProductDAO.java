/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: PropertyDAO.java
 * Date: 14.07.2010
*/
package org.tagaprice.server.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;

import com.google.gwt.core.ext.typeinfo.NotFoundException;

public class ProductDAO {
	private static ProductDAO instance;
	private static EntityDAO eDao;
	protected DBConnection db;
	

	public static ProductDAO getInstance() throws FileNotFoundException, IOException { 
		return getInstance(new DBConnection());
	}
	
	public static ProductDAO getInstance(DBConnection db){
		if(instance == null){
			instance = new ProductDAO(db);
			eDao = new EntityDAO(db);
		}
		return instance;
	}
	
	private ProductDAO(DBConnection db) {
		this.db=db;
	}
	
	
	public void get(ProductData p) throws SQLException, NotFoundException, org.tagaprice.shared.exception.NotFoundException {
		
		//Get Entitiy Data
		eDao.get(p);
		
		//Get Product Data
		//TODO ...
		
		//Get Property Data
		String sql = "SELECT ep.value, pr.name, pr.title, u.unit_id, u.fallback_unit, u.factor, e.current_revision " +
				"FROM entityproperty ep " +
				"INNER JOIN property pr " +
				"ON (pr.prop_id = ep.prop_id) " +
				"INNER JOIN unit u " +
				"ON (ep.unit_id = u.unit_id) " +
				"INNER JOIN entity e " +
				"ON (e.ent_id = u.unit_id) " +
				"WHERE (ep.ent_id = ?)";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, p.getId());
		ResultSet res = pstmt.executeQuery();
		
		SearchResult<PropertyData> sr = new SearchResult<PropertyData>();
		
		while(res.next()){
			sr.add(new PropertyData(
					res.getString("name"), 
					res.getString("title"), 
					res.getString("value"), 
					new Unit(
							res.getLong("unit_id"), 
							res.getInt("current_revision"), //TODO get Revision
							res.getLong("fallback_unit"), 
							res.getDouble("factor"))));
		}
		p.setProperties(sr);
		
	}
	
}
