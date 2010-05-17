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
package org.tagaprice.client;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Contains all important information to represent a
 * receipt. 
 *
 */
public class ReceiptContainer implements Serializable {

	private int id;
	private String name;
	private Date date;
	private int bill; //in Cent
	private ShopPreviewContainer shopPreviewContainer;
	private ProductPreviewContainer[] productPreviewContainer;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param date
	 * @param bill
	 * @param shopPreviewContainer
	 * @param productPreviewContainer
	 */
	public ReceiptContainer(
			int id,
			String name,
			Date date,
			int bill,
			ShopPreviewContainer shopPreviewContainer,
			ProductPreviewContainer[] productPreviewContainer) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.bill = bill;
		this.shopPreviewContainer = shopPreviewContainer;
		this.productPreviewContainer = productPreviewContainer;
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
	public ShopPreviewContainer getShopPreviewContainer() {
		return shopPreviewContainer;
	}
	
	/**
	 * 
	 * @param shopPreviewContainer
	 */
	public void setShopPreviewContainer(ShopPreviewContainer shopPreviewContainer) {
		this.shopPreviewContainer = shopPreviewContainer;
	}
	
	/**
	 * 
	 * @return
	 */
	public ProductPreviewContainer[] getProductPreviewContainer() {
		return productPreviewContainer;
	}
	
	/**
	 * 
	 * @param productPreviewContainer
	 */
	public void setProductPreviewContainer(
			ProductPreviewContainer[] productPreviewContainer) {
		this.productPreviewContainer = productPreviewContainer;
	}
	
}
