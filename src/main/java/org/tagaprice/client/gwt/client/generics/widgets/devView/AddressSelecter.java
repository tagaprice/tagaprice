package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.widgets.IAddressSelecter;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Address;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter{

	private VerticalPanel _vePa1 = new VerticalPanel();
	private VerticalPanel _vePa2 = new VerticalPanel();
	private OneAddressSelecter _newAdress = new OneAddressSelecter();

	public AddressSelecter() {
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


	private void addAddress(IAddress address){
		OneAddressSelecter tempAddresser = new OneAddressSelecter();
		tempAddresser.setAddress(address);

		_vePa2.add(tempAddresser);
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

	class OneAddressSelecter extends Composite{

		VerticalPanel vePaTemp = new VerticalPanel();

		TextBox _street = new TextBox();
		TextBox _zip = new TextBox();
		TextBox _city = new TextBox();
		TextBox _country = new TextBox();
		Label _lat = new Label();
		Label _lng = new Label();
		Geocoder coder = new Geocoder();

		MarkerOptions _mOptions = MarkerOptions.newInstance();
		Marker marker;
		MapWidget mapWidget;

		public OneAddressSelecter() {

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
							_country.setText(locations.get(0).getCountry());
						}

						@Override
						public void onFailure(int statusCode) {
							// TODO Auto-generated method stub

						}
					});
				}
			});
		}

		public void setAddress(IAddress address){
			_street.setText(address.getStreet());
			_zip.setText(address.getZip());
			_city.setText(address.getCity());
			_country.setText(""+address.getCountry());


			setLatLng(LatLng.newInstance(address.getLat(), address.getLng()));
		}

		public IAddress getAddress(){
			IAddress backAddr = new Address();
			backAddr.setStreet(_street.getText());
			backAddr.setZip(_zip.getText());
			backAddr.setCity(_city.getText());
			//TODO add function to country
			//backAddr.setCountry(_country.getText());
			backAddr.setLat(marker.getLatLng().getLatitude());
			backAddr.setLng(marker.getLatLng().getLongitude());


			return backAddr;
		}

		private void setLatLng(LatLng latLng){
			_lat.setText(""+latLng.getLatitude());
			_lng.setText(""+latLng.getLongitude());
			marker.setLatLng(latLng);
			mapWidget.setCenter(marker.getLatLng());



		}
	}

}
