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
 * Filename: PriceHandler.java
 * Date: 02.06.2010
*/
package org.tagaprice.shared.rpc;

import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.NotFoundException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/unit")
public interface UnitHandler extends RemoteService {
	/**
	 * request a Unit by ID
	 * @param id Unit ID
	 * @return the requested Unit object
	 * @throws NotFoundException if the requested Object wasn't found
	 */
	Unit get(long id) throws IllegalArgumentException, NotFoundException;

	/**
	 * request other units that stand in relation to your requested unit ID
	 * @param id Unit ID
	 * @return list of matching units
	 */
	SearchResult<Unit> getSimilar(long id) throws NotFoundException;
}
