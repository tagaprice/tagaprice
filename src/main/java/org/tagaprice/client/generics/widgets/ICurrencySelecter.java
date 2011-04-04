package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.receiptManagement.Currency;

import com.google.gwt.user.client.ui.IsWidget;


/**
 * This interface defines the primary methods to select a {@link Currency}.
 *
 */
public interface ICurrencySelecter extends IsWidget {

	/**
	 * Set the current {@link Currency}
	 * @param currency the current {@link Currency}
	 */
	public void setCurrency(Currency currency);

	/**
	 * Returns the selected {@link Currency}
	 * @return the selected {@link Currency}
	 */
	public Currency getCurrency();
}
