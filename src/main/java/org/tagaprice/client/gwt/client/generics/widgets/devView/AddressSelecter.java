package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.widgets.IAddressSelecter;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter{

	private VerticalPanel _vePa1 = new VerticalPanel();
	private VerticalPanel _vePa2 = new VerticalPanel();

	public AddressSelecter() {
		initWidget(_vePa1);

		_vePa1.add(new Label("Addresses"));
		_vePa1.add(_vePa2);
	}


	private void addAddress(IAddress address){
		VerticalPanel vePaTemp = new VerticalPanel();

		MarkerOptions _mOptions = MarkerOptions.newInstance();
		_mOptions.setDraggable(true);
		Marker marker = new Marker(LatLng.newInstance(address.getLat(), address.getLng()), _mOptions);
		marker.setDraggingEnabled(true);

		MapWidget mapWidget = new MapWidget(marker.getLatLng(), 13);
		mapWidget.setSize("200px", "200px");
		mapWidget.addOverlay(marker);

		vePaTemp.add(new HTML("<hr />"));
		Grid tempGrid = new Grid(6, 2);
		tempGrid.setWidget(0, 0, new Label("street"));
		tempGrid.setWidget(1, 0, new Label("zip"));
		tempGrid.setWidget(2, 0, new Label("city"));
		tempGrid.setWidget(3, 0, new Label("country"));
		tempGrid.setWidget(4, 0, new Label("lat"));
		tempGrid.setWidget(5, 0, new Label("lng"));

		tempGrid.setWidget(0, 1, new Label(address.getStreet()));
		tempGrid.setWidget(1, 1, new Label(address.getZip()));
		tempGrid.setWidget(2, 1, new Label(address.getCity()));
		tempGrid.setWidget(3, 1, new Label(""+address.getCountry()));
		tempGrid.setWidget(4, 1, new Label(""+address.getLat()));
		tempGrid.setWidget(5, 1, new Label(""+address.getLat()));

		vePaTemp.add(tempGrid);
		vePaTemp.add(mapWidget);
		_vePa2.add(vePaTemp);
	}

	@Override
	public void setAddresses(ArrayList<IAddress> address) {
		_vePa2.clear();

		for(IAddress a:address)
			addAddress(a);
	}

	@Override
	public ArrayList<IAddress> getAddresses() {
		// TODO Auto-generated method stub
		return null;
	}

}
