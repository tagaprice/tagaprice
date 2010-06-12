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
import org.tagaprice.shared.List;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.RequestError;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public abstract class Serializer {
	public abstract void put(Currency currency) throws IOException;
	public abstract void put(Price price) throws IOException;
	public abstract void put(ProductData product) throws IOException;
	public abstract void put(PropertyData property) throws IOException;
	public abstract void put(PropertyList propertyList) throws IOException;
	public abstract void put(Quantity quantity) throws IOException;
	public abstract void put(ReceiptData receipt) throws IOException;
	public abstract void put(RequestError error) throws IOException;
	public abstract void put(ServerResponse response) throws IOException;
	public abstract void put(ShopData shop) throws IOException;
	public abstract void put(Unit unit) throws IOException;
	
	protected void putAny(Serializable s) throws IOException {
		if (s instanceof Currency) put((Currency) s);
		else if (s instanceof Price) put((Price) s);
		else if (s instanceof ProductData) put((ProductData) s);
		else if (s instanceof PropertyData) put((PropertyData) s);
		else if (s instanceof PropertyList) put((PropertyList) s);
		else if (s instanceof RequestError) put((RequestError) s);
		else if (s instanceof Quantity) put((Quantity) s);
		else if (s instanceof ReceiptData) put((ReceiptData) s);
		else if (s instanceof ServerResponse) put((ServerResponse) s);
		else if (s instanceof ShopData) put((ShopData) s);
		else if (s instanceof Unit) put((Unit) s);
	}
}
