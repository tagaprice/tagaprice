/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: PriceHandlerImpl.java
 * Date: 02.06.2010
*/
package org.tagaprice.server.rpc;

import java.util.ArrayList;

import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.PriceData;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.rpc.PriceHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PriceHandlerImpl  extends RemoteServiceServlet implements PriceHandler {

	@Override
	public ArrayList<PriceData> get(Long id, BoundingBox bbox, PriceMapType type)
			throws IllegalArgumentException {
		
		ArrayList<PriceData> list = new ArrayList<PriceData>();
		
		int c=(int)(Math.random()*10);
		for(int i=0;i<c;i++){
			list.add(new PriceData(
					new ProductData(152, 4, "Mousse au Chocolat", 1, 15, 16, "logo.png", 20, (int)(Math.random()*100), new Price((int)(Math.random()*100), 23, 8, "€", 1), new Quantity(125, 23, 3, "g", 1),true), 
					new ShopData(123, 1825, "ACME Store", 2, null, 80, 25, new Address("Park Avenue 23", "New York", "USA", 48.21211+(-0.001*i), 16.37647+(-0.0001*i)))));
		}
		
		
		
		
		return list;
	}

}
