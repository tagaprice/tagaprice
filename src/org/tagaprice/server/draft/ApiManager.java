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

import org.tagaprice.server.ApiVersion;
import org.tagaprice.server.DuplicateNameException;

/**
 * this is the current API version draft.
 * When a stable API version is released, it is copied to ApiVersionXXX
 * (and a new servlet-entry is added to web.xml)
 */
public class ApiManager extends ApiVersion {
	public ApiManager() throws DuplicateNameException {
		registerCall(new GetVersion());
	}

	private static final long serialVersionUID = 1L;
	
	
}
