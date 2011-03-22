package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.tagaprice.client.gwt.client.generics.ICO2Countries;
import org.tagaprice.client.gwt.client.generics.widgets.ICountrySelecter;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class CountrySelecter extends Composite implements ICountrySelecter {
	private ListBox _countryListBox = new ListBox();

	public CountrySelecter() {
		initWidget(_countryListBox);

		for(Country c:Country.values())
			_countryListBox.addItem(ICO2Countries.ICO2_COUNTRIES.getString(""+c.name()), ""+c.name());
	}

	@Override
	public void setCountry(Country country) {
		int pos=0;

		for(int i=0; i<Country.values().length && pos==0;i++){
			if(Country.values()[i].equals(country))
				pos=i;
		}

		_countryListBox.setSelectedIndex(pos);

	}

	@Override
	public Country getCountry() {
		return Country.valueOf(_countryListBox.getValue(_countryListBox.getSelectedIndex()));
	}

}
