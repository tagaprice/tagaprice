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
 * Filename: HomePage.java
 * Date: 18.07.2010
*/
package org.tagaprice.client;

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.shared.ReceiptData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class HomePage extends InfoBoxComposite {

	TaPManager Mng = TaPManager.getInstance();
	
	Grid grid = new Grid(9, 3);
	Label newReceiptLabel = new Label("Rechnung eintrage");
	Image newReceiptImage = new Image(MyResources.INSTANCE.productPriview());
	
	Label registerLable = new Label("Sign Up");
	Image registerImage = new Image(MyResources.INSTANCE.productPriview());
	
	Label newProductLabel = new Label("New Product");
	Image newProductImage = new Image(MyResources.INSTANCE.productPriview());
	
	Label newShopLabel = new Label("New Shop");
	Image newShopImage = new Image(MyResources.INSTANCE.productPriview());
	
	Label draftLabel = new Label("Drafts");
	Image draftImage = new Image(MyResources.INSTANCE.productPriview());
	
	Label loginLabel = new Label("Login");
	Image loginImage = new Image(MyResources.INSTANCE.productPriview());
	
	public HomePage() {
		init(grid);
		setStyleName("HomePage");
		
		
		grid.setWidth("100%");
		grid.setStyleName("HomePage-Grid");
		//grid.setBorderWidth(1);
		grid.getCellFormatter().setWidth(0, 0, "33%");
		grid.getCellFormatter().setWidth(0, 1, "33%");
		grid.getCellFormatter().setWidth(0, 2, "33%");
		
		//
		
		grid.setWidget(0, 0, newReceiptImage);
		grid.setWidget(1, 0, newReceiptLabel);
		
		grid.setWidget(0, 1, registerImage);
		grid.setWidget(1, 1, registerLable);
		
		grid.setWidget(0, 2, newProductImage);
		grid.setWidget(1, 2, newProductLabel);
		
		
		grid.setWidget(3, 0, newShopImage);
		grid.setWidget(4, 0, newShopLabel);
		
		grid.setWidget(3, 1, draftImage);
		grid.setWidget(4, 1, draftLabel);
		
		grid.setWidget(3, 2, loginImage);
		grid.setWidget(4, 2, loginLabel);
		
		
		grid.setWidget(6, 0, new Image(MyResources.INSTANCE.productPriview()));
		grid.setWidget(7, 0, new Label("Label"));
		
		grid.setWidget(6, 1, new Image(MyResources.INSTANCE.productPriview()));
		grid.setWidget(7, 1, new Label("Label"));
		
		grid.setWidget(6, 2, new Image(MyResources.INSTANCE.productPriview()));
		grid.setWidget(7, 2, new Label("Label"));
		
		
		//Listener
		newReceiptImage.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				//TODO RPC Change
				HandlerManager.getReceiptHandler().get(0l, new AsyncCallback<ReceiptData>() {

					@Override
					public void onSuccess(ReceiptData tResult) {
						History.newItem("receipt/get&id="+tResult.getId());
					}

					@Override
					public void onFailure(Throwable caught) {
						showInfo("Fail: "+caught, BoxType.WARNINGBOX);
					}
				});		
			}
		});
		
		newReceiptLabel.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				//TODO RPC Change
				HandlerManager.getReceiptHandler().get(0l, new AsyncCallback<ReceiptData>() {

					@Override
					public void onSuccess(ReceiptData tResult) {
						History.newItem("receipt/get&id="+tResult.getId());
					}

					@Override
					public void onFailure(Throwable caught) {
						showInfo("Fail: "+caught, BoxType.WARNINGBOX);
					}
				});		
			}
		});
		
		
		registerLable.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("user/registration/new");				
			}
		});
		
		registerImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("user/registration/new");				
			}
		});
		
		
		newProductImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("product/new");				
			}
		});
		
		newProductLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("product/new");				
			}
		});
		
		newShopImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("shop/new");				
			}
		});
		
		newShopLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("shop/new");				
			}
		});
		
		loginImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("user/login");				
			}
		});
		
		loginLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("user/login");				
			}
		});
	}
}
