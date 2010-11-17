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
 * Filename: PriceData.java
 * Date: 02.06.2010
*/
package org.tagaprice.shared.entities;

import java.util.Date;

import org.tagaprice.shared.ISerializable;

public class PriceData  implements ISerializable {

	private static final long serialVersionUID = 1L;
	
	private Product productData;
	private Shop shopData;
	private Price price;
	private Date date;
	
	public PriceData() {
	}
	
	public PriceData(Product productData){		
		this();
		this.productData=productData;
	}
	
	public PriceData(Product productData, Shop shopData, Price p, Date date){
		this(productData);
		this.shopData=shopData;
		this.price = p;
		this.date = date;
	}
	
	@Override
	public String getSerializeName() {
		return "PriceData";
	}

	public Product getProductData() {
		return productData;
	}

	public void setProductData(Product productData) {
		this.productData = productData;
	}

	public Shop getShopData() {
		return shopData;
	}

	public void setShopData(Shop shopData) {
		this.shopData = shopData;
	}

	public Price getPrice() {
		return price;
	}
	
	public void setPrice(Price p) {
		this.price = p;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
