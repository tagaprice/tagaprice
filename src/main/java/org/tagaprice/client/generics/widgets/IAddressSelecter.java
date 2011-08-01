package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Address;
import com.google.gwt.user.client.ui.IsWidget;

public interface IAddressSelecter extends IsWidget {



	/**
	 * Set an address
	 * @param address
	 */
	public void setAddress(Address address);

	/**
	 * Return the selected {@link IAddress}
	 * @return the selected {@link IAddress}
	 */
	public Address getAddress();
}
