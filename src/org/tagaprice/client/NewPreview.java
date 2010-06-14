/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: NewPreview.java
 * Date: May 27, 2010
 */
package org.tagaprice.client;

import org.tagaprice.shared.ProductData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class NewPreview extends EntityPreview{

	public enum Filter {SHOP, PRODUCT};
	Filter filter;
	Label label;
	Composite parent;


	public NewPreview(Filter filter, Composite parent){
		this.filter = filter;
		this.parent = parent;
		if(filter.equals(Filter.SHOP))
			label = new Label("new shop");
		else label = new Label("new product");

		initWidget(label);
	}

	@Override
	public void click() {
		if(filter.equals(Filter.SHOP)){ 
			if(parent instanceof ReceiptWidget)
				((ReceiptWidget)parent).shop.setWidget(new Label("this is a new shop - currently just a label"));
		} else {  
			//create new shop
		}

	}




}
