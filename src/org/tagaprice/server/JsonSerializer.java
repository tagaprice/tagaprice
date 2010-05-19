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
 * Filename: JsonSerializer.java
 * Date: 19.05.2010
*/
package org.tagaprice.server;

import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;

public class JsonSerializer extends EntitySerializer {
	@Override
	public StringBuffer put(ProductData product) {
		StringBuffer rc = new StringBuffer();
		
		rc.append("{\nid: ");
		rc.append(product.getId());
		rc.append(",\nname: \"");
		rc.append(product.getName());
		rc.append("\",\nprice: ");
		rc.append(product.getPrice());
		rc.append(",\ncurrency: \"");
		rc.append(product.getCurrency()); /// TODO create a Currency class
		/// TODO implement all the rest
		rc.append("\"\n}");
		return rc;
	}

	@Override
	public StringBuffer put(ShopData shop) {
		// TODO Auto-generated method stub
		StringBuffer rc = new StringBuffer();
		
		return rc;
	}

	@Override
	public StringBuffer put(ReceiptData receipt) {
		// TODO Auto-generated method stub
		StringBuffer rc = new StringBuffer();
		
		return rc;
	}

	@Override
	public StringBuffer put(ServerResponse response) {
		// TODO Auto-generated method stub
		StringBuffer rc = new StringBuffer();
		rc.append("{\nstatus: \"");
		rc.append(response.getStatusName());
		rc.append("\"\n");
		
		rc.append(putAny(response.getEntity()));
		
		rc.append("}");
		return rc;
	}
	
}
