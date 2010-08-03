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
 * Filename: UIManager.java
 * Date: 18.05.2010
 */
package org.tagaprice.client;

import org.tagaprice.client.user.RegistrationPage;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class UIManager extends Composite {

	DockPanel myDock = new DockPanel();
	Image home = new Image(MyResources.INSTANCE.home());
	HorizontalPanel logoPanel = new HorizontalPanel();
	TaPManager myMng = TaPManagerImpl.getInstance();
	HomePage homePage = new HomePage();
	TitlePanel myTitlePan = new TitlePanel("Home", homePage, TitlePanel.Level.H1);
	UniversalSearchWidget universalSearch;
	InfoBox infoBox = new InfoBox();

	public UIManager() {
		initWidget(myDock);
		init();
	}

	private void init(){
		myDock.setWidth("100%");

		universalSearch = new UniversalSearchWidget(logoPanel);

		
		
		
		//logo
		myDock.add(logoPanel, DockPanel.NORTH);
		logoPanel.setWidth("100%");
		logoPanel.add(home);
		logoPanel.add(new Label("TagAPrice"));
		home.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("home/");		
			}
		});

		//Serach
		myDock.add(universalSearch, DockPanel.NORTH);



		//InfoBox
		myDock.add(infoBox, DockPanel.NORTH);

		//Center
		myDock.add(myTitlePan, DockPanel.CENTER);




	}


	public void showReceipt(final ReceiptData receiptData){
		
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onFailure(Throwable reason) {
				System.err.println("code Download Failed");					
			}

			@Override
			public void onSuccess() {
				if(receiptData==null){
					ReceiptWidget tempReceipt = new ReceiptWidget(); 
					myTitlePan.setTitleWidget("Kassazettel eintragen", tempReceipt);
				}
				else {
					ReceiptWidget tempReceipt = new ReceiptWidget(receiptData, true); 
					myTitlePan.setTitleWidget("Kassazettel eintragen",tempReceipt);
				}
				
			}
			
		});
		
		
	}


	public void showProduct(ProductData productData, Type type){
		ProductPage tempProduct = new ProductPage(productData,type);
		myTitlePan.setTitleWidget(productData.getTitle(), tempProduct);
	}

	public void showShop(ShopData shopData, Type type){
		ShopPage tempShop = new ShopPage(shopData, type);
		myTitlePan.setTitleWidget(shopData.getTitle(), tempShop);
	}
	
	public void showUserRegistrationPage(final String verificationCode){
		
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				RegistrationPage tempRegi = new RegistrationPage(verificationCode);
				myTitlePan.setTitleWidget("Registration", tempRegi);
				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				System.err.println("code Download Failed");			
			}
		});
		
		
	}


	public void showHome(){
		myTitlePan.setTitleWidget(
				"Home", 
				homePage);
	}

	/**
	 * 
	 * @return
	 */
	public InfoBox getInfoBox(){
		return infoBox;
	}


	public void waitingPage(){
		myTitlePan.setTitleWidget("Waiting", new Label("Processing..."));
	}

}