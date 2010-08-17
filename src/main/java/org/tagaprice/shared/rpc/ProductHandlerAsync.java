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
 * Filename: ProductHandlerAsync.java
 * Date: 27.05.2010
*/
package org.tagaprice.shared.rpc;

import org.tagaprice.shared.ProductData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductHandlerAsync {

	void get(Long id, AsyncCallback<ProductData> callback)
		throws IllegalArgumentException;

	void save(ProductData data, AsyncCallback<ProductData> callback) 
		throws IllegalArgumentException;
	
	

}
