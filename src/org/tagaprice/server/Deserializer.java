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
 * Filename: Deserializer.java
 * Date: 20.05.2010
*/
package org.tagaprice.server;

import java.io.IOException;

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

public abstract class Deserializer {
	public abstract Currency getCurrency(JSONObject json) throws IOException;
	public abstract Price getPrice(JSONObject json) throws IOException;
	public abstract ProductData getProduct(JSONObject json) throws IOException;
	public abstract PropertyData getProperty(JSONObject json) throws IOException;
	public abstract PropertyList getPropertyList(JSONObject json) throws IOException;
	public abstract Quantity getQuantity(JSONObject json) throws IOException;
	public abstract ReceiptData getReceipt(JSONObject json) throws IOException;
	public abstract ServerResponse getServerResponse(JSONObject json) throws IOException;
	public abstract ShopData getShop(JSONObject json) throws IOException;
	public abstract Unit getUnit(JSONObject json) throws IOException;


	public Serializable getAny(JSONObject json, String type) throws IOException {
		Serializable rc = null;

		if (type.equals("currency")) rc = getCurrency(json);
		else if (type.equals("price")) rc = getPrice(json);
		else if (type.equals("product")) rc = getProduct(json);
		else if (type.equals("property")) rc = getProperty(json);
		else if (type.equals("propertyList")) rc = getPropertyList(json);
		else if (type.equals("quantity")) rc = getQuantity(json);
		else if (type.equals("receipt")) rc = getReceipt(json);
		else if (type.equals("serverResponse")) rc = getServerResponse(json);
		else if (type.equals("shop")) rc = getShop(json);
		else if (type.equals("unit")) rc = getUnit(json);

		return rc; /// TODO
	}
}
