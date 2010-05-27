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

import org.tagaprice.client.InfoBox;
import org.tagaprice.client.SearchWidget;
import org.tagaprice.client.UIManager;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;

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
	 *  
	 * @param id Unique Receipt Id (If Id=0 you get an empty Draft-Container with a new draft-id )
	 * @param draft Is receipt a draft.
	 * @return Returns a ReceiptContainer
	 */
	public ReceiptData getReceipt(Long id);
	
	/**
	 * Returns product by ID.
	 * @param id
	 * @return
	 */
	public void getProduct(Long id, AsyncCallback<ProductData> response);
	
	/**
	 * Returns shop by ID.
	 * @param id
	 * @return
	 */
	public ShopData getShop(Long id);
	
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
	
	
}
