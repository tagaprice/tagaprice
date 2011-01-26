package org.tagaprice.client.gwt.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods to select a {@link Shop} selector for a receipt.
 * @author Helga Weik (kaltra)
 *
 */

public interface IShopSelecter extends IsWidget, HasChangeHandlers {

	/**
	 * set a current shop
	 * @param shop
	 */

	public void setShop(IShop shop);

	/**
	 * returns the current selected shop
	 * @return
	 */

	public IShop getShop();

	public void setAvailableShops(ArrayList<IShop> shops);



}
