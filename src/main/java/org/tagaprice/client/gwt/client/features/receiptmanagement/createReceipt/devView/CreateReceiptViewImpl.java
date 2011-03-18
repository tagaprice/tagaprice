package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView;

import java.util.ArrayList;
import java.util.Date;
import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.gwt.client.generics.widgets.AddressSelecter;
import org.tagaprice.client.gwt.client.generics.widgets.ReceiptEntrySelecter;
import org.tagaprice.client.gwt.shared.entities.BoundingBox;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.Currency;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.Price;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class CreateReceiptViewImpl extends Composite implements ICreateReceiptView {
	interface CreateReceiptViewImplUiBinder extends UiBinder<Widget, CreateReceiptViewImpl>{}
	private static CreateReceiptViewImplUiBinder uiBinder = GWT.create(CreateReceiptViewImplUiBinder.class);
	private MapWidget _searchMap = new MapWidget();
	private Presenter _presenter;
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateReceiptViewImpl.class);
	private VerticalPanel _shopSearchSuggestVePa = new VerticalPanel();
	private PopupPanel _shopSearchSuggestPop = new PopupPanel();
	private VerticalPanel _productSearchSuggestVePa = new VerticalPanel();
	private PopupPanel _productSearchSuggestPop = new PopupPanel();




	@UiField
	TextBox _title;

	@UiField
	DatePicker _date;

	@UiField
	HorizontalPanel _shopHolder;

	@UiField
	TextBox _shopSearch;

	@UiField
	TextBox _searchProducts;

	@UiField
	ReceiptEntrySelecter _receiptEntrySelecter;

	@UiField
	SimplePanel _searchMapArea;

	@UiField
	VerticalPanel _newAddressArea;

	@UiField
	Button _saveButton;

	AddressSelecter _addressSelecter;
	IAddress _currAddress=null;

	public CreateReceiptViewImpl() {
		initWidget(CreateReceiptViewImpl.uiBinder.createAndBindUi(this));
		_searchMapArea.setWidget(_searchMap);
		_searchMap.setSize("100%", "70px");
		_saveButton.setEnabled(false);

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


		_shopSearchSuggestPop.setWidget(_shopSearchSuggestVePa);
		_shopSearchSuggestPop.setAutoHideEnabled(true);
		_productSearchSuggestPop.setWidget(_productSearchSuggestVePa);
		_productSearchSuggestPop.setAutoHideEnabled(true);

		_shopSearchSuggestPop.showRelativeTo(_shopSearch);
		_productSearchSuggestPop.showRelativeTo(_searchProducts);

		DOM.setStyleAttribute(_shopSearchSuggestPop.getElement(), "zIndex", "100000");
		DOM.setStyleAttribute(_productSearchSuggestPop.getElement(), "zIndex", "100000");


		//_newAddressArea.setWidget(_addressSelecter);
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
		return _currAddress;
	}

	@Override
	public void setAddress(IAddress address) {
		_currAddress=address;
		System.out.println("setAddress: "+address);
		if(_currAddress==null)
			_shopHolder.clear();
		else{
			_searchMapArea.setVisible(false);
			_shopSearch.setEnabled(false);
			_newAddressArea.setVisible(false);
			_saveButton.setEnabled(true);
			_shopHolder.add(new Label(address.getShop().getTitle()+" "+address.getStreet()));
			_shopHolder.add(new Button("-", new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_searchMapArea.setVisible(true);
					_shopSearch.setEnabled(true);
					_saveButton.setEnabled(false);
					_shopHolder.clear();

				}
			}));
			_shopSearchSuggestPop.hide();
		}


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
		_shopSearchSuggestVePa.clear();
		for(final IShop s:shopResults){
			for(final IAddress a:s.getAddresses()){
				Label foundAddress = new Label(s.getTitle()+" "+a.getStreet());


				foundAddress.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						setAddress(a);
					}
				});
				_shopSearchSuggestVePa.add(foundAddress);
			}

			Label createNewAddress = new Label(s.getTitle()+" (Add Address)");
			createNewAddress.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					_newAddressArea.clear();
					_searchMapArea.setVisible(false);
					_shopSearch.setEnabled(false);

					if(_addressSelecter==null)_addressSelecter = new AddressSelecter();

					_newAddressArea.add(new Label("Shop: "+s.getTitle()));
					_newAddressArea.add(_addressSelecter);
					_newAddressArea.add(new Button("Add Address", new ClickHandler() {
						@Override
						public void onClick(ClickEvent e) {
							IAddress ia= _addressSelecter.getAddress();
							ia.setShop(s);
							setAddress(ia);
						}
					}));
					_newAddressArea.setVisible(true);



				}
			});
			_shopSearchSuggestVePa.add(createNewAddress);


			CreateReceiptViewImpl._logger.log("shopSearchResult: "+s.getTitle());
		}

		_shopSearchSuggestPop.showRelativeTo(_shopSearch);

		//_shopSearchSuggestPop.setPopupPosition( _shopSearch.getAbsoluteLeft()+300,_shopSearch.getAbsoluteTop());
		//_shopSearchSuggestPop.setPopupPosition(50, 50);

	}

	@Override
	public void setProductSearchResults(ArrayList<IProduct> productResults) {
		_productSearchSuggestVePa.clear();
		for(IProduct p:productResults){
			CreateReceiptViewImpl._logger.log("shopProductResult: "+p.getTitle());
			for(final IPackage pa: p.getPackages()){
				Label clickProduct = new Label(pa.getProduct().getTitle()+" - "+pa.getQuantity().getQuantity()+""+pa.getQuantity().getUnit());

				_productSearchSuggestVePa.add(clickProduct);

				clickProduct.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(0, Currency.dkk), pa));
					}
				});
			}
			_productSearchSuggestVePa.add(new Label(p.getTitle()));
		}
		_productSearchSuggestPop.showRelativeTo(_searchProducts);


	}




}
