package org.tagaprice.shared.entities.receiptManagement;

import java.io.Serializable;

import org.tagaprice.shared.entities.productmanagement.Package;

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
	 * defines the {@link Package} (Product)
	 * @param prackage the {@link Package} (Product)
	 */
	public void setPackage(Package ipackage);

	/**
	 * 
	 * @return the related {@link Package}
	 */
	public Package getPackage();

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