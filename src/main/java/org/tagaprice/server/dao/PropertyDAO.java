package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class PropertyDAO implements DAOClass<Entity> {
	private static PropertyDAO instance = null;
	private DBConnection db;
	private UnitDAO unitDAO;

	private PropertyDAO(DBConnection db) {
		this.db = db;
		unitDAO = UnitDAO.getInstance(db);
	}
	
	public static PropertyDAO getInstance(DBConnection db) {
		if (instance == null) instance = new PropertyDAO(db);
		return instance;
	}
	/**
	 * Get the properties of an entity
	 */
	@Override
	public void get(Entity entity) throws SQLException, NotFoundException {
		// TODO Auto-generated method stub
		//Get Property Data

		String sql = "SELECT ep.value, pr.name, pr.title, ep.unit_id " +
				"FROM entityproperty ep " +
				"INNER JOIN property pr " +
				"USING (prop_id) " +
				"WHERE (ep.ent_id = ?) ";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, entity.getId());
		ResultSet res = pstmt.executeQuery();
		
		SearchResult<PropertyData> sr = new SearchResult<PropertyData>();
		
		while(res.next()){
			sr.add(new PropertyData(
					res.getString("name"), 
					res.getString("title"), 
					res.getString("value"), 
					unitDAO.get(res.getLong("unit_id"))));
		}
		entity.setProperties(sr);
	}

	/**
	 * 
	 */
	@Override
	public void save(Entity entity) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		// TODO Auto-generated method stub
		throw new SQLException("Not yet implemented");
	}

}
