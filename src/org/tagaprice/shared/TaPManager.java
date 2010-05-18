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

import org.tagaprice.client.UIManager;
import org.tagaprice.client.UIManagerImpl;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;


/**
 * DAO Manger
 *
 */
public class TaPManager implements TaPManagerImpl {
	
	private static TaPManagerImpl TaPMng;	
	private static UIManager uiMng = new UIManager();
	
	public static TaPManagerImpl getInstance(){
		if(TaPMng==null){
			TaPMng=new TaPManager();
			init();
		}
		return TaPMng;
	}
	
	private static void init(){
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String[] historyToken = event.getValue().split("&");
				
				System.out.println("events: "+event.getValue());
				
				if(historyToken[0].equals("home") || event.getValue().equals(" ")){
					System.out.println("home");
					uiMng.home();
				}else if(historyToken[0].equals("receipt/edit")){
					
					System.out.println("edit"); //GWT.println(nutzten);
					
					String[] equalToken = historyToken[1].split("=");
					
					
					ReceiptData emptyData = TaPMng.getReceipt(Integer.parseInt(equalToken[1]), true);
					
					uiMng.editReceipt(emptyData, true);
					
					
				}
				
				
			}
		});
		
	}
	
	
	
	@Override
	public ReceiptData getReceipt(int id, boolean draft) {
		// TODO Auto-generated method stub
		System.out.println("getReceipt: id: "+id+", draft: "+draft);
		
		ReceiptData receiptContainer;
		
		if(draft==true && id==0){
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			receiptContainer = new ReceiptData(
					15, 
					true,
					"Default title", 
					new Date(), 
					0, 
					null, 
					myProducts);
		}else if(draft==true && id!=0){
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			myProducts.add(new ProductData(152, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", "260", "g",true));
			myProducts.add(new ProductData(120, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, 98, "€", "1", "L",false));
			myProducts.add(new ProductData(12, "Coca Cola 2L", "logo.png", 50, 100, 230, "€", "2", "L",true));
			
			receiptContainer = new ReceiptData(
					id, 
					true,
					"Super geile Weihnachten", 
					new Date(), 
					0, 
					new ShopData(15, "Billa Schwedenplatz", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0), 
					myProducts);
		}else {
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			myProducts.add(new ProductData(152, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", "260", "g",true));
			myProducts.add(new ProductData(120, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, 98, "€", "1", "L",false));
			myProducts.add(new ProductData(12, "Coca Cola 2L", "logo.png", 50, 100, 230, "€", "2", "L",true));
			
			receiptContainer = new ReceiptData(
					id, 
					false,
					"Ostern war teuer", 
					new Date(), 
					0, 
					new ShopData(15, "Hofer Taborstrasse", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0), 
					myProducts);
		}
		
		
		
		return receiptContainer;
	}

	@Override
	public ProductData getProduct(int id) {
		// TODO Auto-generated method stub
		return null;
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
		System.out.println("Saved receipt or draft");
		
	}


	

	
}
