package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyDefinition.Datatype;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class PropertyDefinitionDAO implements DAOClass<PropertyDefinition> {
	private DBConnection db;
	private EntityDAO entityDAO;
	
	public PropertyDefinitionDAO(DBConnection db) {
		this.db = db;
		entityDAO = new EntityDAO(db);
	}
	
	public PropertyDefinition get(long id) throws SQLException, NotFoundException {
		return get(id, 0);
	}
	
	public PropertyDefinition get(long id, int rev) throws SQLException, NotFoundException {
		PropertyDefinition def = new PropertyDefinition(id, rev);
		get(def);
		return def;
	}
	
	public PropertyDefinition get(String name, int localeId) throws SQLException, NotFoundException {
		PropertyDefinition def = new PropertyDefinition(name, localeId);
		get(def);
		return def;
	}
	
	@Override
	public void get(PropertyDefinition prop) throws SQLException, NotFoundException {
		String sql = "SELECT prop_id, name, minValue, maxValue, type, uniq FROM propertyRevision ";
		PreparedStatement pstmt;
		int pos = 1;
		if (prop.hasId()) {
			// get by ID
			sql += "WHERE prop_id = ?";
		}
		else {
			sql += "INNER JOIN entity ON (prop_id = ent_id) WHERE name = ? AND locale_id = ?";
		}
		if (prop.getRev() > 0) sql += "AND rev = ?";
		
		pstmt = db.prepareStatement(sql);
		
		if (prop.hasId()) {
			pstmt.setLong(pos++, prop.getId());
		}
		else {
			pstmt.setString(pos++, prop.getName());
			pstmt.setLong(pos++, prop.getLocaleId());
		}
		if (prop.getRev() > 0) pstmt.setLong(pos++, prop.getRev());

		ResultSet res = pstmt.executeQuery();
		if (!res.next()) throw new NotFoundException("PropertyDefinition not found (id: "+prop.getId()+")");
		
		prop._setId(res.getLong("prop_id"));
		prop.setName(res.getString("name"));
		if (res.getString("minvalue") != null) prop.setMinValue(res.getInt("minvalue"));
		else prop.setMinValue(null);
		if (res.getString("maxvalue") != null) prop.setMaxValue(res.getInt("maxvalue"));
		else prop.setMaxValue(null);
		prop.setType(Datatype.valueOf(res.getString("type").toUpperCase()));
		prop.setUnique(res.getBoolean("uniq"));

		entityDAO.get(prop);
	}
	
	@Override
	public void save(PropertyDefinition prop) throws SQLException, NotFoundException, RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		String sql;

		entityDAO.save(prop);
		if (prop.getRev() == 1) {
			// create a new PropertyDefinition
			pstmt = db.prepareStatement("INSERT INTO property (prop_id) VALUES (?)");
			pstmt.setLong(1, prop.getId());
			pstmt.executeUpdate();
		}
		else if (prop.getRev() < 1) throw new RevisionCheckException("invalid revision: "+prop.getRev());
		
		sql = "INSERT INTO propertyRevision (prop_id, rev, name, minValue, maxValue, type, uniq) VALUES (?, ?, ?, ?, ?, ?::propertyType, ?)";
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, prop.getId());
		pstmt.setInt(2, prop.getRev());
		pstmt.setString(3, prop.getName());
		
		if (prop.getMinValue() != null) pstmt.setInt(4, prop.getMinValue());
		else pstmt.setNull(4, Types.INTEGER);
		
		if (prop.getMaxValue() != null) pstmt.setInt(5, prop.getMaxValue());
		else pstmt.setNull(5, Types.INTEGER);
		
		pstmt.setString(6, prop.getType().name().toLowerCase());
		pstmt.setBoolean(7, prop.isUnique());
		
		pstmt.executeUpdate();
	}
}
