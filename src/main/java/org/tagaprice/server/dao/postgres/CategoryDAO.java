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
 * Filename: TypeDAO.java
 * Date: 15.07.2010
*/
package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.ICategoryDAO;
import org.tagaprice.shared.entities.Category;
import org.tagaprice.shared.entities.PropertyGroup;
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.exception.DAOException;

public class CategoryDAO implements ICategoryDAO {
	protected DBConnection _db;
	private EntityDAO _entityDAO;
	private PropertyDefinitionDAO _propDAO;
	private static Logger _log = Logger.getLogger(CategoryDAO.class);
	
	public CategoryDAO(DBConnection db){
		this._db=db;
		_entityDAO= new EntityDAO(db);
		_propDAO = new PropertyDefinitionDAO(db);
	}
	
	@Override
	public Category getById(long id) throws DAOException {
		_log.debug("id:"+id);
		Category category;
		try {
			category = getAndSetSuperCategoryRecursive(id);
			if(category == null) return null;

			//get parent
			//TODO GET Properties
			/*
		PropertyGroup pg =new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
		pg.addGroupElement(new PropertyDefinition(2L, 1, "energy", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,0, 15, null,true)); 
		pg.addGroupElement(new PropertyDefinition(3L, 2, "protein", "Protein", 1, PropertyDefinition.Datatype.DOUBLE, -5, 20, null,true));
		pg.addGroupElement(new PropertyDefinition(4L, 3, "url", "URL", 1, PropertyDefinition.Datatype.STRING,-10, 25, null,false));
		type.addPropertyGroup(pg);
			 */

			//TODO Create PropertyGroups in db and add a group to a Type
			PropertyGroup pg5 = new PropertyGroup("NoCatList", PropertyGroup.PropertyGroupType.LIST);

			String sql ="SELECT prop_id FROM propertyrevision pr " +
			"INNER JOIN entity en " +
			"ON en.ent_id=pr.prop_id " +
			"WHERE en.current_revision=pr.rev";

			PreparedStatement pstmt = _db.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();

			while(res.next()){
				PropertyTypeDefinition tempProp;
				tempProp = _propDAO.getById(res.getLong("prop_id"));
//				_log.debug(tempProp.getUnit());
				//tempProp.setUnique()
				pg5.addGroupElement(tempProp);
			}

			category.addPropertyGroup(pg5);
			return category;
		} catch (SQLException e) {
			String msg = "Failed to get category. SQLException: "+e.getMessage()+".";
			_log.error(msg + " Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	@Override
	public List<Category> getCategoryList(long id) throws DAOException {
		_log.debug("id:"+id);
		Category category = _entityDAO.getById(new Category(), id);
		if(category == null) return null;
		

		String sql = "SELECT type_id FROM typerevision tr " +
		"INNER JOIN entity en " +
		"ON en.ent_id=tr.type_id AND en.current_revision=tr.rev " +
		"WHERE parent_id=?";
		try {
			PreparedStatement pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();

			List<Category> categories = new ArrayList<Category>();
			while(res.next()){
				Category curCategory = _entityDAO.getById(new Category(), res.getLong("type_id"));
				if(curCategory != null)
					categories.add(curCategory);
			}

			return categories;
		} catch (SQLException e) {
			String msg = "Failed to get category list. SQLException: "+e.getMessage()+".";
			_log.error(msg + " Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	@Override
	public long getRootCategoryId() throws DAOException {
		String sql = "SELECT type_id FROM typerevision " +
				"INNER JOIN entity " +
				"ON entity.ent_id=typerevision.type_id AND entity.current_revision=typerevision.rev " +
				"WHERE parent_id IS NULL ";
		try {
		PreparedStatement pstmt;
			pstmt = _db.prepareStatement(sql);
		ResultSet res = pstmt.executeQuery();
		res.next();
		return res.getLong("type_id");
		} catch (SQLException e) {
			String msg = "Failed to get root category id. SQLException: "+e.getMessage()+".";
			_log.error(msg + " Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	private Category getAndSetSuperCategoryRecursive(long id) throws DAOException, SQLException {
		_log.debug("id:"+id);
		Category category = _entityDAO.getById(new Category(), id);
		if(category == null) return null;
		//get parent
		String sql = "SELECT parent_id FROM typerevision tr " +
				"INNER JOIN entity en " +
				"ON en.ent_id=tr.type_id AND en.current_revision=tr.rev " +
				"WHERE type_id=? AND parent_id IS NOT NULL ";
		PreparedStatement pstmt = _db.prepareStatement(sql);
		pstmt.setLong(1, category.getId());
		ResultSet res = pstmt.executeQuery();
		
		if(res.next()){ //category has super category
			category.setSuperCategory(getAndSetSuperCategoryRecursive(res.getLong("parent_id")));
		} else {
			category.setSuperCategory(null);
		}
		return category;
	}
}
