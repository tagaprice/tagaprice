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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Type;

public class TypeDAO {
	protected DBConnection db;
	private static TypeDAO instance;
	
	public static TypeDAO getInstance() throws FileNotFoundException, IOException{
		return getInstance(new DBConnection());
	}
	
	public static TypeDAO getInstance(DBConnection db){
		if(instance == null){
			instance = new TypeDAO(db);
		}
		
		return instance;
	}
	
	
	private TypeDAO(DBConnection db){
		this.db=db;
	}
	
	public void get(Type t){
		//TODO ...
	}
	
}
