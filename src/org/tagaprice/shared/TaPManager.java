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
package org.tagaprice.shared;

import java.util.ArrayList;

import org.tagaprice.client.InfoBox;
import org.tagaprice.client.SearchWidget;
import org.tagaprice.client.UIManager;
import org.tagaprice.client.SearchWidget.Filter;

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
	 * @param text
	 * @param searchWidget
	 */
	public void search(String text, SearchWidget searchWidget);
	
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
	
	
	
	public ArrayList<Entity> searchShops(LatLngBounds bounds, SearchWidget sw);
	
}
