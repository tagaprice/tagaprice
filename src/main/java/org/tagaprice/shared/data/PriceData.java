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
package org.tagaprice.shared.data;

import java.util.Date;

import org.tagaprice.shared.Serializable;

public class PriceData  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ProductData productData;
	private ShopData shopData;
	private Price price;
	private Date date;
	
	public PriceData() {
	}
	
	public PriceData(ProductData productData){		
		this();
		this.productData=productData;
	}
	
	public PriceData(ProductData productData, ShopData shopData, Price p, Date date){
		this(productData);
		this.shopData=shopData;
		this.price = p;
		this.date = date;
	}
	
	@Override
	public String getSerializeName() {
		return "PriceData";
	}

	public ProductData getProductData() {
		return productData;
	}

	public void setProductData(ProductData productData) {
		this.productData = productData;
	}

	public ShopData getShopData() {
		return shopData;
	}

	public void setShopData(ShopData shopData) {
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
