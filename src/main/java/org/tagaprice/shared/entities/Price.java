/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: Price.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;

/**
 * Represents an amount of money in a given currency. 
 */
public class Price implements ISerializable {
	private static final long serialVersionUID = 1L;

	/** price in currency/100 */
	private int _price; 
	private Currency _currency;
	
	/**
	 * Default constructor needed for serialization.
	 */
	public Price() { }
	
	
	/**
	 * Create a new Price
	 * @param price amount of money
	 * @param currency currency of this price
	 */
	public Price(int price, Currency currency) {
		_price = price;
		_currency = currency;
	}
	
	
	/**
	 * TODO i would remove this constructor and call the other one {@link Price#Price(int, Currency)}) 
	 * since that is what this constructor simply does...
	 * @param price
	 * @param currencyId
	 * @param currencyRev
	 * @param currencyName
	 * @param currencyLocale
	 */
	public Price(int price, int currencyId, int currencyRev, String currencyName, int currencyLocale) {
		this(price, new Currency(currencyId, currencyRev, currencyName, currencyLocale));
	}
	
	
	/**
	 * @return the amount of money of this price.
	 */
	public int getPrice() {
		return _price;
	}

	
	/**
	 * @return {@link Currency} of this price.
	 */
	public Currency getCurrency() {
		return _currency;
	}
	
	
	/**
	 * @param price set the amount of money of this price.
	 */
	public void setPrice(int price) {
		this._price = price;
	}
	

	@Override
	public String getSerializeName() {
		return "price";
	}
	

	@Override
	public boolean equals(Object o) {
		boolean rc = true;

		if (o instanceof Price) {
			Price p = (Price) o;
			if (getPrice() != p.getPrice()) rc = false;
			if (getCurrency() != null) {
				if (!getCurrency().equals(p.getCurrency())) rc = false;
			}
			else if (p.getCurrency() != null) rc = false;
		}
		else rc = false;

		return rc;
	}
}
