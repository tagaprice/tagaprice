package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.io.Serializable;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;

/**
 * Stores information about a bought product, the quantity and the price (for quantity 1).
 * @author Helga
 *
 */
public interface IReceiptEntry extends Serializable {

	public abstract int getQuantity();

	public abstract void setQuantity(int quantity);

	/**
	 * returns the price per unit in cent
	 * @return
	 */
	public abstract long getPrice();

	/**
	 * sets the price per unit in cent
	 * @param price
	 */
	public abstract void setPrice(long price);

	public abstract IProduct getProduct();

	public abstract void setProduct(IProduct gwtProduct);

	public abstract IReceipt getReceipt();

	public abstract void setReceipt(IReceipt receipt);

	public abstract IRevisionId getProductId();

	public abstract void setProductId(IRevisionId productId);

	public abstract IRevisionId getReceiptId();

	public abstract void setReceiptId(IRevisionId receiptId);












}
