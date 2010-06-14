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
package org.tagaprice.shared;

public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	private int price; // price in currency/100
	private Currency currency;
	
	public Price() {
	}
	
	public Price(int price, Currency currency) {
		setPrice(price);
		setCurrency(currency);
	}
	
	public Price(int price, int currencyId, int currencyRev, String currencyName) {
		this(price, new Currency(currencyId, currencyRev, currencyName));
	}
	
	public int getPrice() {
		return price;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public void setCurrency(long currencyId, int rev, String currencyName) {
		setCurrency(new Currency(currencyId, rev, currencyName));
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
