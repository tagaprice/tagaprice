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

import org.tagaprice.client.UIManager;

/**
 * Interface for the DAO Manager
 *
 */
public interface TaPManager {

	/**
	 *  
	 * @param id Unique Receipt Id (If Id=0 you get an empty Draft-Container with a new draft-id )
	 * @param draft Is receipt a draft.
	 * @return Returns a ReceiptContainer
	 */
	public ReceiptData getReceipt(int id);
	
	/**
	 * Returns product by ID.
	 * @param id
	 * @return
	 */
	public ProductData getProduct(int id);
	
	/**
	 * Returns shop by ID.
	 * @param id
	 * @return
	 */
	public ShopData getShop(int id);
	
	/**
	 * 
	 * @param receiptContainer
	 * @param draft
	 */
	public void editReceipt(ReceiptData receiptData);
	
	/**
	 * Returns the UIManager
	 * @return
	 */
	public UIManager getUIManager();
}
