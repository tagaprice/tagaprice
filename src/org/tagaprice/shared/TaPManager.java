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
				String[] historyToken = event.getValue().split(";");
				String pageToken[] = historyToken[0].split("/");
				
				System.out.println("events: "+event.getValue());
				
				if(pageToken[0].equals("home") || event.getValue().equals(" ")){
					System.out.println("home");
					uiMng.home();
				}else if(pageToken[0].equals("receipt")){
					if(pageToken[1].equals("new")){
						System.out.println("new");
						uiMng.saveReceipt(TaPMng.getReceipt(0, true), true);
					}
					
				}
				
				
			}
		});
		
	}
	
	
	
	@Override
	public ReceiptContainer getReceipt(int id, boolean draft) {
		// TODO Auto-generated method stub
		
		ArrayList<ProductContainer> myProducts = new ArrayList<ProductContainer>();
		
		ReceiptContainer receiptContainer = new ReceiptContainer(
				15, 
				"Default title", 
				new Date(), 
				0, 
				null, 
				myProducts);
		return receiptContainer;
	}

	@Override
	public ProductContainer getProduct(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShopContainer getShop(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UIManager getUIManager() {
		return uiMng;
	}


	@Override
	public void saveReceipt(ReceiptContainer receiptContainer, boolean draft) {
		// TODO Auto-generated method stub
		
		
		//only a test
		if(draft){
			uiMng.refreshDraft();
		}else{
			uiMng.refreshSave();
			History.newItem("home;");
		}
		
	}


	

	
}
