package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.io.Serializable;
import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

/**
 * Every Product can be bought in different packages. This interface defines the properties of an package.
 * 
 */
public interface IPackage extends Serializable {

	/**
	 * Set the {@link IQuantity} which is represented by an Quantity and an Unit.
	 * 
	 * @param qantity
	 *            is represented by an Quantity and an Unit
	 */
	public void setQuantity(IQuantity qantity);

	/**
	 * Returns the {@link IQuantity} of an {@link IPackage}
	 * 
	 * @return the {@link IQuantity} of an {@link IPackage}
	 */
	public IQuantity getQuantity();

	/**
	 * Add one barcode is labeled on the {@link IPackage}
	 * 
	 * @param barcode
	 *            is labeled on the {@link IPackage}
	 */
	//TODO Replace with Barcode Object
	public void addBarcode(int barcode);

	/**
	 * Add many barcodes are labeled on the {@link IPackage}
	 * 
	 * @param barcode
	 *            labeled on the {@link IPackage}
	 */
	//TODO Replace with Barcode Object
	public void addBarcodes(ArrayList<Integer> barcode);

	/**
	 * Returns all barcodes labeled on a {@link IPackage}
	 * 
	 * @return all barcodes labeled on a {@link IPackage}
	 */
	//TODO Replace with Barcode Object
	public ArrayList<Integer> getBarcodes();
}
