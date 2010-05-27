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
 * Filename: ShopChooser.java
 * Date: May 26, 2010
*/
package org.tagaprice.client;

import org.tagaprice.client.SearchWidget.Filter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShopChooser extends Composite{
	interface MyUiBinder extends UiBinder<VerticalPanel, ShopChooser>{}
	MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField VerticalPanel verticalPanel;
	@UiField SearchWidget searchWidget;
	
	
	public ShopChooser(){
		verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		searchWidget = new SearchWidget(Filter.SHOP);
		verticalPanel.add(searchWidget);
		
		
		
	}
	
}
