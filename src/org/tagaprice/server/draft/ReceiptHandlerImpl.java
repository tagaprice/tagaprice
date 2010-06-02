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
 * Filename: ReceiptHandlerImpl.java
 * Date: 30.05.2010
*/
package org.tagaprice.server.draft;

import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.client.ReceiptHandler;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ReceiptHandlerImpl extends RemoteServiceServlet implements ReceiptHandler {

	@Override
	public ReceiptData get(Long id) throws IllegalArgumentException {
		//MockMock
		ReceiptData receiptContainer;
		
		if(id==0){
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			receiptContainer = new ReceiptData(
					15, 
					true,
					"Default title", 
					new Date(), 
					0, 
					null, 
					myProducts);
		}else if(id==15) {
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();	
			receiptContainer = new ReceiptData(
					15, 
					true,
					"Default title", 
					new Date(), 
					0, 
					null, 
					myProducts);
		}else{
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			myProducts.add(new ProductData(152, 15, 16, "Grouda geschnitten", "logo.png", 20, 80, new Price(325, 23, "€"), new Quantity(260, 23, "g"),true));
			myProducts.add(new ProductData(120, 15, 16, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, new Price(98, 23, "€"), new Quantity(1, 24, "l"),false));
			
			receiptContainer = new ReceiptData(
					18, 
					false,
					"Weihnachts einkauf", 
					new Date(), 
					0, 
					new ShopData(15, "Billa Flossgasse", "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")), 
					myProducts);
		}
		
		
		
		return receiptContainer;
	}

}
