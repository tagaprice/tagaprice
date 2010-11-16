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

import org.tagaprice.client.pages.HomePage;
import org.tagaprice.client.pages.APage;
import org.tagaprice.client.pages.ProductPage;
import org.tagaprice.client.pages.ReceiptListPage;
import org.tagaprice.client.pages.ReceiptPage;
import org.tagaprice.client.pages.ShopPage;
import org.tagaprice.client.pages.account.ConfirmRegistrationPage;
import org.tagaprice.client.pages.account.LoginPage;
import org.tagaprice.client.pages.account.RegistrationPage;
import org.tagaprice.client.widgets.SearchWidget;
import org.tagaprice.client.widgets.SearchWidget.SearchType;
import org.tagaprice.client.widgets.SelectiveListWidget.SelectionType;
import org.tagaprice.shared.data.Address;
import org.tagaprice.shared.data.Product;
import org.tagaprice.shared.data.Receipt;
import org.tagaprice.shared.data.Shop;
import org.tagaprice.shared.data.Type;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class UIManager extends APage {

	private DockPanel myDock = new DockPanel();
	private Image home = new Image(ImageBundle.INSTANCE.home());
	private HorizontalPanel logoPanel = new HorizontalPanel();
	TaPManager myMng = TaPManager.getInstance();
	private SimplePanel centerPage = new SimplePanel();
	private APage currentPage;

	
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



	public void showReceipt(final Receipt receiptData){
		waitingPage();
		ReceiptPage tempReceipt = new ReceiptPage(receiptData, true); 
		centerPage.setWidget(tempReceipt);
		
		
	}
	
	public void showReceiptsList(){
		waitingPage();
		currentPage=new ReceiptListPage();
		centerPage.setWidget(currentPage);	
	}


	public void showProduct(Product productData, Type type){
		waitingPage();
		currentPage = new ProductPage(productData,type);
		centerPage.setWidget(currentPage);
	}

	public void showShop(Shop shopData, Type type){
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
