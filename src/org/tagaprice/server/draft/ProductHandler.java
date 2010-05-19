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
 * Filename: GetVersion.java
 * Date: 19.05.2010
*/
package org.tagaprice.server.draft;

import org.tagaprice.server.ApiCall;
import org.tagaprice.server.Responder;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;

public class ProductHandler implements ApiCall {
	@Override
	public String getName() {
		return "product";
	}

	/// TODO figure out the best way to get the request parameters (e.g. product id, search string, ...
	@Override
	public void onCall(String function, Responder r) {
		if (function.equals("get")) doGet(r);
	}
	
	public void doGet(Responder r) {
		r.setEntity(new ProductData(23, 42, 12, "TestProduct" , null, 80, 80, new Price(120, 23, "€"), new Quantity(1, 23, "g"), false));
	}

}
