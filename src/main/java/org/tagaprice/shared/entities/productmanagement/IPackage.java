package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.ISEntity;
import org.tagaprice.shared.entities.dump.Quantity;

/**
 * Every Product can be bought in different packages. This interface defines the properties of an package.
 * 
 */
public interface IPackage extends ISEntity{

	/**
	 * Set the related {@link IProduct}
	 * @param product the related {@link IProduct}
	 */
	public void setProduct(IProduct product);

	/**
	 * Returns the related {@link IProduct}
	 * @return the related {@link IProduct}
	 */
	public IProduct getProduct();

	/**
	 * Set the {@link Quantity} which is represented by an Quantity and an Unit.
	 * 
	 * @param qantity
	 *            is represented by an Quantity and an Unit
	 */
	public void setQuantity(Quantity qantity);

	/**
	 * Returns the {@link Quantity} of an {@link IPackage}
	 * 
	 * @return the {@link Quantity} of an {@link IPackage}
	 */
	public Quantity getQuantity();

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
