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
 * Filename: DAOClass.java
 * Date: 21.07.2010
*/
package org.tagaprice.server.dao;

import java.sql.SQLException;

import org.tagaprice.shared.ISerializable;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

/**
 * Specifies the methods all DAO-Classes have to implement
 * @author Manuel Reithuber
 */
public interface DAOClass<T extends ISerializable> {
	
	public void get(T entity) throws SQLException, NotFoundException;
	public void save(T entity) throws SQLException, NotFoundException, RevisionCheckException, InvalidLocaleException;
}
