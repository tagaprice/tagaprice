package org.tagaprice.shared.entities.receiptManagement;

import java.io.Serializable;

import org.tagaprice.shared.entities.productmanagement.*;

/**
 * Stores information about a bought product, the quantity and the price.
 * 
 */
public interface IReceiptEntry extends Serializable {

	/**
	 * The related receipt
	 * @param receipt the related receipt
	 */
	public void setReceipt(IReceipt receipt);

	/**
	 * sets the price per {@link Currency}  in cent
	 * @param price  per {@link Currency}  in cent
	 */
	public void setPrice(IPrice price);

	/**
	 * defines the {@link IPackage} (Product)
	 * @param prackage the {@link IPackage} (Product)
	 */
	public void setPackage(IPackage ipackage);

	/**
	 * 
	 * @return the related {@link IPackage}
	 */
	public IPackage getPackage();

	/**
	 * 
	 * @return the related {@link IReceipt}
	 */
	public IReceipt getReceipt();

	/**
	 * returns the price per {@link Currency} in cent
	 * @return per {@link Currency}  in cent
	 */
	public IPrice getPrice();


}