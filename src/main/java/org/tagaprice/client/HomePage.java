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
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class HomePage extends InfoBoxComposite {

	TaPManager Mng = TaPManager.getInstance();
	
	Grid grid = new Grid(2, 3);
	ImageTextButton newReceipt = new ImageTextButton("add Receipt", new Image(MyResources.INSTANCE.productPriview()), "receipt/get");

	
	public HomePage() {
		init(grid);
		setStyleName("HomePage");
		
		grid.setWidth("100%");
		grid.setStyleName("HomePage-Grid");
		//grid.setBorderWidth(1);
		grid.getCellFormatter().setWidth(0, 0, "33%");
		grid.getCellFormatter().setWidth(0, 1, "33%");
		grid.getCellFormatter().setWidth(0, 2, "33%");
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);

		//
		
		
		grid.setWidget(0, 0, new ImageTextButton("add Product", new Image(MyResources.INSTANCE.productPriview()), "product/new"));
		grid.setWidget(0, 1, new ImageTextButton("add Shop", new Image(MyResources.INSTANCE.productPriview()), "shop/new"));		
		
		if(Cookies.getCookie("TaPSId")!=null){
			grid.setWidget(0, 2, new ImageTextButton("Logout", new Image(MyResources.INSTANCE.productPriview()), "user/logout"));	
			grid.setWidget(1, 0, new ImageTextButton("add Receipt", new Image(MyResources.INSTANCE.productPriview()), new ClickHandler() {			
				@Override
				public void onClick(ClickEvent event) {
					TaPManager.getInstance().getUIManager().waitingPage();
										
					try {
						HandlerManager.getReceiptHandler().save(null, new AsyncCallback<ReceiptData>() {
							
							@Override
							public void onSuccess(ReceiptData result) {
								History.newItem("receipt/get&id="+result.getId());
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								showInfo("Fail: "+caught, BoxType.WARNINGBOX);
							}
						});
					} catch (IllegalArgumentException e) {
						showInfo("Fail: "+e, BoxType.WARNINGBOX);
					} catch (InvalidLoginException e) {
						showInfo("Fail: "+e, BoxType.WARNINGBOX);
					}
					
						
				}
			}));
			grid.setWidget(1, 1, new ImageTextButton("My Receipts", new Image(MyResources.INSTANCE.productPriview()), "receipt/list"));
		}else{
			grid.setWidget(0, 2, new ImageTextButton("Login", new Image(MyResources.INSTANCE.productPriview()), "user/login"));	
			grid.setWidget(1, 0, new ImageTextButton("Sign Up", new Image(MyResources.INSTANCE.productPriview()), "user/registration/new"));		
		}

		
		

	}
}

class ImageTextButton extends Composite {
	FlowPanel vePa1 = new FlowPanel();
	Label label;
	Image image;
	
	public ImageTextButton(String text, Image pic, ClickHandler handler){
		init(text, pic);
		addClickHandler(handler);
	}
	
	public ImageTextButton(String text, Image pic, final String link) {
		init(text, pic);

		addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem(link);				
			}
		});
		
	}
	
	private void init(String text, Image pic){
		initWidget(vePa1);
		label = new Label(text);
		image = pic;
		
		vePa1.add(image);
		vePa1.add(label);

		setStyleName("ImageTextButton");
	}
	
	private void addClickHandler(ClickHandler handler){
		addDomHandler(handler,ClickEvent.getType());
	}
}
