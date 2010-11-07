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
package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class TypeDAO implements DAOClass<Type> {
	protected DBConnection db;
	private EntityDAO entityDAO;
	
	public TypeDAO(DBConnection db){
		this.db=db;
		entityDAO= new EntityDAO(db);
	}
	
	public void get(Type type) throws SQLException, NotFoundException, NotFoundException{
		
		getRec(type,0);
		
	}
	
	private void getRec(Type type, int c) throws SQLException, NotFoundException, NotFoundException{
		
		
		
		entityDAO.get(type);
		
		type.setSuperType(null);
		String sql = "SELECT parent_id FROM typerevision tr " +
				"INNER JOIN entity en " +
				"ON en.ent_id=tr.type_id AND en.current_revision=tr.rev " +
				"WHERE type_id=? AND parent_id IS NOT NULL ";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, type.getId());
		ResultSet res = pstmt.executeQuery();
		
		
		
		
		if(res.next()){
			Type sType = new Type(res.getLong("parent_id"));

			c=c+1;
			getRec(sType, c);
			c=c-1;
			type.setSuperType(sType);
		}
		
		
		//TODO GET Properties
		if(c==0){
			PropertyGroup pg =new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
			pg.addGroupElement(new PropertyDefinition(2L, 1, "energy", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,0, 15, null,true)); 
			pg.addGroupElement(new PropertyDefinition(3L, 2, "protein", "Protein", 1, PropertyDefinition.Datatype.DOUBLE, -5, 20, null,true));
			pg.addGroupElement(new PropertyDefinition(4L, 3, "url", "URL", 1, PropertyDefinition.Datatype.STRING,-10, 25, null,false));
			type.addPropertyGroup(pg);
			PropertyGroup pg5 = new PropertyGroup("NUTRITIONFACTS", PropertyGroup.GroupType.LIST);
			pg5.addGroupElement(new PropertyDefinition(2L, 1, "ean", "BAR", 1, PropertyDefinition.Datatype.STRING, 1, 14, null,false)); 

			type.addPropertyGroup(pg5);
			
		}
		
		

	}
	
	
	public ArrayList<Type> getTypeList(Type type) throws SQLException, NotFoundException, NotFoundException{
		
		if(type==null) type = new Type(13);
		
		entityDAO.get(type);
		ArrayList<Type> types = new ArrayList<Type>();
		
		
		String sql = "SELECT type_id FROM typerevision tr " +
				"INNER JOIN entity en " +
				"ON en.ent_id=tr.type_id AND en.current_revision=tr.rev " +
				"WHERE parent_id=?";
		
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, type.getId());
		ResultSet res = pstmt.executeQuery();
		
		while(res.next()){
			Type tType = new Type(res.getLong("type_id"));
			entityDAO.get(tType);
			
			types.add(tType);
		}
				 
		
		return types;
	}

	@Override
	public void save(Type entity) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		// TODO Auto-generated method stub
		
	}
}
