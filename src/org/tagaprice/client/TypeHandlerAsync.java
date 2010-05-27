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
 * Filename: TypeDraftAsync.java
 * Date: 27.05.2010
*/
package org.tagaprice.client;

import org.tagaprice.shared.Type;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TypeHandlerAsync {

	void get(long id, AsyncCallback<Type> callback) 
		throws IllegalArgumentException;
	
}
