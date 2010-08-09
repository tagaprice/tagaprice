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

import java.util.ArrayList;

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.PriceData;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.rpc.PriceHandler;
import org.tagaprice.shared.rpc.PriceHandlerAsync;
import org.tagaprice.shared.rpc.ProductHandler;
import org.tagaprice.shared.rpc.ProductHandlerAsync;
import org.tagaprice.shared.rpc.ReceiptHandler;
import org.tagaprice.shared.rpc.ReceiptHandlerAsync;
import org.tagaprice.shared.rpc.TypeHandler;
import org.tagaprice.shared.rpc.TypeHandlerAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * DAO Manger
 *
 */
public class TaPManagerImpl implements TaPManager {

	private static TaPManager TaPMng;	
	private static UIManager uiMng = new UIManager();
	private TypeHandlerAsync typeHandler = GWT.create(TypeHandler.class);
	private ProductHandlerAsync productHandler = GWT.create(ProductHandler.class);
	private ReceiptHandlerAsync receiptHandler = GWT.create(ReceiptHandler.class);
	private PriceHandlerAsync priceHandler = GWT.create(PriceHandler.class);


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
				//TODO Create a Split "=" parser
				if(historyToken[0].equals("receipt/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showReceiptPage(Long.parseLong(equalToken[1]));
				}else if(historyToken[0].equals("product/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showProductPage(Long.parseLong(equalToken[1]));					
				}else if(historyToken[0].equals("shop/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showShopPage(Long.parseLong(equalToken[1]));
				}else if(historyToken[0].equals("user/registration/new")){ 
					TaPMng.showUserRegistrationPage(null);
				}else{
					uiMng.showHome();
				}


			}
		});

	}


	public void showProductPage(final Long id){
		uiMng.waitingPage();

		TaPMng.getProduct(id, new AsyncCallback<ProductData>() {

			@Override
			public void onSuccess(final ProductData pResult) {

				TaPMng.getType(pResult.getTypeId(), new AsyncCallback<Type>() {

					@Override
					public void onSuccess(Type tResult) {
						uiMng.showProduct(pResult, tResult);
					}

					@Override
					public void onFailure(Throwable caught) {
						TaPMng.getInfoBox().showInfo("Fail: "+caught, BoxType.WARNINGBOX);
					}
				});

			}

			@Override
			public void onFailure(Throwable caught) {
				TaPMng.getInfoBox().showInfo("Fail: "+caught, BoxType.WARNINGBOX);

			}
		});	

		
	}
	
	public void showShopPage(final Long id){
		//Create Page
		uiMng.waitingPage();
		final ShopData shop = new ShopData(123, 2, "ACME Store", 3, null, 80, 25, new Address("Park Avenue 23", "New York", "USA"));
		SearchResult<PropertyData> propList = new SearchResult<PropertyData>();
		propList.add(new PropertyData("type", "Type", "drugstore", null));
		shop.setProperties(propList);
		
		TaPMng.getType(id, new AsyncCallback<Type>() {
			
			@Override
			public void onSuccess(Type tResult) {
				uiMng.showShop(shop,tResult);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				TaPMng.getInfoBox().showInfo("Fail: "+caught, BoxType.WARNINGBOX);
			}
		});

	}

	public void showReceiptPage(final Long id){
		uiMng.waitingPage();

		getReceipt(id, new AsyncCallback<ReceiptData>() {

			@Override
			public void onSuccess(ReceiptData result) {
				uiMng.showReceipt(result);				
			}

			@Override
			public void onFailure(Throwable caught) {
				TaPMng.getInfoBox().showInfo("Fail: "+caught, BoxType.WARNINGBOX);				
			}
		});
	}


	@Override
	public void getReceipt(Long id, AsyncCallback<ReceiptData> response) {		
		receiptHandler.get(id, response);		
	}

	@Override
	public void getProduct(Long id, AsyncCallback<ProductData> response) {
		productHandler.get(id, response);
	}

	@Override
	public void saveProduct(ProductData data,AsyncCallback<ProductData> response) {
		productHandler.save(data, response);
	}

	@Override
	public void getPrice(Long id, BoundingBox bbox, PriceMapType type, AsyncCallback<ArrayList<PriceData>> response){
		priceHandler.get(id, bbox, type, response);
	}

	@Override
	public ShopData getShop(Long id) {
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
			System.out.println("SaveRecei_superTypept-Error");
		}

	}


	public ArrayList<ShopData> searchShops(String userInput, SearchWidget sw) {
		// TODO Auto-generated method stub

			ArrayList<ShopData> tmps= new ArrayList<ShopData>();
			tmps.add(new ShopData(15, 3, "Billa Flossgasse", 1, "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")));
			tmps.add(new ShopData(12, 3, "Amazon.de", 1, "logo.png", 80, 3));
			tmps.add(new ShopData(15, 3, "Billa Flossgasse", 1, "logo.png", 80, 50, new Address(48.217883, 16.390475)));
			tmps.add(new ShopData(15, 3, "Spar Schonbrunn", 1, "logo.png", 20, 70, new Address(48.184516, 16.311865)));

		
		return tmps;
	}

	@Override
	public InfoBox getInfoBox() {
		return uiMng.getInfoBox();
	}

	@Override
	public void getType(long id, AsyncCallback<Type> response) {
		typeHandler.get(id,response);
	}
	
	@Override
	public void getTypeList(Type type, AsyncCallback<ArrayList<Type>> response){
		typeHandler.getTypeList(type, response);
	}


	@Override
	public ArrayList<ShopData> searchShops(LatLngBounds bounds, SearchWidget searchWidget){
		//TODO search for shops/products/both in the bounding box, set suggestions
		ArrayList<ShopData> tmp= new ArrayList<ShopData>();
		tmp.add(new ShopData(15, 4, "Billa Flossgasse", 1, "logo.png", 80, 50, new Address(48.217883, 16.390475)));
		tmp.add(new ShopData(12, 3, "Spar Schonbrunn", 1, "logo.png", 20, 70, new Address(48.184516, 16.311865)));

		return tmp;
	}
	
	@Override
	public ArrayList<ShopData> searchShops(LatLngBounds bounds,String searchString,  SearchWidget searchWidget){
		//TODO search for shops/products/both in the bounding box, set suggestions
		ArrayList<ShopData> tmp= new ArrayList<ShopData>();
		tmp.add(new ShopData(15, 8, "Billa Flossgasse", 2, "logo.png", 80, 50, new Address(48.217883, 16.390475)));
		tmp.add(new ShopData(14, 7, "Spar Schonbrunn", 2, "logo.png", 20, 70, new Address(48.184516, 16.311865)));

		return tmp;
	}


	public ArrayList<ProductData> searchProducts(String searchString, SearchWidget searchWidget){
		ArrayList<ProductData> tmp= new ArrayList<ProductData>();
		tmp.add(new ProductData(13l, 6, "Gouda Kaese", 2, 15l, 16l, "logo.png", 50, 50, new Price(1200, 23, 1, "€", 1), new Quantity(250, 23, 2, "g", 2)));
		return tmp;
	}
	
	public ShopData getShop(double lat, double lng){
		///TODO get shop
		return new ShopData(15, 5, "Spar Schonbrunn", 2, "logo.png", 20, 70, new Address(48.184516, 16.311865));
	}

	@Override
	public ArrayList<Entity> search(String text, SearchWidget searchWidget) {
		ArrayList<Entity> tmp= new ArrayList<Entity>();
		tmp.add(new ProductData(13l, 6, "Gouda Kaese", 2, 15l, 16l, "logo.png", 50, 50, new Price(1200, 23, 1, "€", 1), new Quantity(250, 23, 2, "g", 2)));
		tmp.add(new ShopData(15, 3, "Billa Flossgasse", 1, "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")));
		tmp.add(new ShopData(12, 3, "Amazon.de", 1, "logo.png", 80, 3));
		tmp.add(new ShopData(15, 3, "Billa Flossgasse", 1, "logo.png", 80, 50, new Address(48.217883, 16.390475)));
		tmp.add(new ShopData(15, 3, "Spar Schonbrunn", 1, "logo.png", 20, 70, new Address(48.184516, 16.311865)));


		return tmp;
		
		
	}

	@Override
	public void showUserRegistrationPage(String verificationCode) {
		uiMng.waitingPage();
		uiMng.showUserRegistrationPage(verificationCode);
		
		
	}


}
