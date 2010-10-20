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

import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
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
	private AddressPolling autoAddressing = new AddressPolling(60000);

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
					TaPMng.showReceiptPage(new ReceiptData(Long.parseLong(equalToken[1])));
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
	public void newShopPage(final String title) {	
		uiMng.waitingPage();
		System.out.println("in newShopPage");
		
		Geolocation geoLocation = Geolocation.getGeolocation();
		geoLocation.getCurrentPosition(new PositionCallback() {
			
			@Override
			public void onSuccess(Position position) {
				final Address address = new Address(position.getCoords().getLatitude(), 
						position.getCoords().getLongitude());
				
				Geocoder geocoder = new Geocoder();
				geocoder.getLocations(
						LatLng.newInstance(address.getLat(), address.getLng()), 
						new LocationCallback() {
							
							@Override
							public void onSuccess(JsArray<Placemark> locations) {
								address.setAddress(
										locations.get(0).getStreet(), 
										locations.get(0).getCity(), 
										new Country(
												locations.get(0).getCountry().toLowerCase(), 
												locations.get(0).getCountry(), 
												locations.get(0).getCountry()));
								TaPMng.setCurrentAddress(address);
								
								//Start opening shopPage
								String title2=title;
								if(title==null) 
									title2="Default Title"; //Change this to language
								ShopData sd = new ShopData(title2, 1, 1l, 0l, "logo.png", TaPMng.getCurrentAddress());
								
								uiMng.showShop(sd,new Type("root", 2, 1, null));
							}
							
							@Override
							public void onFailure(int statusCode) {
								System.out.println("can't locate Address");
							}
						});				
				
			}
			
			@Override
			public void onFailure(PositionError error) {
				// TODO Auto-generated method stub
				System.out.println("can't locate LatLng");
			}
		});
		
		
		
		
		
		
	}
	
	/**
	 * Starts Receipt Page
	 * @param id
	 */
	public void showReceiptPage(ReceiptData data){
		uiMng.waitingPage();

		try {
			HandlerManager.getReceiptHandler().get(data, new AsyncCallback<ReceiptData>() {

				@Override
				public void onSuccess(ReceiptData result) {
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
		return autoAddressing.getCurrentAddress();
	}
	
	/**
	 * Set current position
	 * @param address
	 */
	public void setCurrentAddress(Address address){
		autoAddressing.setCurrentAddress(address);
	}
	
}
