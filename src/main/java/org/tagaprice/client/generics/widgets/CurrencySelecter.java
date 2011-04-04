package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.receiptManagement.Currency;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is just a wrapper!
 *
 */
public class CurrencySelecter extends Composite implements ICurrencySelecter {

	private ICurrencySelecter currencySelecter = GWT.create(ICurrencySelecter.class);

	public CurrencySelecter() {
		initWidget(currencySelecter.asWidget());
	}

	@Override
	public void setCurrency(Currency currency) {
		currencySelecter.setCurrency(currency);
	}

	@Override
	public Currency getCurrency() {
		return currencySelecter.getCurrency();
	}

}
