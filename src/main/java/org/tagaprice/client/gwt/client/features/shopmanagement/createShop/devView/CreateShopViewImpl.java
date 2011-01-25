package org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView;

import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
	TextBox _lat;

	@UiField
	TextBox _lng;

	@UiField
	Button _saveButton;



	public CreateShopViewImpl() {
		initWidget(CreateShopViewImpl.uiBinder.createAndBindUi(this));
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
		_country.setText(country.name());
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setLat(double)
	 */
	@Override
	public void setLat(double lat) {
		_lat.setText(""+lat);
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#setLng(double)
	 */
	@Override
	public void setLng(double lng) {
		_lng.setText(""+lng);
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

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#getLat()
	 */
	@Override
	public double getLat() {
		// TODO Auto-generated method stub
		return Double.parseDouble(_lat.getText());
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.ICreateShopView#getLng()
	 */
	@Override
	public double getLng() {
		return Double.parseDouble(_lng.getText());
	}

	@Override
	public void setRevisionId(IRevisionId revisionId) {
		if(revisionId != null) {
			this._id.setText(revisionId.getId() + "_" + revisionId.getRevision());
		}else {
			this._id.setText("");
		}
	}



}
