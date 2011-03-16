package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView;

import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.gwt.client.generics.widgets.ReceiptEntrySelecter;
import org.tagaprice.client.gwt.shared.entities.BoundingBox;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;

public class CreateReceiptViewImpl extends Composite implements ICreateReceiptView {
	interface CreateReceiptViewImplUiBinder extends UiBinder<Widget, CreateReceiptViewImpl>{}
	private static CreateReceiptViewImplUiBinder uiBinder = GWT.create(CreateReceiptViewImplUiBinder.class);
	private MapWidget _searchMap = new MapWidget();
	private Presenter _presenter;
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateReceiptViewImpl.class);




	@UiField
	TextBox _title;

	@UiField
	DatePicker _date;

	@UiField
	Label _shop;

	@UiField
	TextBox _shopSearch;

	@UiField
	TextBox _searchProducts;

	@UiField
	ReceiptEntrySelecter _receiptEntrySelecter;

	@UiField
	SimplePanel _searchMapArea;

	public CreateReceiptViewImpl() {
		initWidget(CreateReceiptViewImpl.uiBinder.createAndBindUi(this));
		_searchMapArea.setWidget(_searchMap);
		_searchMap.setSize("100%", "70px");


		//SearchShop
		_shopSearch.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent e) {
				_presenter.shopSearchStringHasChanged(_shopSearch.getText());
			}
		});

		_searchProducts.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent arg0) {
				_presenter.productSearchStringHasChanged(_searchProducts.getText());
			}
		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;

	}

	@Override
	public void setTitle(String title) {
		_title.setText(title);

	}

	@Override
	public String getTitle() {
		return _title.getText();
	}

	@Override
	public Date getDate() {
		return _date.getValue();
	}

	@Override
	public void setDate(Date date) {
		_date.setValue(date);
	}

	@Override
	public IAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(IAddress address) {

		if(address==null)
			_shop.setText("");
		else
			_shop.setText(address.getShop().getTitle()+" "+address.getStreet());

	}


	@Override
	public ArrayList<IReceiptEntry> getReceiptEntries() {
		return _receiptEntrySelecter.getReceiptEntries();
	}

	@Override
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries) {
		_receiptEntrySelecter.setReceiptEntries(receiptEntries);

	}

	@Override
	public BoundingBox getBoundingBox() {
		return new BoundingBox(
				_searchMap.getBounds().getSouthWest().getLatitude(),
				_searchMap.getBounds().getSouthWest().getLongitude(),
				_searchMap.getBounds().getNorthEast().getLatitude(),
				_searchMap.getBounds().getNorthEast().getLongitude());


	}

	@Override
	public void setShopSearchResults(ArrayList<IShop> shopResults) {
		for(IShop s:shopResults)
			CreateReceiptViewImpl._logger.log("shopSearchResult: "+s.getTitle());

	}

	@Override
	public void setProductSearchResults(ArrayList<IProduct> productResults) {
		for(IProduct p:productResults)
			CreateReceiptViewImpl._logger.log("shopProductResult: "+p.getTitle());


	}




}
