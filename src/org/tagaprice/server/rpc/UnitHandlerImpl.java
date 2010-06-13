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

import org.tagaprice.client.UnitHandler;
import org.tagaprice.server.dao.UnitDAO;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.NotFoundException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UnitHandlerImpl  extends RemoteServiceServlet implements UnitHandler {
	private static final long serialVersionUID = 1L;
	private UnitDAO dao;

	public UnitHandlerImpl() {
		dao = UnitDAO.getInstance();
	}
	
	@Override
	public Unit get(long id) throws NotFoundException {
		return dao.get(id);
	}

	@Override
	public SearchResult<Unit> getSimilar(long id) throws NotFoundException {
		return dao.getSimilar(id);
	}

	
}
