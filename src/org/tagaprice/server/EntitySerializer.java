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
package org.tagaprice.server;

import java.io.IOException;

import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public abstract class EntitySerializer {
	public abstract void put(Currency currency) throws IOException;
	public abstract void put(Price price) throws IOException;
	public abstract void put(ProductData product) throws IOException;
	public abstract void put(Quantity quantity) throws IOException;
	public abstract void put(ReceiptData receipt) throws IOException;
	public abstract void put(ServerResponse response) throws IOException;
	public abstract void put(ShopData shop) throws IOException;
	public abstract void put(Unit unit) throws IOException;
	
	protected void putAny(Entity e) throws IOException {
		if (e instanceof Currency) put((Currency) e);
		else if (e instanceof Price) put((Price) e);
		else if (e instanceof ProductData) put((ProductData) e);
		else if (e instanceof Quantity) put((Quantity) e);
		else if (e instanceof ReceiptData) put((ReceiptData) e);
		else if (e instanceof ServerResponse) put((ServerResponse) e);
		else if (e instanceof ShopData) put((ShopData) e);
		else if (e instanceof Unit) put((Unit) e);
	}
}
