package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.client.generics.widgets.ICurrencySelecter;
import org.tagaprice.shared.entities.receiptManagement.Currency;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

/**
 * No selection or any function at the moment. it workes only with euro.
 * @author brandiatmuhkuh
 *
 */
public class CurrencySelecter extends Composite implements ICurrencySelecter {

	private Label _listBoxCurrency = new Label("€");
	private Currency _currency = Currency.euro;

	public CurrencySelecter() {
		initWidget(_listBoxCurrency);
		
		/*
		for(Currency c:Currency.values()){
			_listBoxCurrency.addItem(""+c.name(),""+c.name());
		}
		*/

	}

	@Override
	public void setCurrency(Currency currency) {
		_currency=currency;
		/*
		int pos = 0;

		for(int i=0; i< Currency.values().length; i++){
			if(Currency.values()[i].equals(currency)){
				pos=i;
			}
		}

		_listBoxCurrency.setSelectedIndex(pos);
		*/
	}

	@Override
	public Currency getCurrency() {
		return _currency;
	}

}
