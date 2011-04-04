package org.tagaprice.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.generics.widgets.IMultipleAddressSelecter;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MultipleAddressSelecter extends Composite implements IMultipleAddressSelecter{

	private VerticalPanel _vePa1 = new VerticalPanel();
	private VerticalPanel _vePa2 = new VerticalPanel();
	private AddressSelecter _newAdress = new AddressSelecter();

	public MultipleAddressSelecter() {
		initWidget(_vePa1);

		_vePa1.add(new Label("Addresses"));
		_vePa1.add(_vePa2);

		//new Adress
		_vePa1.add(new HTML("<b>Add new address</b>"));
		_vePa1.add(_newAdress);
		Button addAddress = new Button("Add this address");
		_vePa1.add(addAddress);
		addAddress.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				addAddress(_newAdress.getAddress());
			}
		});
	}


	private void addAddress(ISubsidiary address){
		AddressSelecter tempAddresser = new AddressSelecter();
		tempAddresser.setAddress(address);

		_vePa2.add(tempAddresser);
	}

	@Override
	public void setSubsidiaries(ArrayList<ISubsidiary> address) {
		_vePa2.clear();

		for(ISubsidiary a:address)
			addAddress(a);
	}

	@Override
	public ArrayList<ISubsidiary> getSubsidiaries() {
		ArrayList<ISubsidiary> rA = new ArrayList<ISubsidiary>();

		for(int i=0;i<_vePa2.getWidgetCount();i++)
			rA.add(((AddressSelecter)_vePa2.getWidget(i)).getAddress());

		return rA;
	}


	@Override
	public void setCurrentAddress(Address address) {
		_newAdress.setCurrentAddress(address);
	}


}
