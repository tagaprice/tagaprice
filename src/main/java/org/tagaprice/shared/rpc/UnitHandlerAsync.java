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
 * Filename: PriceHandlerAsync.java
 * Date: 02.06.2010
*/
package org.tagaprice.shared.rpc;

import org.tagaprice.shared.SerializableArrayList;
import org.tagaprice.shared.entities.Unit;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UnitHandlerAsync {

	void get(long id, AsyncCallback<Unit> callback)
		throws IllegalArgumentException;

	void getSimilar(long id, AsyncCallback<SerializableArrayList<Unit>> units);
}
