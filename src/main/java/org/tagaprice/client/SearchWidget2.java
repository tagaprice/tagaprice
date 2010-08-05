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
 * Filename: SearchWidget2.java
 * Date: 20.07.2010
*/
package org.tagaprice.client;

import org.tagaprice.client.SelectiveVerticalPanel.SelectionType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.ShopData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchWidget2 extends Composite {

	public enum SearchType {ALL, PRODCUT, SHOP}
	private TextBox searchBox = new TextBox();
	private VerticalPanel vePa1 = new VerticalPanel();
	private VerticalPanel vePa2 = new VerticalPanel();
	private SelectiveVerticalPanel selVePa;
	private PopupPanel popPa;
	
	public SearchWidget2(
			String search, 
			SearchType searchType,
			boolean showNew, 
			boolean popup,
			SelectionType selectionType) {
		
		
		initWidget(vePa1);
		vePa1.setWidth("100%");
		vePa1.add(searchBox);
		searchBox.setWidth("100%");
		vePa2.setWidth("100%");
		
		//
		selVePa = new SelectiveVerticalPanel(selectionType);
		selVePa.setWidth("100%");
		
		//popup
		if(popup){
			popPa=new PopupPanel(true);
			popPa.setWidget(vePa2);
		}else{
			vePa1.add(vePa2);
		}
		
		
		vePa2.add(selVePa);
		
		if(showNew){
			boolean product = false;
			boolean shop = false;
			if(searchType.equals(SearchType.PRODCUT)){
				product=true;
			}else if(searchType.equals(SearchType.SHOP)){
				shop=true;
			}else{
				product=true;
				shop=true;
			}
			
			if(product){
				Label productLabel = new Label("New Product");
				vePa2.add(productLabel);
				
				productLabel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						System.out.println("new Product");
					}
				});
			}
			
			if(shop){
				Label shopLabel = new Label("New Shop");
				vePa2.add(shopLabel);
				
				shopLabel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						System.out.println("new Shop");
					}
				});
			}
		}
	}
	
	
	public SearchWidget2(
			String search, 
			SearchType searchType,
			boolean showNew, 
			boolean popup,
			SelectionType selectionType,
			BoundingBox bbox) {
		this(search, searchType, showNew, popup, selectionType);
	}
	
	public SearchWidget2(
			String search,
			SearchType searchType,
			boolean showNew, 
			boolean popup,
			SelectionType selectionType,
			ShopData shopData) {
		this(search, searchType, showNew, popup, selectionType);
	}
}
