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
package org.tagaprice.server.serializer;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.ServerResponse.StatusCode;

public class JsonDeserializer extends Deserializer {
	@Override
	public Currency getCurrency(String data) throws IOException {
		Currency rc = null;

		try {
			if (data != null) {
				JSONObject json = new JSONObject(data);
	
				// {"id": 23,"name": "€"}
				long id = json.getLong("id");
				int rev = json.getInt("rev");
				String name = json_getString(json, "name");
				rc = new Currency(id, rev, name);
			}
		} catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public Price getPrice(String data) throws IOException {
		Price rc = null;
		try {
			if (data != null) {
				JSONObject json = new JSONObject(data);
	
				// {"price": 120, "currency": Currency }
				int price = json.getInt("price");
				Currency currency = getCurrency(json_getString(json, "currency"));
				rc = new Price(price, currency);
			}
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public ProductData getProduct(String data) throws IOException {
		ProductData rc = null;
		
		try {
			JSONObject json = new JSONObject(data);
			
			long id = json.getLong("id");
			int rev = json.getInt("rev");
			long brandId = -1;
			if (json.has("brandId")) {
				brandId = json.getLong("brandId");
			}
			long typeId = json.getLong("typeId");
			String name = json_getString(json, "name");
			String imageSrc = json_getString(json, "imgSrc");
			int progress = json.getInt("progress");
			int rating = json.getInt("rating");
			Price price = getPrice(json_getString(json, "price"));
			Quantity quantity = getQuantity(json_getString(json, "quantity"));

			boolean hasReceipt = true;
			if (json.has("hasReceipt")) {
				//TODO shouldn't this be done in a different way???
				hasReceipt = json.getBoolean("hasReceipt");
			}
			
			rc = new ProductData(id, rev, brandId, typeId, name, imageSrc, progress, rating, price, quantity, hasReceipt);
		}
		catch (JSONException e) {
			e.printStackTrace(System.err);
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public PropertyData getProperty(String data) throws IOException {
		PropertyData rc = null;
		
		try {
			if (data != null) {
				JSONObject json = new JSONObject(data);
				
				String name = json_getString(json, "name");
				String value = json_getString(json, "value");
				Unit unit = getUnit(json_getString(json, "unit"));
				rc = new PropertyData(name, value, unit);
			}
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public Quantity getQuantity(String data) throws IOException {
		Quantity rc = null;
		
		try {
			if (data != null) {
				JSONObject json = new JSONObject(data);
				// {"quantity": 1, "unit": Unit}
				int quantity = json.getInt("quantity");
				Unit unit = getUnit(json_getString(json, "unit"));
				rc = new Quantity(quantity, unit);
			}
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public ReceiptData getReceipt(String data) throws IOException {
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
	public SearchResult<Serializable> getSearchResult(String data) throws IOException {
		SearchResult<Serializable> rc = null;
		
		try {
			JSONArray json = new JSONArray(data);
			
			rc = new SearchResult<Serializable>();
			for (int i = 0; i < json.length(); i++) {
				rc.add(getAny(json_getString(json, i), json_getString(json.getJSONObject(i), "class")));
			}

		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}

	@Override
	public ServerResponse getServerResponse(String data) throws IOException {
		ServerResponse rc = null;
		try {
			JSONObject json = new JSONObject(data);
			
			String status = json_getString(json, "status");
			Serializable response = null;
			if (!json.isNull("response")) {
				String type = json_getString(json, "type");
				response = getAny(json_getString(json, "response"), type);
			}
			rc = new ServerResponse(StatusCode.getByName(status), response);
		} catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		
		return rc;
	}

	@Override
	public ShopData getShop(String data) throws IOException {
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
	public Unit getUnit(String data) throws IOException {
		Unit rc = null;
		
		try {
			if (data != null) {
				JSONObject json = new JSONObject(data);
				// {"id": 23, "name": "g"}
				long id = json.getLong("id");
				int rev = json.getInt("rev");
				String name = json_getString(json, "name");
				if (json.has("siUnit") || json.has("factor")) {
					long fallbackId = json.getLong("siUnit");
					double factor = json.getDouble("factor");
					rc = new Unit(id, rev, name, fallbackId, factor);
				}
				else rc = new Unit(id, rev, name);
			}
		}
		catch (JSONException e) {
			throw new IOException("JSON parsing failed", e);
		}
		return rc;
	}
	
	/**
	 * bug fixed version of JSONObject.getString() which returns null instead of
	 * "null" if the JSON value was null
	 */
	private String json_getString(JSONObject json, String key) throws JSONException {
		String value = null;
		if (!json.isNull(key)) value = json.getString(key);
		return value;
	}
	
	private String json_getString(JSONArray json, int index) throws JSONException {
		String value = null;
		if (!json.isNull(index)) value = json.getString(index);
		return value;
	}

}
