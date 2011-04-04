package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.productmanagement.Country;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public  class CountrySelecter extends Composite implements ICountrySelecter {

	private ICountrySelecter countrySelecter = GWT.create(ICountrySelecter.class);

	public CountrySelecter() {
		initWidget(countrySelecter.asWidget());
	}

	@Override
	public void setCountry(Country country) {
		countrySelecter.setCountry(country);
	}

	@Override
	public Country getCountry() {
		return countrySelecter.getCountry();
	}

}
