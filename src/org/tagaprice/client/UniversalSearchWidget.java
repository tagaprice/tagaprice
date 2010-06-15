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
 * Filename: UniversalSearchWidget.java
 * Date: Jun 14, 2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.NewPreview.Filter;
import org.tagaprice.shared.Entity;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;

public class UniversalSearchWidget extends SearchWidget{
	
	private Panel parent;
	private ListWidget<EntityPreview> suggestList;
//	private ListWidget<ProductPreview> productList; 
//	private ListWidget<ShopPreview> shopList; 
//	private VerticalPanel vePa;
	
	public UniversalSearchWidget(Panel parentPanel){
		super();
		parent = parentPanel;
		
		addTextBox();
		
//		vePa = new VerticalPanel();
//		productList = new ListWidget<ProductPreview>();
//		shopList = new ListWidget<ShopPreview>();
		suggestList=new ListWidget<EntityPreview>();
//		vePa.add(productList);
//		vePa.add(shopList);
		
		suggestPanel = new PopupPanel(true);
//		suggestPanel.add(vePa);
		suggestPanel.add(suggestList);
	}

	@Override
	protected ListWidget<EntityPreview> getSuggestionList() {
		return suggestList;
	}

	@Override
	protected void handleEnterKey() {
		textBox.setText(null);
		((PopupPanel)suggestPanel).hide(true);
		suggestList.getSelectionPreview().click();
	
	}

	@Override
	protected void sendSearchRequest(String searchString) {
		setSuggestions(tapManager.search(textBox.getText(), this));
//		setProductSuggestions(p);	
		
//		setShopSuggestions(tapManager.searchShops(textBox.getText(), this));
		
	}

/*	public void setProductSuggestions(ArrayList<ProductData> suggestData){
		productList.populateProductList(suggestData);
		productList.addSuggestion(new NewPreview(Filter.PRODUCT, parent));
		((PopupPanel) suggestPanel).showRelativeTo(textBox);
	}
	
	
	public void setShopSuggestions(ArrayList<ShopData> suggestData){
		shopList.populateShopList(suggestData);
		shopList.addSuggestion(new NewPreview(Filter.SHOP, parent));	
	}
*/
	private void setSuggestions(ArrayList<Entity> suggestData){
		suggestList.populateList(suggestData);
		suggestList.addSuggestion(new NewPreview(Filter.PRODUCT, parent));
		suggestList.addSuggestion(new NewPreview(Filter.SHOP, parent));
		((PopupPanel) suggestPanel).showRelativeTo(textBox);
	}
	
}
