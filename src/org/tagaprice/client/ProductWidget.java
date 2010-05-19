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
 * Filename: ProductWidget.java
 * Date: 19.05.2010
*/
package org.tagaprice.client;

import org.tagaprice.shared.ProductData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductWidget extends Composite {

	private ProductData productData;
	private VerticalPanel vePa1 = new VerticalPanel();
	
	public ProductWidget(ProductData productData) {
		initWidget(vePa1);
		this.productData=productData;
		
		//style
		vePa1.setWidth("100%");
		
		
		
		vePa1.add(new ProductPreview(this.productData, false));
	}
}
