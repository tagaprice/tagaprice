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
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class ProductDAO implements DAOClass<ProductData> {
	private static ProductDAO instance;
	private EntityDAO entityDAO;
	private DBConnection db;
	

	public static ProductDAO getInstance(DBConnection db){
		if(instance == null){
			instance = new ProductDAO(db);
		}
		return instance;
	}
	
	private ProductDAO(DBConnection db) {
		this.db=db;
		entityDAO=EntityDAO.getInstance(db);
	}
	
	@Override
	public void get(ProductData p) throws SQLException, NotFoundException, NotFoundException {
		
		//Get Entitiy Data
		entityDAO.get(p);
		
		System.out.println("i:1");
		
		//Get Product Data
		String sql = "SELECT brand_id, type_id, imageurl " +
				"FROM productrevision " +
				"INNER JOIN ENTITY ON(ent_id = prod_id AND current_revision = rev) " +
				"WHERE prod_id = ?";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, p.getId());
		ResultSet res = pstmt.executeQuery();
		
		if (!res.next()) throw new NotFoundException("Product not found");
		p.setBrandId(res.getLong("brand_id"));
		p.setTypeId(res.getLong("type_id"));
		p.setImageSrc(res.getString("imageurl"));	
		
		System.out.println("i:2");
	}

	@Override
	public void save(ProductData entity) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		// TODO Auto-generated method stub
		throw new SQLException("Not yet implemented");
	}
}
