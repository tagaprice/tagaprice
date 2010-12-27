package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;


/**
 * Defines all methods needed to buy a {@link Product}
 *
 */
public interface IProduct extends IEntity {


	/**
	 * Sets the depending {@link ICategory} for a product.
	 * @param category Is the depending {@link ICategory} for a product
	 */
	public void setCategory(ICategory category);

	/**
	 * Returns the {@link ICategory} which this {@link IProduct} depends from.
	 * @return Returns the {@link ICategory} which this {@link IProduct} depends from.
	 */
	public ICategory getCategory();



	/**
	 * Add a {@link IQuantity} in which this {@link IProduct} can be bought.
	 * @param quantity the {@link IQuantity} in which this {@link IProduct} can be bought.
	 */
	public void addQuantity(IQuantity quantity);

	/**
	 * Add a list of {@link IQuantity} in which this {@link IProduct} can be bought.
	 * @param quantities a list of {@link IQuantity} in which this {@link IProduct} can be bought.
	 */
	public void addQuantities(ArrayList<IQuantity> quantities);

	/**
	 * Returns a list of {@link IQuantity}s corresponding to this {@link IProduct}.
	 * @return a list of {@link IQuantity}s corresponding to this {@link IProduct}.
	 */
	public ArrayList<IQuantity> getQuantities();

}
