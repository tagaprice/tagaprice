package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IPropertyDefinitionDAO;
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class PropertyDefinitionDAO implements IPropertyDefinitionDAO {
	private DBConnection _db;
	private EntityDAO _entityDAO;
	private UnitDAO _unitDAO;
	private static Logger _log = Logger.getLogger(PropertyDefinitionDAO.class);
	
	public PropertyDefinitionDAO(DBConnection db) {
		this._db = db;
		_entityDAO = new EntityDAO(db);
		_unitDAO = new UnitDAO(db);
	}
	
	@Override
	public PropertyTypeDefinition getById(long id) throws DAOException {
		String sql = "SELECT prop_id, name, minValue, maxValue, type, uniq, unit_id FROM propertyRevision ";
		sql += "WHERE prop_id = ?";
		
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) 
				return null;
			
			return createPropertyDefinition(res);
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	@Override
	public PropertyTypeDefinition getByIdAndRef(long id, int rev) throws DAOException {
		String sql = "SELECT prop_id, name, minValue, maxValue, type, uniq, unit_id FROM propertyRevision ";
		sql += "WHERE prop_id = ?";
		sql += "AND rev = ?";
		
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.setLong(2, rev);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) 
				return null;
			
			return createPropertyDefinition(res);
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	@Override
	public PropertyTypeDefinition getByNameAndLocalId(String name, int localeId) throws DAOException  {
		String sql = "SELECT prop_id, name, minValue, maxValue, type, uniq, unit_id FROM propertyRevision ";
		sql += "INNER JOIN entity ON (prop_id = ent_id) WHERE name = ? AND locale_id = ?";
		
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setLong(2, localeId);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) 
				return null;
			
			return createPropertyDefinition(res);
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	/**
	 * Creates a PropertyDefinition for given ResultSet and returns it.
	 */
	private PropertyTypeDefinition createPropertyDefinition(ResultSet res) throws SQLException, DAOException {
		PropertyTypeDefinition propertyDefinition = new PropertyTypeDefinition();

		propertyDefinition._setId(res.getLong("prop_id"));
		propertyDefinition.setName(res.getString("name"));
		if (res.getString("minvalue") != null) propertyDefinition.setMinValue(res.getInt("minvalue"));
		else propertyDefinition.setMinValue(null);
		if (res.getString("maxvalue") != null) propertyDefinition.setMaxValue(res.getInt("maxvalue"));
		else propertyDefinition.setMaxValue(null);
		propertyDefinition.setType(Datatype.valueOf(res.getString("type").toUpperCase()));
		propertyDefinition.setUnique(res.getBoolean("uniq"));

		propertyDefinition = _entityDAO.getById(propertyDefinition, propertyDefinition.getId());

		//get unit
		if(res.getString("unit_id")!=null){
			Unit unit = _unitDAO.getById(res.getLong("unit_id"));
			propertyDefinition.setUnit(unit);
		}
		return propertyDefinition;
	}
	
	/**
	 * Retrieves PropertyDefinition for given propertyDefinition.
	 * @param propertyDefinition
	 * @return
	 * @throws DAOException
	 */
	@Deprecated
	@SuppressWarnings("unused")
	private boolean get(PropertyTypeDefinition propertyDefinition) throws DAOException {
		String sql = "SELECT prop_id, name, minValue, maxValue, type, uniq, unit_id FROM propertyRevision ";
		PreparedStatement pstmt;
		int pos = 1;
		if (propertyDefinition.hasId()) {
			// get by ID
			sql += "WHERE prop_id = ?";
		}
		else {
			sql += "INNER JOIN entity ON (prop_id = ent_id) WHERE name = ? AND locale_id = ?";
		}
		if (propertyDefinition.getRev() > 0) sql += "AND rev = ?";

		try {
			pstmt = _db.prepareStatement(sql);

			if (propertyDefinition.hasId()) {
				pstmt.setLong(pos++, propertyDefinition.getId());
			}
			else {
				pstmt.setString(pos++, propertyDefinition.getName());
				pstmt.setLong(pos++, propertyDefinition.getLocaleId());
			}
			if (propertyDefinition.getRev() > 0) pstmt.setLong(pos++, propertyDefinition.getRev());

			ResultSet res = pstmt.executeQuery();
			if (!res.next()) 
				return false;
			
			propertyDefinition = new PropertyTypeDefinition();

			propertyDefinition._setId(res.getLong("prop_id"));
			propertyDefinition.setName(res.getString("name"));
			if (res.getString("minvalue") != null) propertyDefinition.setMinValue(res.getInt("minvalue"));
			else propertyDefinition.setMinValue(null);
			if (res.getString("maxvalue") != null) propertyDefinition.setMaxValue(res.getInt("maxvalue"));
			else propertyDefinition.setMaxValue(null);
			propertyDefinition.setType(Datatype.valueOf(res.getString("type").toUpperCase()));
			propertyDefinition.setUnique(res.getBoolean("uniq"));

			propertyDefinition = _entityDAO.getById(propertyDefinition, propertyDefinition.getId());


			//GetUnit
			if(res.getString("unit_id")!=null){
				Unit unit = _unitDAO.getById(res.getLong("unit_id"));
				propertyDefinition.setUnit(unit);
			}
			return true;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
			
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void save(PropertyTypeDefinition prop) throws SQLException, NotFoundException, RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		String sql;

		try {
			_entityDAO.save(prop);
		} catch (DAOException e) {
			//TODO change
			throw new NotFoundException(e.getMessage(), e);
		}
		if (prop.getRev() == 1) {
			// create a new PropertyDefinition
			pstmt = _db.prepareStatement("INSERT INTO property (prop_id) VALUES (?)");
			pstmt.setLong(1, prop.getId());
			pstmt.executeUpdate();
		}
		else if (prop.getRev() < 1) throw new RevisionCheckException("invalid revision: "+prop.getRev());
		
		sql = "INSERT INTO propertyRevision (prop_id, rev, name, minValue, maxValue, type, uniq) VALUES (?, ?, ?, ?, ?, ?::propertyType, ?)";
		pstmt = _db.prepareStatement(sql);
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
				_unitDAO.save(prop.getUnit());
			} catch (DAOException e) {
				e.printStackTrace();
				throw new NotFoundException(e.getMessage(), e);
			}
		
	}
}
