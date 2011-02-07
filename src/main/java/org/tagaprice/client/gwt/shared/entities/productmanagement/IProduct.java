package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;


/**
 * Defines all methods needed to buy a {@link Product}
 * 
 */
public interface IProduct extends IEntity<IProduct> {


	/**
	 * Sets the depending {@link ICategory} for a product.
	 * 
	 * @param category
	 *            Is the depending {@link ICategory} for a product
	 */
	public void setCategory(ICategory category);

	/**
	 * Returns the {@link ICategory} which this {@link IProduct} depends from.
	 * 
	 * @return Returns the {@link ICategory} which this {@link IProduct} depends from.
	 */
	public ICategory getCategory();



	/**
	 * Defines the {@link IQuantity} with which this {@link IProduct} must be bought. There are two different ways a
	 * {@link IProduct} can be bought.
	 * 1.) The quantity is fix. Like CocaCola 1.5L. The quantity is 1.5 and the unit is L (liter)
	 * 2.) The quantity is not fix. Like gas. Every user will buy the gas in different quantities. So the quantity has
	 * to be zero (0) and the unit is L (liter). With this definition the user must enter the quantity of his product.
	 * 
	 * @param quantity
	 *            the {@link IQuantity} in which this {@link IProduct} can be bought.
	 */
	public void setQuantity(IQuantity quantity);


	/**
	 * Returns the {@link IQuantity} with which this {@link IProduct} must be bought.
	 * 
	 * @return the {@link IQuantity} with which this {@link IProduct} must be bought.
	 */
	public IQuantity getQuantity();

	/**
	 * Add one {@link IPackage} to the Product.
	 * @param ipackage that will be added to the {@link IProduct}
	 */
	public void addPackage(IPackage ipackage);

	/**
	 * Add some {@link IPackage} to the Product.
	 * @param ipackage that will be added to the {@link IProduct}
	 */
	public void addPackages(ArrayList<IPackage> iPackage);

	/**
	 * Return all Packages includes in a {@link IProduct}
	 * @return all Packages includes in a {@link IProduct}
	 */
	public ArrayList<IPackage> getPackages();
}
