package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.gwt.client.generics.ColumnDefinition;
import org.tagaprice.client.gwt.client.generics.widgets.ShopSelecter;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;



public class CreateReceiptViewImpl<T> extends Composite implements ICreateReceiptView<T> {

	MyLogger _logger = LoggerFactory.getLogger(CreateReceiptViewImpl.class);

	interface CreateReceiptViewImplUiBinder extends
	UiBinder<Widget, CreateReceiptViewImpl<?>> {
	}

	private static CreateReceiptViewImplUiBinder uiBinder = GWT
	.create(CreateReceiptViewImplUiBinder.class);

	private Presenter _presenter;

	@UiField
	Label _date18N;
	@UiField
	Label _shopI18N;
	@UiField
	Label _productI18N;

	@UiField
	TextBox _date;
	@UiField
	ShopSelecter _shop;
	@UiField
	VerticalPanel _productsPanel;

	@UiField
	Label _shopLoaded;

	SuggestBox _products;

	@UiField
	TextBox _amount;

	@UiField
	TextBox _unitprice;


	@UiField
	FlexTable _productTable;


	HandlerRegistration shopHr;
	HandlerRegistration productHr;

	ArrayList<ColumnDefinition<T>> columnDefinitions;

	public CreateReceiptViewImpl() {
		this.initWidget(CreateReceiptViewImpl.uiBinder.createAndBindUi(this));



	}


	@UiHandler("_saveButton")
	public void onSaveButtonClicked(ClickEvent event) {
		this._presenter.onSaveEvent();
	}


	@Override
	public long getPrice() {
		String fieldValue = _unitprice.getText();
		int posPoint = fieldValue.indexOf('.');
		String fieldWOpoint = fieldValue.substring(0, posPoint) + fieldValue.substring(posPoint+1, fieldValue.length());

		return Long.parseLong(fieldWOpoint);
	}

	@Override
	public String getProductName() {
		return _products.getText();
	}

	@Override
	public  int getQuantity(){
		return Integer.parseInt(_amount.getText());
	}


	@Override
	public void setPresenter(Presenter presenter) {
		this._presenter = presenter;

	}

	@Override
	public void setSuggestProducts(MultiWordSuggestOracle productList) {
		_logger.log("set oracle... suggestbox");
		this._productsPanel.clear();
		this._products = new SuggestBox(productList);
		this._productsPanel.add(this._products);

		if(productHr != null) {
			productHr.removeHandler();
		}
		productHr = this._products.getTextBox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				_logger.log("productfield changed");
				_presenter.onSelectProduct();
			}
		});
	}


	@Override
	public void addReceiptEntry(ArrayList<T> receiptEntries) {
		this._productTable.removeAllRows();
		this._productTable.insertRow(0);
		this._productTable.getRowFormatter().addStyleName(0,"FlexTable-Header");
		for(int i = 0; i < this.columnDefinitions.size(); i++) {
			this._productTable.setHTML(0, i, this.columnDefinitions.get(i).getColumnName());
		}
		for (int i = 0; i < receiptEntries.size(); i++) {
			T elem = receiptEntries.get(i);
			for (int j = 0; j < this.columnDefinitions.size(); j++) {
				ColumnDefinition<T> actualColumnDefinition = this.columnDefinitions.get(j);
				this._productTable.setWidget(i+1, j, actualColumnDefinition.render(elem));
			}
		}

	}


	@Override
	public void setAvailableShops(ArrayList<IShop> availableShops) {
		this._shop.setAvailableShops(availableShops);

	}


	@Override
	public void setShop(IShop shop) {
		this._shop.setShop(shop);

	}

	@Override
	public IShop getShop() {
		return this._shop.getShop();
	}


	@Override
	public void addShopChangeHanlder(ChangeHandler handler) {
		if(shopHr != null) {
			shopHr.removeHandler();
		}
		shopHr = this._shop.addChangeHandler(handler);
	}


	@Override
	public void shopsLoaded(String loaded) {
		this._shopLoaded.setText(loaded);
	}


	@Override
	public void setQuantity(int quantity) {
		this._amount.setText(String.valueOf(quantity));
	}


	@Override
	public void setPrice(long price) {
		String str = "";
		str = (price / 100) + "." + (price % 100);
		this._unitprice.setText(str);
	}

	public void setColumnDefinitions(ArrayList<ColumnDefinition<T>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	@UiHandler("_addProductButton")
	public void onAddProduct(ClickEvent event) {
		_presenter.onAddEntry();
		_products.setText("");
	}


}
