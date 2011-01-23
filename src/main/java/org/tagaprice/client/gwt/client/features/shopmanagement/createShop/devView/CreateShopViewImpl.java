package org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView;

import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateShopViewImpl extends Composite implements ICreateShopView {
	interface CreateShopViewImpleUiBinder extends
	UiBinder<Widget, CreateShopViewImpl> {
	}

	private static CreateShopViewImpleUiBinder uiBinder = GWT
	.create(CreateShopViewImpleUiBinder.class);

	private Presenter _presenter;


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
	TextBox _lat;

	@UiField
	TextBox _lng;

	@UiField
	Button _saveButton;



	public CreateShopViewImpl() {
		initWidget(CreateShopViewImpl.uiBinder.createAndBindUi(this));


		//Implement listener
		_saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent(arg0);
			}
		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter = presenter;
	}

	@Override
	public void setTitle(String title) {
		_name.setText(title);

	}





	@Override
	public void setStreet(String street) {
		_street.setText(street);

	}

	@Override
	public void setZip(String zip) {
		_zip.setText(zip);

	}

	@Override
	public void setCity(String city) {
		_city.setText(city);
	}

	@Override
	public void setCountry(Country country) {
		_country.setText(country.name());
	}

	@Override
	public void setLat(double lat) {
		_lat.setText(""+lat);
	}

	@Override
	public void setLng(double lng) {
		_lng.setText(""+lng);
	}

	@Override
	public String getTitle() {
		return _name.getText();
	}

	@Override
	public String getStreet() {
		return _street.getText();
	}

	@Override
	public String getZip() {
		return _zip.getText();
	}

	@Override
	public String getCity() {
		return _city.getText();
	}

	@Override
	public Country getCountry() {
		// TODO Auto-generated method stub
		return Country.at;
	}

	@Override
	public double getLat() {
		// TODO Auto-generated method stub
		return Double.parseDouble(_lat.getText());
	}

	@Override
	public double getLng() {
		return Double.parseDouble(_lng.getText());
	}

	@Override
	public IRevisionId getRevisionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRevisionId(IRevisionId revisionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public IShop copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
