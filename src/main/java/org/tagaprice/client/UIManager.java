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

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.client.SelectiveVerticalPanel.SelectionType;
import org.tagaprice.client.account.ConfirmRegistrationPage;
import org.tagaprice.client.account.LoginPage;
import org.tagaprice.client.account.RegistrationPage;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class UIManager extends InfoBoxComposite {

	DockPanel myDock = new DockPanel();
	Image home = new Image(MyResources.INSTANCE.home());
	HorizontalPanel logoPanel = new HorizontalPanel();
	TaPManager myMng = TaPManager.getInstance();
	SimplePanel centerPage = new SimplePanel();

	public UIManager() {
		init(myDock);
		myDock.setWidth("99%");
		
		
		
		//logo
		myDock.add(logoPanel, DockPanel.NORTH);
		logoPanel.setWidth("100%");
		logoPanel.setStyleName("LogoPanel");
		logoPanel.add(home);
		logoPanel.setCellWidth(home, "25px");
		logoPanel.add(new SearchWidget(SearchType.ALL, true, true, SelectionType.NOBUTTON));
		home.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("home/");		
			}
		});

		//Search
		//myDock.add(new SearchWidget(SearchType.ALL, true, true, SelectionType.NOBUTTON), DockPanel.NORTH);


		//Center
		//myDock.add(myTitlePan, DockPanel.CENTER);
		centerPage.setWidth("100%");
		myDock.add(centerPage, DockPanel.CENTER);

	}



	public void showReceipt(final ReceiptData receiptData){
		waitingPage();
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onFailure(Throwable reason) {
				showInfo("code Download Failed", BoxType.WARNINGBOX);
			}

			@Override
			public void onSuccess() {
				if(receiptData==null){
					ReceiptWidget tempReceipt = new ReceiptWidget(); 
					//myTitlePan.setTitleWidget("Kassazettel eintragen", tempReceipt);
					centerPage.setWidget(tempReceipt);
				}
				else {
					ReceiptWidget tempReceipt = new ReceiptWidget(receiptData, true); 
					//myTitlePan.setTitleWidget("Kassazettel eintragen",tempReceipt);
					centerPage.setWidget(tempReceipt);
				}
				
			}
			
		});
		
		
	}


	public void showProduct(ProductData productData, Type type){
		waitingPage();
		ProductPage tempProduct = new ProductPage(productData,type);
		centerPage.setWidget(tempProduct);
	}

	public void showShop(ShopData shopData, Type type){
		waitingPage();
		ShopPage tempShop = new ShopPage(shopData, type);
		centerPage.setWidget(tempShop);
	}
	
	public void showUserRegistrationPage(){
		waitingPage();
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				RegistrationPage tempRegi = new RegistrationPage();
				centerPage.setWidget(tempRegi);
				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				showInfo("code Download Failed", BoxType.WARNINGBOX);
			}
		});	
		
	}
	
	public void showConfirmRegistartionPage(final String confirm){
		waitingPage();
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				ConfirmRegistrationPage confPage;
				
				if(confirm == null)
					confPage = new ConfirmRegistrationPage();
				else
					confPage = new ConfirmRegistrationPage(confirm);
				
				centerPage.setWidget(confPage);
			}
			
			@Override
			public void onFailure(Throwable reason) {
				showInfo("code Download Failed", BoxType.WARNINGBOX);				
			}
		});
	}
	
	
	public void showUserLogin(final boolean loggedIn){
		waitingPage();
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				centerPage.setWidget(new LoginPage(loggedIn));
				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				showInfo("Download fail at UserLogin", BoxType.WARNINGBOX);
				
			}
		});
	}


	public void showHome(){
		waitingPage();
		centerPage.setWidget(new HomePage());
	}




	public void waitingPage(){
		centerPage.setWidget(new Label("Waiting..."));
	}

}
