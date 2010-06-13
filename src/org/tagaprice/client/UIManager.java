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
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.TaPManagerImpl;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.Type;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class UIManager extends Composite {

	DockPanel myDock = new DockPanel();
	Label newReceipt = new Label("Rechnung eintrage");
	Label oldReceipt = new Label("Haushaltsbuch");
	Label newDraft = new Label("Drafts(0)");
	Label logo = new Label("LOGO");
	HorizontalPanel menu = new HorizontalPanel();
	HorizontalPanel logoPanel = new HorizontalPanel();
	TaPManager myMng = TaPManagerImpl.getInstance();
	TitlePanel myTitlePan = new TitlePanel("Home", new Label("HomeSeite"), TitlePanel.Level.H1);
	///TODO 
//	SearchWidget sw= new SearchWidget(Filter.ANY);
	InfoBox infoBox = new InfoBox();

	public UIManager() {
		initWidget(myDock);
		init();
	}

	private void init(){
		myDock.setWidth("400px");



		//logo
		myDock.add(logoPanel, DockPanel.NORTH);
		logoPanel.setWidth("100%");
		logoPanel.add(logo);
	//	logoPanel.add(sw);
		logo.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("home/");		
			}
		});

		//menu
		myDock.add(menu, DockPanel.NORTH);		
		menu.setWidth("100%");
		menu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		menu.setStyleName("UIManager-Menu");
		menu.add(newReceipt);
		menu.add(oldReceipt);
		menu.add(newDraft);

		newReceipt.setStyleName("UIManager-Menu-Item");
		oldReceipt.setStyleName("UIManager-Menu-Item");
		newDraft.setStyleName("UIManager-Menu-Item");

		//InfoBox
		myDock.add(infoBox, DockPanel.NORTH);

		//Center
		myDock.add(myTitlePan, DockPanel.CENTER);


		newReceipt.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//TODO RPC Change
				myMng.getReceipt(0l, new AsyncCallback<ReceiptData>() {

					@Override
					public void onSuccess(ReceiptData tResult) {
						History.newItem("receipt/get&id="+tResult.getId());
					}

					@Override
					public void onFailure(Throwable caught) {
						myMng.getInfoBox().showInfo("Fail: "+caught, BoxType.WARNINGBOX);
					}
				});
			}
		});


	}

	/**
	 * Only a test method. 
	 */
	public void refreshDraft(){
		newDraft.setText("Draft(1)");
	}

	/**
	 * Only a test method. 
	 */
	public void refreshSave(){
		newDraft.setText("Draft(0)");
		newReceipt.setText("Receipt(1)");
	}


	public void showReceipt(ReceiptData receiptData){
		if(receiptData==null){
			ReceiptWidget tempReceipt = new ReceiptWidget(); 
			myTitlePan.setTitleWidget("Kassazettel eintragen", tempReceipt);
		}
		else {
			ReceiptWidget tempReceipt = new ReceiptWidget(receiptData, true); 
			myTitlePan.setTitleWidget("Kassazettel eintragen",tempReceipt);
		}
	}


	public void showProduct(ProductData productData, Type type){
		ProductPage tempProduct = new ProductPage(productData,type);
		myTitlePan.setTitleWidget(productData.getName(), tempProduct);
	}

	public void showShop(ShopData shopData, Type type){
		ShopPage tempShop = new ShopPage(shopData, type);
		myTitlePan.setTitleWidget(shopData.getName(), tempShop);
	}


	public void showHome(){
		myTitlePan.setTitleWidget(
				"Home", 
				new HTML("home"));
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
