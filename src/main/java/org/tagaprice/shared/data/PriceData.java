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

/**
 * 
 * TODO refactor this class
 */
public class PriceData  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Product _productData;
	private Shop _shopData;
	private Price _price;
	private Date _date;
	
	/**
	 * Default constructor needed for serialization
	 */
	public PriceData() { }
	

	/**
	 * Create a new {@link PriceData}
	 * @param product the {@link Product} to use
	 * @param shop the {@link Shop} to use
	 * @param p the actual {@link Price}
	 * @param date {@link Date} when this {@link Price} was up to date.
	 */
	public PriceData(Product product, Shop shop, Price p, Date date){
		this._productData=product;
		this._shopData=shop;
		this._price = p;
		this._date = date;
	}
	
	
	@Override
	public String getSerializeName() {
		return "PriceData";
	}

	
	/**
	 * @return the product of this {@link PriceData}
	 */
	public Product getProductData() {
		return _productData;
	}

	
	/**
	 * @param productData the product of this {@link PriceData} to set
	 */
	public void setProductData(Product productData) {
		this._productData = productData;
	}

	
	/**
	 * @return the shop this {@link PriceData} was up to date.
	 */
	public Shop getShopData() {
		return _shopData;
	}

	
	/**
	 * @param shopData the shop this {@link PriceData} was up to date.
	 */
	public void setShopData(Shop shopData) {
		this._shopData = shopData;
	}

	
	/**
	 * @return the price
	 */
	public Price getPrice() {
		return _price;
	}
	
	
	/**
	 * @param p the price
	 */
	public void setPrice(Price p) {
		this._price = p;
	}

	
	/**
	 * @return date this {@link PriceData} was up to date.
	 */
	public Date getDate() {
		return _date;
	}


	/**
	 * @param date date this {@link PriceData} was up to date.
	 */
	public void setDate(Date date) {
		this._date = date;
	}
}
