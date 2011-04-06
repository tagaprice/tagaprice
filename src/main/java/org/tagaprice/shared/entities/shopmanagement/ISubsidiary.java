package org.tagaprice.shared.entities.shopmanagement;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.ISEntity;

/**
 * An Address defines the position where a IShop can be.
 *
 */
public interface ISubsidiary extends ISEntity<ISubsidiary> {

	/**
	 * Set an {@link Address} to the Subsidiary
	 * @param address subsidiary address
	 */
	public void setAddress(Address address);

	/**
	 * @return the address of the subsidiary
	 */
	public Address getAddress();

	/**
	 * @return the {@link IShop}
	 */
	public IShop getShop();


	/**
	 * Set the {@link IShop} on which the {@link IAddress} depends from.
	 * @param shop the {@link IShop} on which the {@link IAddress} depends from.
	 */
	public void setShop(IShop shop);

}
