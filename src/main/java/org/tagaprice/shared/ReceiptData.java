/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: ReceiptContainer.java
 * Date: 16.05.2010
*/
package org.tagaprice.shared;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * Contains all important information to represent a receipt. 
 *
 */
public class ReceiptData extends Entity {
	private static final long serialVersionUID = 1L;

	private boolean draft;
	private Date date;
	private int bill; //in Cent
	private ShopData shop;
	private ArrayList<ProductData> productData = new ArrayList<ProductData>();
	
	/**
	 * default constructor (needed for serialization)
	 */
	public ReceiptData() {
		super();
	}
	
	/**
	 * constructor used for querying the current revision of a Receipt from the database (using ReceiptDAO)
	 * @param id Receipt ID
	 */
	public ReceiptData(long id) {
		super(id);
	}
	
	/**
	 * constructor used for querying a specific revision of a Receipt from the database (using ReceiptDAO)
	 * @param id Receipt ID
	 * @param rev requested revision
	 */
	public ReceiptData(long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * constructor used to store a new Receipt into the database (using ReceiptDAO) 
	 * @param title descriptive receipt name (should not be empty)
	 * @param localeId receipt locale
	 * @param creatorId receipt's creator
	 * @param date receipt date
	 * @param bill TODO what's that for?
	 * @param shop Shop ID
	 * @param products
	 * @param draft
	 */
	public ReceiptData(String title, int localeId, long creatorId, Date date, int bill, ShopData shop, ArrayList<ProductData> products, boolean draft) {
		super(title, localeId, creatorId);
		this.date = date;
		this.bill = bill;
		this.shop = shop;
		this.productData = products;
	}
	
	public ReceiptData(long id, int rev, String title, long creatorId, Date date, int bill, ShopData shop, ArrayList<ProductData> products, boolean draft) {
		super(id, rev, title, creatorId);
		this.date = date;
		this.bill = bill;
		this.shop = shop;
		this.productData = products;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getDraft() {
		return draft;
	}
	
	/**
	 * 
	 * @param draft
	 */
	public void setDraft(boolean draft) {
		this.draft = draft;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBill() {
		return bill;
	}
	
	/**
	 * 
	 * @param bill
	 */
	public void setBill(int bill) {
		this.bill = bill;
	}
	
	/**
	 * 
	 * @return
	 */
	public ShopData getShop() {
		return shop;
	}
	
	/**
	 * 
	 * @param shop Shop
	 */
	public void setShop(ShopData shop) {
		this.shop = shop;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<ProductData> getProductData() {
		return productData;
	}
	
	/**
	 * 
	 * @param productPreviewData
	 */
	public void setProductData(
			ArrayList<ProductData> productData) {
		this.productData = productData;
	}

	@Override
	public String getSerializeName() {
		return "receipt";
	}
	
	
	public long getTotalPrice(){
		
		long totalPrice = 0;
		for(ProductData pd: productData){
			totalPrice= totalPrice+pd.getAvgPrice().getPrice();
		}
		
		return totalPrice;
	}
	// TODO implement missing ReceiptData.equals()

	@Override
	public <T extends Entity> T newRevision() {
		// TODO Auto-generated method stub
		return null;
	}
}
