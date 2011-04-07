package org.tagaprice.shared.entities.dump;

import java.io.Serializable;

import org.tagaprice.shared.entities.Unit;


/**
 * A {@link IQuantity} defines in which way a user can buy a {@link IProduct}.
 */
public interface IQuantity extends Serializable {

	/**
	 * Sets the {@link IProduct} size.
	 * 
	 * @param quantity
	 *            Sets the {@link IProduct} size.
	 */
	public void setQuantity(double quantity);

	/**
	 * Returns the size in which a {@link IProduct} can be
	 * bought
	 * 
	 * @return the size in which a {@link IProduct} can be
	 *         bought
	 */
	public double getQuantity();

	/**
	 * Sets the {@link Unit} which a {@link IProduct} can
	 * have.
	 * 
	 * @param unit
	 *            the {@link Unit} which a {@link IProduct} can
	 *            have.
	 */
	public void setUnit(Unit unit);

	/**
	 * The {@link Unit} in which a {@link IProduct} can be
	 * bought.
	 * 
	 * @return the {@link Unit} in which a {@link IProduct} can be
	 *         bought
	 */
	public Unit getUnit();

}
