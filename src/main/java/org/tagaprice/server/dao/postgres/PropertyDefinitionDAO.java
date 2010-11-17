package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.DAOClass;
import org.tagaprice.shared.entities.Entity;
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class PropertyDefinitionDAO implements DAOClass<PropertyTypeDefinition> {
	private DBConnection db;
	private EntityDAO entityDAO;
	private UnitDAO unitDAO;
	
	public PropertyDefinitionDAO(DBConnection db) {
		this.db = db;
		entityDAO = new EntityDAO(db);
		unitDAO = new UnitDAO(db);
	}
	
	public PropertyTypeDefinition get(long id) throws SQLException, NotFoundException {
		return get(id, 0);
	}
	
	public PropertyTypeDefinition get(long id, int rev) throws SQLException, NotFoundException {
		PropertyTypeDefinition def = new PropertyTypeDefinition(id, rev);
		get(def);
		return def;
	}
	
	public PropertyTypeDefinition get(String name, int localeId) throws SQLException, NotFoundException {
		PropertyTypeDefinition def = new PropertyTypeDefinition(name, localeId);
		get(def);
		return def;
	}
	
	@Override
	public void get(PropertyTypeDefinition prop) throws SQLException, NotFoundException {
		String sql = "SELECT prop_id, name, minValue, maxValue, type, uniq, unit_id FROM propertyRevision ";
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

		try {
			prop = entityDAO.getById(prop, prop.getId());
		} catch (DAOException e) {
			//TODO change
			throw new NotFoundException(e.getMessage(), e);
		}
		
		
		//GetUnit
		if(res.getString("unit_id")!=null){
			Unit unit;
			try {
				unit = unitDAO.getById(res.getLong("unit_id"));
				prop.setUnit(unit);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NotFoundException(e.getMessage(), e);
			}		
		}
			
	}
	
	@Override
	public void save(PropertyTypeDefinition prop) throws SQLException, NotFoundException, RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		String sql;

		try {
			entityDAO.save(prop);
		} catch (DAOException e) {
			//TODO change
			throw new NotFoundException(e.getMessage(), e);
		}
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
		
		
		//Save Unit
		//GetUnit
		if(prop.getUnit()!=null)
			try {
				unitDAO.save(prop.getUnit());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NotFoundException(e.getMessage(), e);
			}
		
	}
}
