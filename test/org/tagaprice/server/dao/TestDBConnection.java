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
 * Filename: TestDBConnection.java
 * Date: 22.07.2010
*/
package org.tagaprice.server.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;

public class TestDBConnection extends DBConnection {
	public TestDBConnection() throws FileNotFoundException, IOException, SQLException {
		super();
		super.begin();
	}
	
	@Override
	public void commit() throws SQLException {
		if (transactionDepth > 0) transactionDepth--;
	}
	
	@Override
	public InputStream _loadResourceFile(String fileName) throws FileNotFoundException {
		File file = new File("war/WEB-INF/conf/jdbc.properties");
		return new FileInputStream(file);
	}
}
