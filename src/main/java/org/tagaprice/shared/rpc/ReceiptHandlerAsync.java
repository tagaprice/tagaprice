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
 * Filename: ReceiptHandlerAsync.java
 * Date: 30.05.2010
*/
package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.shared.data.Receipt;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReceiptHandlerAsync {

	void get(Receipt data, AsyncCallback<Receipt> callback) 
		throws IllegalArgumentException, InvalidLoginException;

	void getUserReceipts(AsyncCallback<ArrayList<Receipt>> callback)
		throws IllegalArgumentException, InvalidLoginException;
	
	void save(Receipt data, AsyncCallback<Receipt> callback) 
		throws IllegalArgumentException, InvalidLoginException;
	
}
