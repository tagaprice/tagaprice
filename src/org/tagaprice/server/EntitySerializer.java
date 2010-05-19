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

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public abstract class EntitySerializer {
	public abstract StringBuffer put(ProductData product);
	public abstract StringBuffer put(Quantity quantity);
	public abstract StringBuffer put(ReceiptData receipt);
	public abstract StringBuffer put(ServerResponse response);
	public abstract StringBuffer put(ShopData shop);
	public abstract StringBuffer put(Unit unit);
	
	protected StringBuffer putAny(Entity e) {
		StringBuffer rc = null;
		
		if (e instanceof ProductData) rc = put((ProductData) e);
		else if (e instanceof Quantity) rc = put((Quantity) e);
		else if (e instanceof ReceiptData) rc = put((ReceiptData) e);
		else if (e instanceof ServerResponse) rc = put((ServerResponse) e);
		else if (e instanceof ShopData) rc = put((ShopData) e);
		else if (e instanceof Unit) rc = put((Unit) e);
		
		return rc;
	}
}
