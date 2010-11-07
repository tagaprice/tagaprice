/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: TypeDraftServiceImpl.java
 * Date: 27.05.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.TypeDAO;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.rpc.TypeHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TypeHandlerImpl extends RemoteServiceServlet implements TypeHandler {
	TypeDAO typeDAO;
	
	
	public TypeHandlerImpl(){
		DBConnection db;
		
			try {
				db = new DBConnection();
				typeDAO = new TypeDAO(db);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		
	}
	
	
	@Override
	public Type get(Type type) throws IllegalArgumentException {
		
		//if(type.getSuperType()==null)type = new Type("root", 9, 1, null);
		
		typeDAO.get(type);
		
		return type;
	}

	
	
	
	@Override
	public ArrayList<Type> getTypeList(Type type)
			throws IllegalArgumentException {
	
		
		return typeDAO.getTypeList(type);
		

	}
	
	

}
