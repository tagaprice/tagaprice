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
 * Filename: JsonDeserializer.java
 * Date: 20.05.2010
*/
package org.tagaprice.server;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.ServerResponse.StatusCode;

public class JsonDeserializer extends Deserializer {
	@Override
	public Currency getCurrency(JSONObject json) throws IOException {
		try {
			// {"id": 23,"name": "€"}
			long id = json.getLong("id");
			String name = json.getString("name");
			return new Currency(id, name);
		} catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
	}

	@Override
	public Price getPrice(JSONObject json) throws IOException {
		Price rc = null;
		// {"price": 120, "currency": Currency }
		try {
			int price = json.getInt("price");
			Currency currency = getCurrency(json.getJSONObject("currency"));
			rc = new Price(price, currency);
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public ProductData getProduct(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyData getProperty(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyList getPropertyList(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getQuantity(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReceiptData getReceipt(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerResponse getServerResponse(JSONObject json) throws IOException {
		ServerResponse rc = null;
		try {
			String status = json.getString("status");
			Serializable response = null;
			if (!json.isNull("response")) {
				String type = json.getString("type");
				response = getAny(json.getJSONObject("response"), type);
			}
			rc = new ServerResponse(StatusCode.getByName(status), response);
		} catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		
		return rc;
	}

	@Override
	public ShopData getShop(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit getUnit(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
