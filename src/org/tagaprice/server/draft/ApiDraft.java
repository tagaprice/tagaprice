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
 * Filename: ApiVersionDraft.java
 * Date: 19.05.2010
*/
package org.tagaprice.server.draft;

import org.tagaprice.server.ApiManager;
import org.tagaprice.server.DuplicateNameException;

/**
 * this is the current API version draft.
 * When a stable API version is released, it is copied to ApiVersionXXX
 * (and a new servlet-entry is added to web.xml)
 */
public class ApiDraft extends ApiManager {
	private static final long serialVersionUID = 1L;

	public ApiDraft() throws DuplicateNameException {
		registerCall(new ProductHandler());
		registerCall(new ShopHandler());
		registerCall(new UnitHandler());
		
	}
}
