package org.tagaprice.shared.entities.receiptManagement;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Set the price of an {@link ReceiptEntry}
 *
 */
public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal _price = new BigDecimal("0.0");
	private Currency _currency;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Price() {}


	/**
	 * <b>NEW</b>
	 * Create a new price
	 * @param price the price
	 * @param currency the currency
	 */
	public Price(BigDecimal price, Currency currency){
		setPrice(price);
		setCurrency(currency);
	}

	/**
	 * The {@link Currency} in which this {@link IReceiptEntry} was bought.
	 * @return {@link Currency} in which this {@link IReceiptEntry} was bought.
	 */
	public Currency getCurrency() {
		return _currency;
	}

	/**
	 * Returns the price in int.
	 * @return the price in int.
	 */
	public BigDecimal getPrice() {
		return _price;
	}

	/**
	 * Defines the {@link Currency} in which this {@link ReceiptEntry} was bought.
	 * @param currency  the {@link Currency} in which this {@link ReceiptEntry} was bought.
	 */
	public void setCurrency(Currency currency) {
		_currency=currency;

	}

	/**
	 * Price is in int because javaScript has problems with floating points.
	 * @param price the price of one {@link ReceiptEntry}
	 */
	public void setPrice(BigDecimal price) {
		_price=price;

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Price [_price=" + _price + ", _currency=" + _currency + "]";
	}



}
