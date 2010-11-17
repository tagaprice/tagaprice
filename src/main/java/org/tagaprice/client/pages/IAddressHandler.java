package org.tagaprice.client.pages;

import org.tagaprice.shared.entities.Address;

/**
 * Is used to refresh the current GPS position. Especially by async requests.
 * 
 */
public interface IAddressHandler {

	/**
	 * Sets the position.
	 * 
	 * @param address
	 *            is the current or selected postion.
	 */
	public abstract void setAddress(Address address);
}
