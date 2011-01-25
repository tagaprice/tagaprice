package org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView;

import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class CreateShopViewImpl extends Composite implements ICreateShopView {
	interface CreateShopViewImpleUiBinder extends
	UiBinder<Widget, CreateShopViewImpl> {
	}

	private static CreateShopViewImpleUiBinder uiBinder = GWT
	.create(CreateShopViewImpleUiBinder.class);

	private Presenter _presenter;

	@UiField
	Label _id;

	@UiField
	TextBox _name;

	@UiField
	TextBox _street;

	@UiField
	TextBox _zip;

	@UiField
	TextBox _city;

	@UiField
	TextBox _country;

	@UiField
	Label _lat;

	@UiField
	Label _lng;

	@UiField
	SimplePanel _mapContainer;

	@UiField
	Button _saveButton;


	MapWidget _map = new MapWidget(LatLng.newInstance(48.20963, 16.37083),13);
	MarkerOptions _mOptions = MarkerOptions.newInstance();
	Marker _positionMarker;


	public CreateShopViewImpl() {
		initWidget(CreateShopViewImpl.uiBinder.createAndBindUi(this));


		//Implement GoogleMaps
		_map.setSize("200px", "200px");
		_mapContainer.add(_map);



		_mOptions.setDraggable(true);
		_positionMarker = new Marker(_map.getCenter(),_mOptions);
		_positionMarker.setDraggingEnabled(true);


		_map.addOverlay(_positionMarker);


		//DragHandler
		_positionMarker.addMarkerDragEndHandler(new MarkerDragEndHandler() {

			@Override
			public void onDragEnd(MarkerDragEndEvent event) {
				_lat.setText(""+_positionMarker.getLatLng().getLatitude());
				_lng.setText(""+_positionMarker.getLatLng().getLongitude());
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setPresenter(org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView.Presenter)
	 */
	@Override
	public void setPresenter(Presenter presenter) {
		_presenter = presenter;
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		_name.setText(title);

	}

	@UiHandler("_saveButton")
	public void onSaveEvent(ClickEvent event) {
		this._presenter.onSaveEvent();
	}





	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setStreet(java.lang.String)
	 */
	@Override
	public void setStreet(String street) {
		_street.setText(street);

	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setZip(java.lang.String)
	 */
	@Override
	public void setZip(String zip) {
		_zip.setText(zip);

	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setCity(java.lang.String)
	 */
	@Override
	public void setCity(String city) {
		_city.setText(city);
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setCountry(org.tagaprice.client.gwt.shared.entities.productmanagement.Country)
	 */
	@Override
	public void setCountry(Country country) {
		_country.setText(country != null ? country.name() : "");
	}



	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#getTitle()
	 */
	@Override
	public String getShopTitle() {
		return _name.getText();
	}

	@Override
	public void setShopTitle(String title) {
		_name.setText(title);
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#getStreet()
	 */
	@Override
	public String getStreet() {
		return _street.getText();
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#getZip()
	 */
	@Override
	public String getZip() {
		return _zip.getText();
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#getCity()
	 */
	@Override
	public String getCity() {
		return _city.getText();
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#getCountry()
	 */
	@Override
	public Country getCountry() {
		// TODO Auto-generated method stub
		return Country.at;
	}


	@Override
	public void setRevisionId(IRevisionId revisionId) {
		if(revisionId != null) {
			this._id.setText(revisionId.getId() + "_" + revisionId.getRevision());
		}else {
			this._id.setText("");
		}
	}

	@Override
	public LatLng getLatLng() {

		return LatLng.newInstance(_positionMarker.getLatLng().getLatitude(), _positionMarker.getLatLng().getLongitude());
	}

	@Override
	public void setLatLng(LatLng latLng) {
		_positionMarker.setLatLng(LatLng.newInstance(latLng.getLatitude(), latLng.getLongitude()));
		_lat.setText(""+_positionMarker.getLatLng().getLatitude());
		_lng.setText(""+_positionMarker.getLatLng().getLongitude());
		_map.setCenter(_positionMarker.getLatLng());

	}



}
