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

import org.tagaprice.client.NewPreview.Filter;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ShopData;

import com.google.gwt.user.client.ui.PopupPanel;

public class ProductSearchWidget extends SearchWidget{


	private ReceiptWidget parent;
	private ListWidget<ProductPreview> productList; 

	public ProductSearchWidget(ReceiptWidget parentReceipt){
		super();
		parent =parentReceipt;

		addTextBox();
		suggestPanel = new PopupPanel(true);
		productList = new ListWidget<ProductPreview>();
		suggestPanel.add(productList);

	}


	@Override
	protected ListWidget<ProductPreview> getSuggestionList() {
		return productList;
	}


	@Override
	protected void handleEnterKey() {
		textBox.setText(null);
		((PopupPanel)suggestPanel).hide(true);
		parent.addProduct(((ProductPreview)productList.getSelectionPreview()).getProductData());
		
	}


	@Override
	protected void sendSearchRequest(String searchString) {
		setProductSuggestions(tapManager.searchProducts(textBox.getText(), this));	
	}


	public void setProductSuggestions(ArrayList<ProductData> suggestData){
		productList.populateProductList(suggestData);
		productList.addSuggestion(new NewPreview(Filter.PRODUCT, parent));
		((PopupPanel) suggestPanel).showRelativeTo(textBox);
	}

}