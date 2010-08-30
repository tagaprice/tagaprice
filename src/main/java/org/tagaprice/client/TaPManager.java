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
package org.tagaprice.client;

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * DAO Manger
 *
 */
public class TaPManager {

	private static TaPManager TaPMng;	
	private static UIManager uiMng = new UIManager();
	

	public static TaPManager getInstance(){
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
				//TODO Create a Split "=" parser
				if(historyToken[0].equals("receipt/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showReceiptPage(Long.parseLong(equalToken[1]));
				}else if(historyToken[0].equals("product/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showProductPage(Long.parseLong(equalToken[1]));					
				}else if(historyToken[0].equals("product/new")){
					//String[] equalToken = historyToken[1].split("=");
					//TaPMng.showProductPage(Long.parseLong(equalToken[1]));
					//TODO Get Product name from GET parameters 
					
					TaPMng.newProductPage(null);
				}else if(historyToken[0].equals("shop/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showShopPage(Long.parseLong(equalToken[1]));
				}else if(historyToken[0].equals("shop/new")){
					
					//TODO get title parameter from GET parameters 
					TaPMng.newShopPage(null);
					
				}else if(historyToken[0].equals("user/registration/new")){ 
					TaPMng.showUserRegistrationPage();
				}else if(historyToken[0].equals("user/registration/confirm")){ 
										
					if(historyToken.length>1){	
						String[] equalToken = historyToken[1].split("=");
						if(equalToken[0].equals("confirm") && !equalToken[1].isEmpty())
							uiMng.showConfirmRegistartionPage(equalToken[1]);
					}else{
						uiMng.showConfirmRegistartionPage(null);
					}
					
				}else if(historyToken[0].equals("user/login") ){ 
					uiMng.showUserLogin(false);
				}else if(historyToken[0].equals("user/logout")){ 
					uiMng.showUserLogin(true);
				}else{
					uiMng.showHome();
				}


			}
		});

	}

	/**
	 * Starts Product Page with id
	 * @param id
	 */
	public void showProductPage(final Long id){
		uiMng.waitingPage();

		try {
			HandlerManager.getProductHandler().get(id, new AsyncCallback<ProductData>() {

				@Override
				public void onSuccess(final ProductData pResult) {

					HandlerManager.getTypeHandler().get(pResult.getTypeId(), new AsyncCallback<Type>() {

						@Override
						public void onSuccess(Type tResult) {
							uiMng.showProduct(pResult, tResult);
						}

						@Override
						public void onFailure(Throwable caught) {
							uiMng.showInfo("Fail type: "+caught, BoxType.WARNINGBOX);
						}
					});

				}

				@Override
				public void onFailure(Throwable caught) {
					uiMng.showInfo("Fail product: "+caught, BoxType.WARNINGBOX);

				}
			});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	

	/**
	 * Creates empty productPage
	 * @param title
	 */
	public void newProductPage(String title) {
		uiMng.waitingPage();
		
		if(title==null) title="Default Title "; //Change this to language
		ProductData pd3 = new ProductData(title , 1, 1l, 2l, 0l, "logo.png", null);

		uiMng.showProduct(pd3, new Type("root", 2, 1, null));		
		
	}
	
	/**
	 * Starts Shop Page with id
	 * @param id
	 */
	public void showShopPage(final Long id){
		//Create Page
		uiMng.waitingPage();
				
		HandlerManager.getShopHandler().get(id, new AsyncCallback<ShopData>() {
			
			@Override
			public void onSuccess(final ShopData result) {
				HandlerManager.getTypeHandler().get(result.getTypeId(), new AsyncCallback<Type>() {

					@Override
					public void onSuccess(Type tResult) {
						uiMng.showShop(result, tResult);
					}

					@Override
					public void onFailure(Throwable caught) {
						uiMng.showInfo("Fail type: "+caught, BoxType.WARNINGBOX);
					}
				});
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail shop: "+caught, BoxType.WARNINGBOX);
				
			}
		});

	}
	
	
	/**
	 * Creates empty shopPage. 
	 * @param title
	 */
	public void newShopPage(String title) {	
		uiMng.waitingPage();
		
		if(title==null) title="Default Title"; //Change this to language
		ShopData sd = new ShopData(title, 1, 1l, 0l, "logo.png", new Address("Park Avenue 23", "New York", new Country("us", "USA", null)));
		
		uiMng.showShop(sd,new Type("root", 2, 1, null));
		
		
	}
	
	/**
	 * Starts Receipt Page
	 * @param id
	 */
	public void showReceiptPage(final Long id){
		uiMng.waitingPage();

		HandlerManager.getReceiptHandler().get(id, new AsyncCallback<ReceiptData>() {

			@Override
			public void onSuccess(ReceiptData result) {
				uiMng.showReceipt(result);				
			}

			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail: "+caught, BoxType.WARNINGBOX);				
			}
		});
	}
	

	/**
	 * Returns the UIManager
	 * @return
	 */
	public UIManager getUIManager() {
		return uiMng;
	}


	/**
	 * 
	 * @param receiptContainer
	 * @param draft
	 */
	public void saveReceipt(ReceiptData receiptContainer) {
		if(receiptContainer.getId()>0 && receiptContainer.getDraft()==true){
			System.out.println("saveDraft");
		}else if(receiptContainer.getId()>0 && receiptContainer.getDraft()==false){
			System.out.println("saveReceipt or change Draft to Receipt");
		}else{
			System.out.println("SaveRecei_superTypept-Error");
		}

	}



	/**
	 * Start a new user Registration
	 * @param varificationCode If not null user has just being verified.
	 */
	public void showUserRegistrationPage() {
		uiMng.showUserRegistrationPage();		
	}
	
	/**
	 * 
	 */
	public void showUserLogin() {
		//uiMng.showUserLogin();		
	}


	



}
