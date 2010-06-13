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
 * Filename: EntitySender.java
 * Date: 19.05.2010
*/
package org.tagaprice.server.serializer;

import java.io.IOException;

import org.tagaprice.shared.Currency;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.RequestError;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public abstract class Serializer {
	public abstract void put(Currency currency, boolean annotateType) throws IOException;
	public abstract <T extends Serializable> void put(SearchResult<T> list, boolean annotateType) throws IOException;
	public abstract void put(Price price, boolean annotateType) throws IOException;
	public abstract void put(ProductData product, boolean annotateType) throws IOException;
	public abstract void put(PropertyData property, boolean annotateType) throws IOException;
	public abstract void put(Quantity quantity, boolean annotateType) throws IOException;
	public abstract void put(ReceiptData receipt, boolean annotateType) throws IOException;
	public abstract void put(RequestError error, boolean annotateType) throws IOException;
	public abstract void put(ServerResponse response, boolean annotateType) throws IOException;
	public abstract void put(ShopData shop, boolean annotateType) throws IOException;
	public abstract void put(Unit unit, boolean annotateType) throws IOException;
	
	protected void putAny(Serializable s) throws IOException {
		putAny(s, false);
	}
	
	protected void putAny(Serializable s, boolean annotateType) throws IOException {
		if (s instanceof Currency) put((Currency) s, annotateType);
		else if (s instanceof SearchResult<?>) put((SearchResult<?>) s, annotateType);
		else if (s instanceof Price) put((Price) s, annotateType);
		else if (s instanceof ProductData) put((ProductData) s, annotateType);
		else if (s instanceof PropertyData) put((PropertyData) s, annotateType);
		else if (s instanceof RequestError) put((RequestError) s, annotateType);
		else if (s instanceof Quantity) put((Quantity) s, annotateType);
		else if (s instanceof ReceiptData) put((ReceiptData) s, annotateType);
		else if (s instanceof ServerResponse) put((ServerResponse) s, annotateType);
		else if (s instanceof ShopData) put((ShopData) s, annotateType);
		else if (s instanceof Unit) put((Unit) s, annotateType);
	}
}
