package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods to select a {@link Shop} selecter for a receipt.
 * @author Helga Weik (kaltra)
 *
 */

public interface IShopSelector extends IsWidget {

	/**
	 * set a current shop
	 * @param shop
	 */

	public abstract void setShop(IShop shop);

	/**
	 * returns the current selected shop
	 * @return
	 */

	public abstract IShop getShop();



}
