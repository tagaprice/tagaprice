package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.tagaprice.client.gwt.client.generics.widgets.CountrySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.IAddressSelecter;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Subsidiary;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.ISubsidiary;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter {


	VerticalPanel vePaTemp = new VerticalPanel();

	TextBox _street = new TextBox();
	TextBox _zip = new TextBox();
	TextBox _city = new TextBox();
	//TextBox _country = new TextBox();
	CountrySelecter _country = new CountrySelecter();
	Label _lat = new Label();
	Label _lng = new Label();
	Geocoder coder = new Geocoder();
	ISubsidiary _address;

	MarkerOptions _mOptions = MarkerOptions.newInstance();
	Marker marker;
	MapWidget mapWidget;

	public AddressSelecter() {

		HorizontalPanel hoPaTemp = new HorizontalPanel();
		_mOptions.setDraggable(true);

		marker = new Marker(LatLng.newInstance(48.21426, 16.37692), _mOptions);
		marker.setDraggingEnabled(true);

		mapWidget = new MapWidget(marker.getLatLng(), 13);
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

		tempGrid.setWidget(0, 1, _street);
		tempGrid.setWidget(1, 1, _zip);
		tempGrid.setWidget(2, 1, _city);
		tempGrid.setWidget(3, 1, _country);
		tempGrid.setWidget(4, 1, _lat);
		tempGrid.setWidget(5, 1, _lng);

		hoPaTemp.add(tempGrid);
		hoPaTemp.add(mapWidget);
		vePaTemp.add(hoPaTemp);

		initWidget(vePaTemp);


		marker.addMarkerDragEndHandler(new MarkerDragEndHandler() {

			@Override
			public void onDragEnd(MarkerDragEndEvent event) {
				setLatLng(event.getSender().getLatLng());
				coder.getLocations(event.getSender().getLatLng(), new LocationCallback() {

					@Override
					public void onSuccess(JsArray<Placemark> locations) {
						_street.setText(locations.get(0).getStreet());
						_zip.setText(locations.get(0).getPostalCode());
						_city.setText(locations.get(0).getCity());
						_country.setCountry(Country.valueOf(locations.get(0).getCountry()));
						//_country.setText(locations.get(0).getCountry());
					}

					@Override
					public void onFailure(int statusCode) {
						// TODO Auto-generated method stub

					}
				});
			}
		});
	}

	@Override
	public void setAddress(ISubsidiary address){
		_address=address;
		_street.setText(_address.getStreet());
		_zip.setText(_address.getZip());
		_city.setText(_address.getCity());
		_country.setCountry(_address.getCountry());


		setLatLng(LatLng.newInstance(_address.getLat(), _address.getLng()));
	}

	@Override
	public ISubsidiary getAddress(){
		if(_address==null)_address = new Subsidiary();
		_address.setStreet(_street.getText());
		_address.setZip(_zip.getText());
		_address.setCity(_city.getText());
		_address.setCountry(_country.getCountry());
		_address.setLat(marker.getLatLng().getLatitude());
		_address.setLng(marker.getLatLng().getLongitude());


		return _address;
	}

	private void setLatLng(LatLng latLng){
		_lat.setText(""+latLng.getLatitude());
		_lng.setText(""+latLng.getLongitude());
		marker.setLatLng(latLng);
		mapWidget.setCenter(marker.getLatLng());



	}


}
