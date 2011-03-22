package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.tagaprice.client.gwt.client.generics.widgets.ICurrencySelecter;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.Currency;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class CurrencySelecter extends Composite implements ICurrencySelecter {

	private ListBox _listBoxCurrency = new ListBox();

	public CurrencySelecter() {
		initWidget(_listBoxCurrency);

		for(Currency c:Currency.values()){
			_listBoxCurrency.addItem(""+c.name(),""+c.name());
		}

	}

	@Override
	public void setCurrency(Currency currency) {
		int pos = 0;

		for(int i=0; i< Currency.values().length; i++){
			if(Currency.values()[i].equals(currency)){
				pos=i;
			}
		}

		_listBoxCurrency.setSelectedIndex(pos);
	}

	@Override
	public Currency getCurrency() {
		return Currency.valueOf(_listBoxCurrency.getValue(_listBoxCurrency.getSelectedIndex()));
	}

}
