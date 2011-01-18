package org.tagaprice.client.gwt.shared.entities.dump;

import java.io.Serializable;


/**
 * A {@link IQuantity} defines in which way a user can buy a
 * {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct}.
 * 
 */
public interface IQuantity extends Serializable {

	/**
	 * Sets the {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} size.
	 * 
	 * @param quantity
	 *            Sets the {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} size.
	 */
	public void setQuantity(double quantity);

	/**
	 * Returns the size in which a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} can be
	 * bought
	 * 
	 * @return the size in which a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} can be
	 *         bought
	 */
	public double getQuantity();

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

	public IQuantity copy();
}
