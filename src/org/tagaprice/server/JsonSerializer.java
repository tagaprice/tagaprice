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
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public class JsonSerializer extends EntitySerializer {
	@Override
	public StringBuffer put(ProductData product) {
		StringBuffer rc = new StringBuffer();
		
		rc.append("{\n\"id\": ");
		rc.append(product.getId());
		
		rc.append(",\n\"name\": \"");
		rc.append(product.getName());
		
		rc.append("\",\n\"imageSrc\": \"");
		rc.append(product.getImageSrc());
		
		rc.append("\",\n\"price\": ");
		rc.append(product.getPrice());
		
		rc.append(",\n\"currency\": \"");
		rc.append(product.getCurrency()); /// TODO create a Currency class
		
		rc.append("\",\n\"quantity\": ");
		rc.append(put(product.getQuantity()));

		rc.append(",\n\"rating\": ");
		rc.append(product.getRating());
		
		rc.append(",\n\"progress\": ");
		rc.append(product.getProgress());
		
		rc.append("\n}");
		return rc;
	}

	@Override
	public StringBuffer put(Quantity quantity) {
		// TODO Auto-generated method stub
		StringBuffer rc = new StringBuffer();
		
		rc.append("{\n\"quantity\": ");
		rc.append(quantity.getQuantity());
		rc.append(",\n\"unit\": ");
		rc.append(put(quantity.getUnit()));
		rc.append("\n}");
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
	
	@Override
	public StringBuffer put(ShopData shop) {
		// TODO Auto-generated method stub
		StringBuffer rc = new StringBuffer();
		
		return rc;
	}

	@Override
	public StringBuffer put(Unit unit) {
		// TODO Auto-generated method stub
		StringBuffer rc = new StringBuffer();
		
		rc.append("{\n\"id\": ");
		rc.append(unit.getId());
		
		rc.append(",\n\"name\": \"");
		rc.append(unit.getName());
		rc.append('"');
		
		if (unit.getFallbackId() > 0) {
			rc.append(",\n\"siUnit\": ");
			rc.append(unit.getFallbackId());
			rc.append(",\n\"factor\": ");
			rc.append(unit.getFactor());
		}
		rc.append("\n}");
		return rc;
	}
}
