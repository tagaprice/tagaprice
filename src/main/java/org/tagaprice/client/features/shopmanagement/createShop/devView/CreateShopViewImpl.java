package org.tagaprice.client.features.shopmanagement.createShop.devView;

import java.util.ArrayList;

import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.generics.widgets.MultipleAddressSelecter;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.Shop;
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
	MultipleAddressSelecter _addresses;

	@UiField
	Button _saveButton;

	@UiField
	FlexTable _receiptEntriesTable;



	public CreateShopViewImpl() {
		initWidget(CreateShopViewImpl.uiBinder.createAndBindUi(this));

	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.features.shopmanagement.createShop.devView.ICreateShopView#setPresenter(org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView.Presenter)
	 */
	@Override
	public void setPresenter(Presenter presenter) {
		_presenter = presenter;
	}

	/* (non-Javadoc)
	 * @see org.tagaprice.client.features.shopmanagement.createShop.devView.ICreateShopView#setTitle(java.lang.String)
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
	 * @see org.tagaprice.client.features.shopmanagement.createShop.devView.ICreateShopView#getTitle()
	 */
	@Override
	public String getShopTitle() {
		return _name.getText();
	}

	@Override
	public void setShopTitle(String title) {
		_name.setText(title);
	}


	@Override
	public void setCurrentAddress(Address address) {
		_addresses.setCurrentAddress(address);
	}

	@Override
	public void addChild(Shop kid) {
		_addresses.addShop(kid);
	}

	@Override
	public ArrayList<Shop> getChildren() {
		return _addresses.getShops();
	}

	@Override
	public void setChildren(ArrayList<Shop> children) {
		_addresses.setShops(children);
	}
}
