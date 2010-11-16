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
import java.sql.Types;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.data.Product;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class ProductDAO implements DAOClass<Product> {
	private EntityDAO entityDAO;
	private DBConnection db;
	
	public ProductDAO(DBConnection db) {
		this.db=db;
		entityDAO= new EntityDAO(db);
	}
	
	@Override
	public void get(Product p) throws SQLException, NotFoundException, NotFoundException {
		
		//Get Entity Data
		entityDAO.get(p);
		
		// TODO implement fetching of a specific product revision
		
		//Get Product Data
		String sql = "SELECT brand_id, type_id, imageurl " +
				"FROM productrevision " +
				"INNER JOIN ENTITY ON(ent_id = prod_id) " +
				"WHERE prod_id = ? AND rev = ?";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, p.getId());
		pstmt.setLong(2, p.getRev());
		ResultSet res = pstmt.executeQuery();
		
		if (!res.next()) throw new NotFoundException("Product not found");
		
		if (res.getString("brand_id") != null) p.setBrandId(res.getLong("brand_id"));
		else p.setBrandId(null);
		if (res.getString("type_id") != null) p.setTypeId(res.getLong("type_id"));
		else p.setTypeId(null);
		p.setImageSrc(res.getString("imageurl"));	
		
	}

	@Override
	public void save(Product prod) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		
		entityDAO.save(prod);
		if (prod.getRev() == 1) {
			// create a new Product
			pstmt = db.prepareStatement("INSERT INTO product (prod_id) VALUES (?)");
			pstmt.setLong(1, prod.getId());
			pstmt.executeUpdate();
		}
		else if (prod.getRev() < 1) throw new RevisionCheckException("invalid revision: "+prod.getRev());

		String sql = "INSERT INTO productRevision (prod_id, rev, brand_id, type_id, imageUrl) VALUES (?, ?, ?, ?, ?)";
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, prod.getId());
		pstmt.setInt(2, prod.getRev());

		if (prod.getBrandId() != null) pstmt.setLong(3, prod.getBrandId());
		else pstmt.setNull(3, Types.BIGINT);
		
		if (prod.getTypeId() != null) pstmt.setLong(4, prod.getTypeId());
		else pstmt.setNull(4, Types.BIGINT);

		pstmt.setString(5, prod.getImageSrc());
		
		pstmt.executeUpdate();
	}
}
