/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: UnitDAO.java
 * Date: 11.06.2010
*/
package org.tagaprice.server.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.NotFoundException;

public class UnitDAO {
	private static UnitDAO instance;
	private DBConnection db;
	
	private UnitDAO(DBConnection db) {
		this.db = db;
	}
	
	public static UnitDAO getInstance() {
		if (instance == null)
			try {
				instance = new UnitDAO(new DBConnection());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return instance;
	}
	
	public Unit get(long id) throws NotFoundException {
		Unit rc = null;
		String sql = "SELECT unit_id, r.title, fallback_unit, factor" +
				" FROM unit u INNER JOIN entity e ON (unit_id = ent_id)" +
				" INNER JOIN entityRevision r ON (r.ent_id = e.ent_id AND r.rev = e.current_revision)" +
				" WHERE unit_id = ?";
		
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();
			
			if (!res.next()) throw new NotFoundException("Unit "+id+" not found");
			
			rc = new Unit(res.getLong("unit_id"), res.getString("title"), res.getLong("fallback_unit"), res.getDouble("factor"));
		} catch (SQLException e) {
			throw new NotFoundException("Query Error", e);
		}
		
		return rc;
	}
}
