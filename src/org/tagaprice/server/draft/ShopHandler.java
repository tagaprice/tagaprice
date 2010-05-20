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
import org.tagaprice.server.ApiCallData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.ShopData;

public class ShopHandler implements ApiCall {
	@Override
	public String getName() {
		return "shop";
	}

	/// TODO figure out the best way to get the request parameters (e.g. product id, search string, ...
	@Override
	public void onCall(String function, ApiCallData d) {
		if (function.equals("get")) doGet(d);
	}
	
	public void doGet(ApiCallData d) {
		ShopData shop = new ShopData(123, "ACME Store", null, 80, 25, "Park Avenue 23", "New York", "USA", 0.5, 0.3);
		PropertyList propList = new PropertyList();
		propList.add(new PropertyData("type", "Type", "drugstore", null));
		shop.setProperties(propList);
		d.setResponse(shop);
	}

}
