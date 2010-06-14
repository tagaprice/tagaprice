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
package org.tagaprice.server.api.draft;

import java.io.IOException;

import org.tagaprice.server.ApiCall;
import org.tagaprice.server.ApiCallData;
import org.tagaprice.server.serializer.JsonDeserializer;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.Unit;

public class ProductHandler implements ApiCall {
	@Override
	public String getName() {
		return "product";
	}

	/// TODO figure out the best way to get the request parameters (e.g. product id, search string, ...
	@Override
	public void onCall(String function, ApiCallData d) {
		if (function.equals("get")) get(d);
		else if (function.equals("save")) save(d);
	}
	
	public void get(ApiCallData d) {
		ProductData product = new ProductData(23, 15, 42, 12, "TestProduct" , null, 80, 80, new Price(120, 23, 8, "€"), new Quantity(1, 23, 2, "g"), false);
		SearchResult<PropertyData> propList = new SearchResult<PropertyData>();
		propList.add(new PropertyData("Weight", "123", new Unit(23, 3, "g")));
		product.setProperties(propList);
		d.setResponse(product);
	}
	
	public void save(ApiCallData d) {
		JsonDeserializer des = new JsonDeserializer();
		try {
			String json = "{\"status\"=\"ok\", \"type\"=\"price\", \"response\"={\"price\": 120, \"currency\": {\"id\": 23,\"name\": \"€\"}}}";
			ServerResponse response = des.getServerResponse(json);
			d.setResponse(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
