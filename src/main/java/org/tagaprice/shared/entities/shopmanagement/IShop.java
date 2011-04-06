package org.tagaprice.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.IEntity;

public interface IShop extends IEntity {


	/**
	 * Set some {@link ISubsidiary} to the Shop. The setShop() method is called for every {@link ISubsidiary} and this
	 * {@link IShop} will be added. All include {@link ISubsidiary} will be deleted.
	 * 
	 * @param addresses
	 *            that will be set to the {@link IShop}
	 */
	public void setSubsidiary(ArrayList<ISubsidiary> subsidiaries);

	/**
	 * Add one new {@link ISubsidiary} to this {@link IShop}. The setShop() method is called for every {@link ISubsidiary} and
	 * this {@link IShop} will be added.
	 * 
	 * @param subsidiary
	 *            that will be added to the {@link IShop}
	 */
	public void addSubsidiary(ISubsidiary subsidiary);

	/**
	 * Returns all {@link ISubsidiary}
	 * 
	 * @return all {@link ISubsidiary}
	 */
	public ArrayList<ISubsidiary> getSubsidiaries();

}
