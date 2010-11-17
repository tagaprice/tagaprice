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

import java.util.List;
import org.tagaprice.shared.Category;
import org.tagaprice.shared.exception.ServerException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/category")
public interface CategoryHandler extends RemoteService {
	Category get(Category type) throws IllegalArgumentException, ServerException;
	List<Category> getTypeList(Category type) throws IllegalArgumentException, ServerException;
}
