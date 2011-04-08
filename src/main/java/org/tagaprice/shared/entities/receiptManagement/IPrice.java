package org.tagaprice.shared.entities.receiptManagement;

import java.io.Serializable;

/**
 * Set the price of an {@link ReceiptEntry}
 *
 */
public interface IPrice extends Serializable {

	/**
	 * Price is in int because javaScript has problems with floating points.
	 * @param price the price of one {@link ReceiptEntry}
	 */
	public void setPrice(int price);

	/**
	 * Defines the {@link Currency} in which this {@link ReceiptEntry} was bought.
	 * @param currency  the {@link Currency} in which this {@link ReceiptEntry} was bought.
	 */
	public void setCurrency(Currency currency);

	/**
	 * Returns the price in int.
	 * @return the price in int.
	 */
	public int getPrice();

	/**
	 * The {@link Currency} in which this {@link ReceiptEntry} was bought.
	 * @return {@link Currency} in which this {@link ReceiptEntry} was bought.
	 */
	public Currency getCurrency();

}
