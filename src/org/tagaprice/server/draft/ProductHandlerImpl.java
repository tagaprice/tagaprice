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
 * Filename: ProductHandlerImpl.java
 * Date: 27.05.2010
*/
package org.tagaprice.server.draft;

import org.tagaprice.client.ProductHandler;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.Unit;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProductHandlerImpl extends RemoteServiceServlet implements ProductHandler{

	@Override
	public ProductData get(Long id) throws IllegalArgumentException {
		ProductData test = new ProductData(152, 15, 16, "Mousse au Chocolat", "logo.png", 20, 80, new Price(139, 23, "€"), new Quantity(125, 23, "g"),true);
		
		
		PropertyList properties = new PropertyList();
		
		properties.add(new PropertyData("energy", "Brennwert", "2109", new Unit(1, "kj")));
		properties.add(new PropertyData("protein", "Eiweiss", "5,3", new Unit(2, "g")));
		properties.add(new PropertyData("carbohydrate", "Kohlenhydrate", "27,5", new Unit(2, "g")));
		properties.add(new PropertyData("fat", "Fett", "41,3", new Unit(2, "g")));
		properties.add(new PropertyData("fiber", "Ballaststoffe", "1,9", new Unit(2, "g")));
		properties.add(new PropertyData("sodium", "Natrium", "0,05", new Unit(2, "g")));
		properties.add(new PropertyData("link", "URL", "tagaprice.com", new Unit(5, "fl")));
		properties.add(new PropertyData("ean", "EAN", "14526486", new Unit(5, "g")));
		
		test.setProperties(properties);
		
		return test;
	}

}
