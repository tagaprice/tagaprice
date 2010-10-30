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

import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.client.SelectiveVerticalPanel.SelectionType;
import org.tagaprice.client.account.ConfirmRegistrationPage;
import org.tagaprice.client.account.LoginPage;
import org.tagaprice.client.account.RegistrationPage;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class UIManager extends Page {

	private DockPanel myDock = new DockPanel();
	private Image home = new Image(MyResources.INSTANCE.home());
	private HorizontalPanel logoPanel = new HorizontalPanel();
	TaPManager myMng = TaPManager.getInstance();
	private SimplePanel centerPage = new SimplePanel();
	private Page currentPage;

	
	public UIManager() {
		init(myDock);
		myDock.setWidth("100%");
		
		
		
		//logo
		myDock.add(logoPanel, DockPanel.NORTH);
		logoPanel.setWidth("100%");
		logoPanel.setStyleName("LogoPanel");
		logoPanel.add(home);
		logoPanel.setCellWidth(home, "25px");
		SearchWidget search = new SearchWidget(SearchType.ALL, true, true, SelectionType.NOBUTTON);
		logoPanel.add(search);
		logoPanel.setCellWidth(search, "100%");
		home.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("home/");		
			}
		});

		final Button reGps=new Button("R", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				myMng.refeshCurrentAdress();
			}
		});
		reGps.setWidth("25px");
		logoPanel.add(reGps);
		
		//Search
		//myDock.add(new SearchWidget(SearchType.ALL, true, true, SelectionType.NOBUTTON), DockPanel.NORTH);


		//Center
		//myDock.add(myTitlePan, DockPanel.CENTER);
		centerPage.setWidth("100%");
		myDock.add(centerPage, DockPanel.CENTER);

	}



	public void showReceipt(final ReceiptData receiptData){
		waitingPage();
		ReceiptWidget tempReceipt = new ReceiptWidget(receiptData, true); 
		centerPage.setWidget(tempReceipt);
		
		
	}
	
	public void showReceiptsList(){
		waitingPage();
		currentPage=new ReceiptListPage();
		centerPage.setWidget(currentPage);	
	}


	public void showProduct(ProductData productData, Type type){
		waitingPage();
		currentPage = new ProductPage(productData,type);
		centerPage.setWidget(currentPage);
	}

	public void showShop(ShopData shopData, Type type){
		waitingPage();
		currentPage = new ShopPage(shopData, type);
		centerPage.setWidget(currentPage);
	}
	
	public void showUserRegistrationPage(){
		waitingPage();
		currentPage = new RegistrationPage();
		centerPage.setWidget(currentPage);
		
	}
	
	public void showConfirmRegistartionPage(String confirm){
		waitingPage();
		if(confirm == null)
			currentPage = new ConfirmRegistrationPage();
		else
			currentPage = new ConfirmRegistrationPage(confirm);
		
		centerPage.setWidget(currentPage);
	}
	
	
	public void showUserLogin(final boolean loggedIn){
		waitingPage();
		currentPage=new LoginPage(loggedIn);
		centerPage.setWidget(currentPage);
	}


	public void showHome(){
		waitingPage();
		currentPage=new HomePage();
		centerPage.setWidget(currentPage);
	}




	public void waitingPage(){
		centerPage.setWidget(new Label("Waiting..."));
	}



	@Override
	public void setAddress(Address address) {
		if(currentPage!=null) currentPage.setAddress(address);
		
	}

}
