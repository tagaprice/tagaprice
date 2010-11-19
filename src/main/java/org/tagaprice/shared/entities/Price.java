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

	/** amount of this {@link Price} in currency/100 */
	private int _amount;
	private Currency _currency;

	/**
	 * Default constructor needed for serialization.
	 */
	public Price() { }

	/**
	 * Create a new {@link Price}
	 * 
	 * @param amount
	 *            amount of money
	 * @param currency
	 *            currency of this price
	 */
	public Price(int amount, Currency currency) {
		_amount = amount;
		_currency = currency;
	}

	/**
	 * TODO i would remove this constructor and call the other one {@link Price#Price(int, Currency)})
	 * since that is what this constructor simply does...
	 * 
	 * @param amount
	 * @param currencyId
	 * @param currencyRev
	 * @param currencyName
	 * @param currencyLocale
	 */
	public Price(int amount, int currencyId, int currencyRev, String currencyName, int currencyLocale) {
		this(amount, new Currency(currencyId, currencyRev, currencyName, currencyLocale));
	}


	/**
	 * @return the amount of money of this price.
	 */
	public int getAmount() {
		return _amount;
	}

	/**
	 * @return {@link Currency} of this {@link Price}.
	 */
	public Currency getCurrency() {
		return _currency;
	}

	/**
	 * @param amount
	 *            set the amount of money of this price.
	 */
	public void setAmount(int amount) {
		this._amount = amount;
	}


	@Override
	public String getSerializeName() {
		return "price";
	}


	@Override
	public boolean equals(Object o) {
		boolean areEqual = true;

		if (o instanceof Price) {
			Price p = (Price) o;
			if (getAmount() != p.getAmount())
				areEqual = false;
			if (getCurrency() != null) {
				if (!getCurrency().equals(p.getCurrency()))
					areEqual = false;
			} else if (p.getCurrency() != null)
				areEqual = false;
		} else
			areEqual = false;

		return areEqual;
	}
}
