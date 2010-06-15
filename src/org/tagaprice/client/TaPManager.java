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
 * Filename: TaPManagerImpl.java
 * Date: 18.05.2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.PriceData;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;

import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface for the DAO Manager
 *
 */
public interface TaPManager {
	
	/**
	 * Starts Product Page with id
	 * @param id
	 */
	public void showProductPage(Long id);
	
	/**
	 * Starts Receipt Page
	 * @param id
	 */
	public void showReceiptPage(final Long id);
	
	
	/**
	 * Starts Shop Page with id
	 * @param id
	 */
	public void showShopPage(Long id);
	
	/**
	 *  
	 * @param id Unique Receipt Id (If Id=0 you get an empty Draft-Container with a new draft-id )
	 * @param draft Is receipt a draft.
	 * @return Returns a ReceiptContainer
	 */
	public void getReceipt(Long id, AsyncCallback<ReceiptData> response);
	
	/**
	 * Returns product by ID.
	 * @param id
	 * @return
	 */
	public void getProduct(Long id, AsyncCallback<ProductData> response);
	
	/**
	 * Save, create or update a product.
	 * @param data
	 * @param response
	 * @return
	 */
	public void saveProduct(ProductData data, AsyncCallback<ProductData> response); 
	
	/**
	 * Get price by Shop, Product, ProductGroup, ShopGroup
	 * @param id
	 * @param bbox
	 * @param type
	 * @param response
	 */
	public void getPrice(Long id, BoundingBox bbox, PriceMapType type, AsyncCallback<ArrayList<PriceData>> response);
	
	/**
	 * Returns shop by ID.
	 * @param id
	 * @return
	 */
	public ShopData getShop(Long id);
	
	
	/**
	 * Returns shop by location
	 * @param lat Latitude
	 * @param lng Longitude
	 * @return
	 */
	public ShopData getShop(double lat, double lng);
	
	/**
	 * 
	 * @param receiptContainer
	 * @param draft
	 */
	public void saveReceipt(ReceiptData receiptData);
	
	/**
	 * Returns the UIManager
	 * @return
	 */
	public UIManager getUIManager();

		
	/**
	 * 
	 * @return
	 */
	public InfoBox getInfoBox();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public void getType(long id, AsyncCallback<Type> response);
	
	public ArrayList<ShopData> searchShops(String userInput, SearchWidget sw);
	
	public ArrayList<ShopData> searchShops(LatLngBounds bounds,String searchString,  SearchWidget searchWidget);
	
	public ArrayList<ProductData> searchProducts(String searchString, SearchWidget searchWidget);

	public ArrayList<ShopData> searchShops(LatLngBounds bounds, SearchWidget searchWidget);

	public ArrayList<Entity> search(String text, SearchWidget searchWidget);
	
}
