package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.shopmanagement.Shop;

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

	public void setShop(Shop shop);

	/**
	 * returns the current selected shop
	 * @return
	 */

	public Shop getShop();

	public void setAvailableShops(ArrayList<Shop> shops);



}
