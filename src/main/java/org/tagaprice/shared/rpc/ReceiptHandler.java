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
 * Filename: ReceiptHandler.java
 * Date: 30.05.2010
*/
package org.tagaprice.shared.rpc;

import java.util.List;

import org.tagaprice.shared.entities.Receipt;
import org.tagaprice.shared.exception.ServerException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/receipt")
public interface ReceiptHandler extends RemoteService {
	
	/**
	 * 
	 * @param id If 0 new Draft will be created
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ServerException 
	 */
	Receipt get(Receipt data) throws ServerException;
	
	List<Receipt> getUserReceipts() throws ServerException;
	
	Receipt save(Receipt data) throws ServerException;
}
