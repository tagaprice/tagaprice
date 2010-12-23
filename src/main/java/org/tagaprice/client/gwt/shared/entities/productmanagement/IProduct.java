package org.tagaprice.client.gwt.shared.entities.productmanagement;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IUnit;


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
	 * Sets the {@link IUnit} in which this {@link IProduct} can be bought.
	 * @param unit the {@link IUnit} in which this {@link IProduct} can be bought.
	 */
	public void setUnit(IUnit unit);

	/**
	 * Returns the {@link IUnit} in which this {@link IProduct} can be bought.
	 * @return the {@link IUnit} in which this {@link IProduct} can be bought.
	 */
	public IUnit getUnit();
}
