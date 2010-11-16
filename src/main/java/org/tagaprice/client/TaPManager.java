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

import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.shared.data.Address;
import org.tagaprice.shared.data.Country;
import org.tagaprice.shared.data.Product;
import org.tagaprice.shared.data.Receipt;
import org.tagaprice.shared.data.Shop;
import org.tagaprice.shared.data.Type;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * DAO Manger
 *
 */
public class TaPManager {

	private static TaPManager TaPMng;	
	private static UIManager uiMng = new UIManager();
	private Address address = new Address("Flossgasse","vienna",new Country("at", "Austria", "Österreich"), 48.21960,16.37205);
	private Geolocation currentLocation;
	
	
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
					TaPMng.showReceiptPage(new Receipt(Long.parseLong(equalToken[1])));
				}else if(historyToken[0].equals("receipt/list")){
					uiMng.showReceiptsList();
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
			RPCHandlerManager.getProductHandler().get(id, new AsyncCallback<Product>() {

				@Override
				public void onSuccess(final Product pResult) {

					
					//TODO set new Type("root", 9, 1, null) to new Type(1)
					RPCHandlerManager.getTypeHandler().get(new Type(pResult.getTypeId()), new AsyncCallback<Type>() {

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
		
		RPCHandlerManager.getTypeHandler().get(null, new AsyncCallback<Type>() {

			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail product: "+caught, BoxType.WARNINGBOX);				
			}

			@Override
			public void onSuccess(Type result) {
				Product pd3 = new Product("Defaulf Procut Title" , 1, 1l, 2l, result.getId(), "logo.png", null);
				uiMng.showProduct(pd3, result);	
			}
			
		});
		
		
			
		
	}
	
	/**
	 * Starts Shop Page with id
	 * @param id
	 */
	public void showShopPage(final Long id){
		//Create Page
		uiMng.waitingPage();
				
		RPCHandlerManager.getShopHandler().get(id, new AsyncCallback<Shop>() {
			
			//TODO set new Type("root", 9, 1, null) to new Type(1)
			@Override
			public void onSuccess(final Shop result) {
				RPCHandlerManager.getTypeHandler().get(new Type(result.getTypeId()), new AsyncCallback<Type>() {

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
	public void newShopPage(final String title) {	
		uiMng.waitingPage();
		
		
		RPCHandlerManager.getTypeHandler().get(null, new AsyncCallback<Type>() {

			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail shop: "+caught, BoxType.WARNINGBOX);
			}

			@Override
			public void onSuccess(Type result) {
				Shop sd = new Shop("Default Shop Title", 1, 1l, null, "logo.png", TaPMng.getCurrentAddress());
				
				uiMng.showShop(sd,result);
				
			}
			
		});
		
			
		
	}
	
	/**
	 * Starts Receipt Page
	 * @param id
	 */
	public void showReceiptPage(Receipt data){
		uiMng.waitingPage();

		try {
			RPCHandlerManager.getReceiptHandler().get(data, new AsyncCallback<Receipt>() {

				@Override
				public void onSuccess(Receipt result) {
					uiMng.showReceipt(result);				
				}

				@Override
				public void onFailure(Throwable caught) {
					uiMng.showInfo("Fail: "+caught, BoxType.WARNINGBOX);				
				}
			});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvalidLoginException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Returns the UIManager
	 * @return
	 */
	public UIManager getUIManager() {
		return uiMng;
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

	
	/**
	 * 
	 * @return Returns SessionId. Else NULL
	 */
	public String isLoggedIn(){
		if(Cookies.getCookie("TaPSId")==null) return null;
		
		return Cookies.getCookie("TaPSId");
	}


	/**
	 * Returns the current Address and Position. Is is possible to set a static Address. 
	 * @return
	 */
	public Address getCurrentAddress(){
		return address;
	}
	
	
	
	/**
	 * Set current position by user setting. 
	 * @param address
	 */
	public void refeshCurrentAdress(){
		
		
		if(currentLocation==null)currentLocation = Geolocation.getGeolocation();
		
		currentLocation.getCurrentPosition(new PositionCallback() {
			
			@Override
			public void onSuccess(Position position) {
				address.setCoordinates(
						position.getCoords().getLatitude(), 
						position.getCoords().getLongitude());
			}
			
			@Override
			public void onFailure(PositionError error) {
				// TODO Auto-generated method stub
				System.out.println("error");
			}
		});
		
		
		uiMng.setAddress(address);
	}
	
}
