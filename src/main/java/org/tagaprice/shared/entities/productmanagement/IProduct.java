package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.IEntity;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.ICategory;


/**
 * Defines all methods needed to buy a {@link Product}
 * 
 */
public interface IProduct extends IEntity<IProduct> {


	/**
	 * Add one {@link IPackage} to the Product.
	 * @param ipackage that will be added to the {@link IProduct}
	 */
	public void addPackage(IPackage ipackage);

	/**
	 * Returns the {@link ICategory} which this {@link IProduct} depends from.
	 * 
	 * @return Returns the {@link ICategory} which this {@link IProduct} depends from.
	 */
	public ICategory getCategory();


	/**
<<<<<<< HEAD:src/main/java/org/tagaprice/shared/entities/productmanagement/IProduct.java
	 * Sets the {@link Unit} which a {@link org.tagaprice.shared.entities.productmanagement.IProduct} can
	 * have.
	 * 
	 * @param unit
	 *            the {@link Unit} which a {@link org.tagaprice.shared.entities.productmanagement.IProduct}
	 *            can
	 *            have.
=======
	 * Return all Packages includes in a {@link IProduct}
	 * @return all Packages includes in a {@link IProduct}
>>>>>>> origin/master:src/main/java/org/tagaprice/client/gwt/shared/entities/productmanagement/IProduct.java
	 */
	public ArrayList<IPackage> getPackages();

	/**
	 * The {@link Unit} in which a {@link org.tagaprice.shared.entities.productmanagement.IProduct} can be
	 * bought.
	 * 
	 * @return the {@link Unit} in which a {@link org.tagaprice.shared.entities.productmanagement.IProduct} can be
	 * bought
	 */
	public Unit getUnit();



	/**
	 * Sets the depending {@link ICategory} for a product.
	 * 
	 * @param category
	 *            Is the depending {@link ICategory} for a product
	 */
	public void setCategory(ICategory category);

	/**
	 * Set some {@link IPackage} to the Product. All included products will be deleted.
	 * @param ipackage that will be set to the {@link IProduct}
	 */
	public void setPackages(ArrayList<IPackage> iPackage);

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
}
