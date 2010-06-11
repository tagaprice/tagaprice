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
 * Filename: ProductChooser.java
 * Date: Jun 8, 2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.SearchWidget.Filter;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductChooser extends Composite{

	private VerticalPanel verticalPanel;
	private SearchWidget searchWidget;
	private TaPManager tapManager;
	private final ReceiptWidget parent;
	
	public ProductChooser(final ReceiptWidget parent){
		this.parent =parent;
		
		tapManager = new TaPManagerImpl();

		verticalPanel = new VerticalPanel();

		searchWidget = new SearchWidget(Filter.PRODUCT);
		verticalPanel.add(searchWidget);
		
		initWidget(verticalPanel);
			
	}
	
	
}
