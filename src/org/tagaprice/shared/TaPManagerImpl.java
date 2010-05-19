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
 * Filename: TaPManager.java
 * Date: 18.05.2010
*/
package org.tagaprice.shared;

import java.util.ArrayList;
import java.util.Date;
import org.tagaprice.client.InfoBox;
import org.tagaprice.client.SearchWidget;
import org.tagaprice.client.UIManager;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;


/**
 * DAO Manger
 *
 */
public class TaPManagerImpl implements TaPManager {
	
	private static TaPManager TaPMng;	
	private static UIManager uiMng = new UIManager();
	
	public static TaPManager getInstance(){
		if(TaPMng==null){
			TaPMng=new TaPManagerImpl();
			init();
		}
		return TaPMng;
	}
	
	private static void init(){
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String[] historyToken = event.getValue().split("&");
				
				
				if(historyToken[0].equals("receipt/edit")){
					String[] equalToken = historyToken[1].split("=");
					ReceiptData emptyData = TaPMng.getReceipt(Integer.parseInt(equalToken[1]));
					uiMng.showReceipt(emptyData);
				}else if(historyToken[0].equals("product/get")){
					System.out.println("show product/get");
					String[] equalToken = historyToken[1].split("=");
					//TaPMng.getProduct(Integer.parseInt(equalToken[1]));
					uiMng.showProduct(TaPMng.getProduct(Integer.parseInt(equalToken[1])));
					
				} else if(historyToken[0].equals("shop/get")){
					System.out.println("show shop/get");
				} else{
					uiMng.showHome();
				}
				
				
			}
		});
		
	}
	
	
	
	@Override
	public ReceiptData getReceipt(int id) {
		// TODO Auto-generated method stub
		//Server Communication
		
		ReceiptData receiptContainer;
		
		if(id==0){
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			receiptContainer = new ReceiptData(
					15, 
					true,
					"Default title", 
					new Date(), 
					0, 
					null, 
					myProducts);
		}else if(id==15) {
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			myProducts.add(new ProductData(152, 15, 16, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", new Quantity(260, 23, "g"),true));
			myProducts.add(new ProductData(120, 15, 16, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, 98, "€", new Quantity(1, 24, "l"),false));
			myProducts.add(new ProductData(12, 15, 16, "Coca Cola 2L", "logo.png", 50, 100, 230, "€", new Quantity(2, 25, "l"),true));
			
			receiptContainer = new ReceiptData(
					15, 
					true,
					"Ostern war teuer", 
					new Date(), 
					0, 
					new ShopData(15, "Hofer Taborstrasse", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0), 
					myProducts);
		}else{
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			myProducts.add(new ProductData(152, 15, 16, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", new Quantity(260, 23, "g"),true));
			myProducts.add(new ProductData(120, 15, 16, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, 98, "€", new Quantity(1, 24, "l"),false));
			
			receiptContainer = new ReceiptData(
					18, 
					false,
					"Weihnachts einkauf", 
					new Date(), 
					0, 
					new ShopData(15, "Billa Flossgasse", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0), 
					myProducts);
		}
		
		
		
		return receiptContainer;
	}

	@Override
	public ProductData getProduct(int id) {
		// TODO Auto-generated method stub
		return new ProductData(152, 15, 16, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", new Quantity(260, 23, "g"),true);
	}

	@Override
	public ShopData getShop(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UIManager getUIManager() {
		return uiMng;
	}


	@Override
	public void saveReceipt(ReceiptData receiptContainer) {
		// TODO Auto-generated method stub
		if(receiptContainer.getId()>0 && receiptContainer.getDraft()==true){
			System.out.println("saveDraft");
		}else if(receiptContainer.getId()>0 && receiptContainer.getDraft()==false){
			System.out.println("saveReceipt or change Draft to Receipt");
		}else{
			System.out.println("SaveReceipt-Error");
		}
		
	}
	
	
	public void search(String userInput, SearchWidget sw) {
		// TODO Auto-generated method stub
		// send to server
		
		ArrayList<Entity> tmp= new ArrayList<Entity>();
			tmp.add(new ShopData(15, "Billa Flossgasse", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0));
			tmp.add(new ShopData(12, "Amazon.de", "logo.png", 80, 3));
			tmp.add(new ProductData(13, 15, 16, "Gouda Kaese", "logo.png", 50, 50, 1200, "€", new Quantity(250, 23, "g")));
		
		sw.setSuggestions(tmp);
		
	}

	@Override
	public InfoBox getInfoBox() {
		return uiMng.getInfoBox();
	}

	
	
	

	
}
