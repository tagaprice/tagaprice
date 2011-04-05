package org.tagaprice.shared.entities.receiptManagement;

import java.io.Serializable;

/**
 * Set the price of an {@link IReceiptEntry}
 *
 */
public interface IPrice extends Serializable {

	/**
	 * Price is in int because javaScript has problems with floating points.
	 * @param price the price of one {@link IReceiptEntry}
	 */
	public void setPrice(int price);

	/**
	 * Defines the {@link Currency} in which this {@link IReceiptEntry} was bought.
	 * @param currency  the {@link Currency} in which this {@link IReceiptEntry} was bought.
	 */
	public void setCurrency(Currency currency);

	/**
	 * Returns the price in int.
	 * @return the price in int.
	 */
	public int getPrice();

	/**
	 * The {@link Currency} in which this {@link IReceiptEntry} was bought.
	 * @return {@link Currency} in which this {@link IReceiptEntry} was bought.
	 */
	public Currency getCurrency();

}
