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
package org.tagaprice.server.serializer;

import java.io.IOException;

import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public abstract class Deserializer {
	public abstract Currency getCurrency(String data) throws IOException;
	public abstract Price getPrice(String data) throws IOException;
	public abstract ProductData getProduct(String data) throws IOException;
	public abstract PropertyData getProperty(String data) throws IOException;
	public abstract PropertyList getPropertyList(String data) throws IOException;
	public abstract Quantity getQuantity(String data) throws IOException;
	public abstract ReceiptData getReceipt(String data) throws IOException;
	public abstract SearchResult<?> getSearchResult(String data) throws IOException;
	public abstract ServerResponse getServerResponse(String data) throws IOException;
	public abstract ShopData getShop(String data) throws IOException;
	public abstract Unit getUnit(String data) throws IOException;


	public Serializable getAny(String data, String type) throws IOException {
		Serializable rc = null;

		if (type.equals("currency")) rc = getCurrency(data);
		else if (type.equals("price")) rc = getPrice(data);
		else if (type.equals("product")) rc = getProduct(data);
		else if (type.equals("property")) rc = getProperty(data);
		else if (type.equals("propertyList")) rc = getPropertyList(data);
		else if (type.equals("quantity")) rc = getQuantity(data);
		else if (type.equals("receipt")) rc = getReceipt(data);
		else if (type.equals("searchResult")) rc = getSearchResult(data);
		else if (type.equals("serverResponse")) rc = getServerResponse(data);
		else if (type.equals("shop")) rc = getShop(data);
		else if (type.equals("unit")) rc = getUnit(data);

		return rc; /// TODO
	}
}
