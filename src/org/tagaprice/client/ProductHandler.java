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
 * Filename: ProductHandler.java
 * Date: 27.05.2010
*/
package org.tagaprice.client;

import org.tagaprice.shared.ProductData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("draft/product")
public interface ProductHandler extends RemoteService {
	ProductData get(Long id) throws IllegalArgumentException;
}