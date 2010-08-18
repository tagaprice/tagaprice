package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.PropertyDefinition.Datatype;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class PropertyDefinitionDAO implements DAOClass<PropertyDefinition> {
	private static PropertyDefinitionDAO instance = null;
	private DBConnection db;
	private EntityDAO entityDAO;
	private UnitDAO unitDAO;
	
	private PropertyDefinitionDAO(DBConnection db) {
		this.db = db;
	}
	
	public static PropertyDefinitionDAO getInstance(DBConnection db) {
		if (instance == null) {
			instance = new PropertyDefinitionDAO(db);
			instance.entityDAO = EntityDAO.getInstance(db);
			instance.unitDAO = UnitDAO.getInstance(db);
		}
		return instance;
	}
	
	@Override
	public void get(PropertyDefinition prop) throws SQLException, NotFoundException {
		String sql = "SELECT name, minValue, maxValue, type, uniq FROM propertyRevision WHERE prop_id = ?";
		PreparedStatement pstmt;
		if (prop.getRev() > 0) {
			pstmt = db.prepareStatement(sql +  " AND rev = ?");
			pstmt.setLong(2, prop.getRev());
		}
		else {
			pstmt = db.prepareStatement(sql);
		}
		pstmt.setLong(1, prop.getId());
		ResultSet res = pstmt.executeQuery();
		if (!res.next()) throw new NotFoundException("PropertyDefinition not found (id: "+prop.getId()+")");
		
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
