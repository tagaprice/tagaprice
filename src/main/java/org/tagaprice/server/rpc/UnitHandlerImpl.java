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
 * Filename: PriceHandlerImpl.java
 * Date: 02.06.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.UnitDAO;
import org.tagaprice.shared.SerializableArrayList;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.rpc.UnitHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UnitHandlerImpl  extends RemoteServiceServlet implements UnitHandler {
	private static final long serialVersionUID = 1L;
	private UnitDAO dao;

	public UnitHandlerImpl() throws FileNotFoundException, IOException {
		dao = new UnitDAO(new DBConnection());
	}
	
	@Override
	public Unit get(long id) throws NotFoundException {
		Unit rc = new Unit(id);
		try {
			dao.get(rc);
		}
		catch (SQLException e) {
			throw new NotFoundException("DB Query Error", e);
		}
		return rc;
	}

	@Override
	public SerializableArrayList<Unit> getSimilar(long id) throws NotFoundException {
		return dao.getSimilar(id);
	}

	
}
