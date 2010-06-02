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
package org.tagaprice.shared;

public class PriceData  implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProductData productData;
	private ShopData shopData;
	
	public PriceData() {
	}
	
	public PriceData(ProductData productData){		
		this();
		this.productData=productData;
	}
	
	public PriceData(ProductData productData, ShopData shopData){
		this(productData);
		this.shopData=shopData;
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

	
}
