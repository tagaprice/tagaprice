package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.productmanagement.Country;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods to select a {@link ICountrySelecter}. This interface must me implemented for
 * every screen.
 * 
 */
public interface ICountrySelecter extends IsWidget {

	/**
	 * Set the current country
	 * @param country current country
	 */
	public void setCountry(Country country);


	/**
	 * Returns selected country
	 * @return selected country
	 */
	public Country getCountry();
}
