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

import java.util.ArrayList;

import org.tagaprice.shared.entities.Category;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CategoryHandlerAsync {

	void get(Category type, AsyncCallback<Category> callback) 
	throws IllegalArgumentException;

	/**
	 * 
	 * @param id if ID == NULL return Type is root
		 * @param callback
	 * @throws IllegalArgumentException
	 */
	void getTypeList(Category type, AsyncCallback<ArrayList<Category>> callback)
		throws IllegalArgumentException;

}
