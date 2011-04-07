package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.user.client.ui.IsWidget;

public interface IAddressSelecter extends IsWidget {

	/**
	 * Set current address (position) of the user
	 * @param address current address (position) of the user
	 */
	public void setCurrentAddress(Address address);

	/**
	 * Set an address
	 * @param address
	 */
	public void setAddress(Shop address);

	/**
	 * Return the selected {@link IAddress}
	 * @return the selected {@link IAddress}
	 */
	public Shop getAddress();
}
