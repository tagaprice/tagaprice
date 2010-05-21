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
		ProductData rc = null;
		
		try {
			long id = json.getLong("id");
			long brandId = json.getLong("brandId");
			long typeId = json.getLong("typeId");
			String name = json.getString("name");
			String imageSrc = json.getString("imgSrc");
			int progress = json.getInt("progress");
			int rating = json.getInt("rating");
			Price price = getPrice(json.getJSONObject("price"));
			Quantity quantity = getQuantity(json.getJSONObject("quantity"));
			boolean hasReceipt = json.getBoolean("private"); //TODO shouldn't this be done in a different way???
			
			rc = new ProductData(id, brandId, typeId, name, imageSrc, progress, rating, price, quantity, hasReceipt);
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public PropertyData getProperty(JSONObject json) throws IOException {
		PropertyData rc = null;
		
		try {
			String name = json.getString("name");
			String title = json.getString("title");
			String value = json.getString("value");
			Unit unit = getUnit(json.getJSONObject("unit"));
			rc = new PropertyData(name, title, value, unit);
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public PropertyList getPropertyList(JSONObject json) throws IOException {
		PropertyList rc = null;
		
		try {
			/// TODO implement me
			if (rc == null) throw new JSONException("Error: Not yet implemented!");
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public Quantity getQuantity(JSONObject json) throws IOException {
		Quantity rc = null;
		
		try {
            // {"quantity": 1, "unit": Unit}
			int quantity = json.getInt("quantity");
			Unit unit = getUnit(json.getJSONObject("unit"));
			rc = new Quantity(quantity, unit);
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public ReceiptData getReceipt(JSONObject json) throws IOException {
		ReceiptData rc = null;
		
		try {
			/// TODO implement me
			if (rc == null) throw new JSONException("Error: Not yet implemented!");
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
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
		ShopData rc = null;
		
		try {
			/// TODO implement me
			if (rc == null) throw new JSONException("Error: Not yet implemented!");
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public Unit getUnit(JSONObject json) throws IOException {
		Unit rc = null;
		
		try {
			// {"id": 23, "name": "g"}
			long id = json.getLong("id");
			String name = json.getString("name");
			rc = new Unit(id, name);
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

}
