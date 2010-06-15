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
 * Filename: GetVersion.java
 * Date: 19.05.2010
*/
package org.tagaprice.server.api.draft;

import org.tagaprice.server.ApiCall;
import org.tagaprice.server.ApiCallData;
import org.tagaprice.server.dao.UnitDAO;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.ServerResponse.StatusCode;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RequestException;

public class UnitHandler implements ApiCall {
	private UnitDAO dao;
	
	public UnitHandler() {
		dao = UnitDAO.getInstance();
	}
	
	@Override
	public String getName() {
		return "unit";
	}

	/// TODO figure out the best way to get the request parameters (e.g. product id, search string, ...
	@Override
	public void onCall(String function, ApiCallData d) throws RequestException {
		if (function.equals("get")) get(d);
		else if (function.equals("similar")) getSimilar(d);
		else d.setStatusCode(StatusCode.NotFound);
	}
	
	public void get(ApiCallData d) {
		try {
			long id = new Long(d.getRequest().getParameter("id"));
			Unit unit = dao.get(id);
			d.setResponse(unit);
		}
		catch (NotFoundException e) {
			d.setStatusCode(StatusCode.NotFound);
		}
	}
	
	public void getSimilar(ApiCallData d) {
		try {
			long id = new Long(d.getRequest().getParameter("id"));
			SearchResult<Unit> units = dao.getSimilar(id);
			d.setResponse(units);
		}
		catch (NotFoundException e) {
			d.setStatusCode(StatusCode.NotFound);
		}
	}

}