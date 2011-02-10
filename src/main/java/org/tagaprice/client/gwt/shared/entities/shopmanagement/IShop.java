package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IEntity;

public interface IShop extends IEntity<IShop> {


	/**
	 * Set some {@link IAddress} to the Shop. All include products will be deleted.
	 * @param addresses that will be set to the {@link IShop}
	 */
	public void setAddresses(ArrayList<IAddress> addresses);

	/**
	 * Add one new {@link IAddress} to this {@link IShop}
	 * @param address that will be added to the {@link IShop}
	 */
	public void addAddress(IAddress address);

	/**
	 * Returns all {@link IAddress}es
	 * @return  all {@link IAddress}es
	 */
	public ArrayList<IAddress> getAddresses();

}
