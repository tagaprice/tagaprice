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
 * Filename: ListWidget.java
 * Date: May 17, 2010
 */
package org.tagaprice.client.widgets;

import java.util.ArrayList;

import org.tagaprice.client.AEntityPreview;
import org.tagaprice.client.ProductPreview;
import org.tagaprice.client.ShopPreview;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ShopData;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListWidget<T extends AEntityPreview> extends Composite{

	private VerticalPanel verticalPanel;
	private int currentSelection=-1;


	public ListWidget(){
		verticalPanel=new VerticalPanel();
		verticalPanel.setWidth("100%");
		initWidget(verticalPanel);
	}

	public ListWidget(ArrayList<Entity> entityData){
		this();
		populateList(entityData);
	}
	
	public void addSuggetions(ArrayList<Entity> data){
		for(Entity e:data){
			addSuggestion(createPreview(e));
		}
	}
	
	
	public void addSuggestion(AEntityPreview preview){
		verticalPanel.add(preview);
	}


	public void populateList(ArrayList<Entity> entityData){
		verticalPanel.clear();
		
		for(Entity e: entityData){
			verticalPanel.add(createPreview(e));
		}
	}

	public void populateShopList(ArrayList<ShopData> shopData){
		verticalPanel.clear();

		for(ShopData sd: shopData){
			verticalPanel.add(createPreview(sd));
		}
	}

	public void populateProductList(ArrayList<ProductData> productData){
		verticalPanel.clear();

		for(ProductData pd: productData){
			verticalPanel.add(createPreview(pd));
		}

	}

	private AEntityPreview createPreview(Entity e){
		AEntityPreview rc = null;

		if(e instanceof ShopData){
			rc= new ShopPreview((ShopData)e, false);
		}else if(e instanceof ProductData){
			rc=new ProductPreview((ProductData) e, false);
		}

		return rc;
	}

	public void highlightNextSuggestion(){
		if(currentSelection!=-1)
			verticalPanel.getWidget(currentSelection).removeStyleName("highlightSuggestion");
		if(currentSelection<verticalPanel.getWidgetCount()-1)	
			currentSelection++;
		verticalPanel.getWidget(currentSelection).addStyleName("highlightSuggestion");

	}


	public void highlightPrevSuggestion(){
		if(currentSelection>0)
		{	verticalPanel.getWidget(currentSelection).removeStyleName("highlightSuggestion");
		currentSelection--;
		verticalPanel.getWidget(currentSelection).addStyleName("highlightSuggestion");
		}
	}


	public Entity getSelection(){
		Entity rc=null;

		AEntityPreview ep = (AEntityPreview)verticalPanel.getWidget(currentSelection);
		if(ep instanceof ProductPreview){
			rc=((ProductPreview) ep).getProductData();
		}else if(ep instanceof ShopPreview){
			rc=((ShopPreview) ep).getShopData();
		}

		return rc;
	}


	public AEntityPreview getSelectionPreview(){
		AEntityPreview rc= (AEntityPreview) verticalPanel.getWidget(currentSelection);
		currentSelection=-1;
		return rc;
	}



}

