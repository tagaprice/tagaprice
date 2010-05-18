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
public class ReceiptData implements Entity {

	private int id;
	private String name;
	private Date date;
	private int bill; //in Cent
	private ShopData shopData;
	private ArrayList<ProductData> productData = new ArrayList<ProductData>();
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param date
	 * @param bill
	 * @param shopData
	 * @param productData
	 */
	public ReceiptData(
			int id,
			String name,
			Date date,
			int bill,
			ShopData shopData,
			ArrayList<ProductData> productData) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.bill = bill;
		this.shopData = shopData;
		this.productData = productData;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	public ShopData getShopData() {
		return shopData;
	}
	
	/**
	 * 
	 * @param shopPreview
	 */
	public void setShopData(ShopData shopData) {
		this.shopData = shopData;
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
	
}
