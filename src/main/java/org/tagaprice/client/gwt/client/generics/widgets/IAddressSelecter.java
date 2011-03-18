package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;
import com.google.gwt.user.client.ui.IsWidget;

public interface IAddressSelecter extends IsWidget {

	/**
	 * Set an address
	 * @param address
	 */
	public void setAddress(IAddress address);

	/**
	 * Return the selected {@link IAddress}
	 * @return the selected {@link IAddress}
	 */
	public IAddress getAddress();
}
