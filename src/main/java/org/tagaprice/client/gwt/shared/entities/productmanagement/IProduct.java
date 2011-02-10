package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.core.entities.Unit;


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
	 * Sets the {@link Unit} which a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} can
	 * have.
	 * 
	 * @param unit
	 *            the {@link Unit} which a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct}
	 *            can
	 *            have.
	 */
	public void setUnit(Unit unit);

	/**
	 * The {@link Unit} in which a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} can be
	 * bought.
	 * 
	 * @return the {@link Unit} in which a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} can be
	 * bought
	 */
	public Unit getUnit();



	/**
	 * Add one {@link IPackage} to the Product.
	 * @param ipackage that will be added to the {@link IProduct}
	 */
	public void addPackage(IPackage ipackage);

	/**
	 * Set some {@link IPackage} to the Product. All included productes will be deleted.
	 * @param ipackage that will be set to the {@link IProduct}
	 */
	public void setPackages(ArrayList<IPackage> iPackage);

	/**
	 * Return all Packages includes in a {@link IProduct}
	 * @return all Packages includes in a {@link IProduct}
	 */
	public ArrayList<IPackage> getPackages();
}
