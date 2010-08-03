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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class ProductDAO implements DAOClass<ProductData> {
	private static ProductDAO instance;
	private EntityDAO eDao;
	private UnitDAO uDao;
	private DBConnection db;
	

	public static ProductDAO getInstance(DBConnection db){
		if(instance == null){
			instance = new ProductDAO(db);
		}
		return instance;
	}
	
	private ProductDAO(DBConnection db) {
		this.db=db;
	}
	
	@Override
	public void get(ProductData p) throws SQLException, NotFoundException, NotFoundException {
		
		//Get Entitiy Data
		eDao.get(p);
		
		//Get Product Data
		//TODO ...
		
		//Get Property Data
		
		String sql = "SELECT ep.value, pr.name, pr.title, ep.unit_id " +
				"FROM entityproperty ep " +
				"INNER JOIN property pr " +
				"ON (pr.prop_id = ep.prop_id) " +
				"WHERE (ep.ent_id = ?) ";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, p.getId());
		ResultSet res = pstmt.executeQuery();
		
		SearchResult<PropertyData> sr = new SearchResult<PropertyData>();
		
		while(res.next()){
			sr.add(new PropertyData(
					res.getString("name"), 
					res.getString("title"), 
					res.getString("value"), 
					uDao.get(res.getLong("unit_id"))));
		}
		p.setProperties(sr);
		
	}

	@Override
	public void save(ProductData entity) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		// TODO Auto-generated method stub
		throw new SQLException("Not yet implemented");
	}
}
