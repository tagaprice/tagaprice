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

import java.util.Date;



/**
 * 
 * Contains all important information to represent a receipt. 
 *
 */
public class ReceiptContainer implements EntityContainer {

	private int id;
	private String name;
	private Date date;
	private int bill; //in Cent
	private ShopContainer shopContainer;
	private ProductContainer[] productContainer;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param date
	 * @param bill
	 * @param shopContainer
	 * @param productContainer
	 */
	public ReceiptContainer(
			int id,
			String name,
			Date date,
			int bill,
			ShopContainer shopContainer,
			ProductContainer[] productContainer) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.bill = bill;
		this.shopContainer = shopContainer;
		this.productContainer = productContainer;
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
	public ShopContainer getShopContainer() {
		return shopContainer;
	}
	
	/**
	 * 
	 * @param shopPreviewContainer
	 */
	public void setShopPreviewContainer(ShopContainer shopContainer) {
		this.shopContainer = shopContainer;
	}
	
	/**
	 * 
	 * @return
	 */
	public ProductContainer[] getProductContainer() {
		return productContainer;
	}
	
	/**
	 * 
	 * @param productPreviewContainer
	 */
	public void setProductContainer(
			ProductContainer[] productContainer) {
		this.productContainer = productContainer;
	}
	
}
