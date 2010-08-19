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
import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.Entity;
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
import org.tagaprice.shared.rpc.SearchHandler;
import org.tagaprice.shared.rpc.SearchHandlerAsync;
import org.tagaprice.shared.rpc.ShopHandler;
import org.tagaprice.shared.rpc.ShopHandlerAsync;
import org.tagaprice.shared.rpc.TypeHandler;
import org.tagaprice.shared.rpc.TypeHandlerAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
	private SearchHandlerAsync searchHandler = GWT.create(SearchHandler.class);
	private ShopHandlerAsync shopHandler = GWT.create(ShopHandler.class);


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
						uiMng.showInfo("Fail type: "+caught, BoxType.WARNINGBOX);
					}
				});

			}

			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail product: "+caught, BoxType.WARNINGBOX);

			}
		});	
		
	}
	


	@Override
	public void newProductPage(String title) {
		uiMng.waitingPage();
		
		if(title==null) title="Default Title "; //Change this to language
		ProductData pd3 = new ProductData(title , 1, 1l, 2l, 20l, "logo.png", null);

		uiMng.showProduct(pd3, new Type("root", 2, 1, null));		
		
	}
	
	public void showShopPage(final Long id){
		//Create Page
		uiMng.waitingPage();
				
		
		TaPMng.getShop(id, new AsyncCallback<ShopData>() {
			
			@Override
			public void onSuccess(final ShopData result) {
				TaPMng.getType(result.getTypeId(), new AsyncCallback<Type>() {

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
	
	
	@Override
	public void newShopPage(String title) {	
		uiMng.waitingPage();
		
		if(title==null) title="Default Title"; //Change this to language
		ShopData sd = new ShopData(title, 1, 1l, 20l, "logo.png", new Address("Park Avenue 23", "New York", new Country("us", "USA", null)));
		
		uiMng.showShop(sd,new Type("root", 2, 1, null));
		
		
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
				uiMng.showInfo("Fail: "+caught, BoxType.WARNINGBOX);				
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
	public void getShop(Long id, AsyncCallback<ShopData> response) {
		shopHandler.get(id, response);		
	}
	
	@Override
	public void saveShop(ShopData data, AsyncCallback<ShopData> response) {
		shopHandler.save(data, response);
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




	@Override
	public void getType(long id, AsyncCallback<Type> response) {
		typeHandler.get(id,response);
	}
	
	@Override
	public void getTypeList(Type type, AsyncCallback<ArrayList<Type>> response){
		typeHandler.getTypeList(type, response);
	}

	

	@Override
	public void showUserRegistrationPage(String verificationCode) {
		uiMng.waitingPage();
		uiMng.showUserRegistrationPage(verificationCode);
		
		
	}

	@Override
	public void search(String sText, SearchType searchType, AsyncCallback<ArrayList<Entity>> callback) {
		searchHandler.search(sText, searchType, callback);
		
	}

	@Override
	public void search(String sText, SearchType searchType, BoundingBox bbox,
			AsyncCallback<ArrayList<Entity>> callback) {
		searchHandler.search(sText, searchType, bbox, callback);
		
	}

	@Override
	public void search(String sText, ShopData shopData,
			AsyncCallback<ArrayList<Entity>> callback) {
		searchHandler.search(sText, shopData, callback);
		
	}

	



}
