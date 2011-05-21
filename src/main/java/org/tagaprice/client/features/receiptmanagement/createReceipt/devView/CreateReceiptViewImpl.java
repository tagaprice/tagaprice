package org.tagaprice.client.features.receiptmanagement.createReceipt.devView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.generics.widgets.AddressSelecter;
import org.tagaprice.client.generics.widgets.ReceiptEntrySelecter;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
	private Presenter _presenter;
	private VerticalPanel _shopSearchSuggestVePa = new VerticalPanel();
	private PopupPanel _shopSearchSuggestPop = new PopupPanel(true);
	private VerticalPanel _productSearchSuggestVePa = new VerticalPanel();
	private PopupPanel _productSearchSuggestPop = new PopupPanel(true);
	private Map _osmMap;



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
	Shop _currShop=null;

	public CreateReceiptViewImpl() {
		initWidget(CreateReceiptViewImpl.uiBinder.createAndBindUi(this));
		//OSM

		MapOptions defaultMapOptions = new MapOptions();

		org.gwtopenmaps.openlayers.client.MapWidget omapWidget = new org.gwtopenmaps.openlayers.client.MapWidget("200px", "200px", defaultMapOptions);
		OSM osm_2 = OSM.Mapnik("Mapnik");
		osm_2.setIsBaseLayer(true);
		_osmMap = omapWidget.getMap();
		_osmMap.addLayer(osm_2);
		omapWidget.setSize("100%", "150px");


		_searchMapArea.setWidget(omapWidget);


		//SearchShop
		_shopSearch.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent e) {
				_presenter.shopSearchStringHasChanged(_shopSearch.getText());
			}
		});

		_searchProducts.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				_presenter.productSearchStringHasChanged(_searchProducts.getText());
			}
		});


		_shopSearchSuggestPop.setWidget(_shopSearchSuggestVePa);
		_productSearchSuggestPop.setWidget(_productSearchSuggestVePa);


		_shopSearchSuggestPop.showRelativeTo(_shopSearch);
		_productSearchSuggestPop.showRelativeTo(_searchProducts);

		DOM.setStyleAttribute(_shopSearchSuggestPop.getElement(), "zIndex", "100000");
		DOM.setStyleAttribute(_productSearchSuggestPop.getElement(), "zIndex", "100000");


		//_newAddressArea.setWidget(_addressSelecter);
	}

	@UiHandler("_saveButton")
	public void onSaveEvent(ClickEvent event) {
		_presenter.onSaveEvent();
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
		Log.debug("Date: "+date);
		_date.setValue(date);
	}

	@Override
	public Shop getShop() {
		return _currShop;
	}

	@Override
	public void setShop(Shop shop) {
		_currShop=shop;
		_shopHolder.clear();
		if(_currShop==null){
			_shopSearch.setEnabled(true);

			if(_shopHolder!=null)_shopHolder.clear();
			if(_shopSearch!=null)_shopSearch.setText("");
			if(_saveButton!=null)_saveButton.setEnabled(false);
			if(_shopSearchSuggestPop!=null)_shopSearchSuggestPop.setAutoHideEnabled(true);

			if(_searchMapArea!=null)_searchMapArea.setVisible(true);
			if(_shopSearch!=null)_shopSearch.setEnabled(true);
			_newAddressArea.setVisible(false);
		}else{
			_searchMapArea.setVisible(false);
			_shopSearch.setEnabled(false);
			_newAddressArea.setVisible(false);
			_saveButton.setEnabled(true);
			_shopHolder.add(new Label(shop.getTitle()));
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
	public ArrayList<ReceiptEntry> getReceiptEntries() {
		return _receiptEntrySelecter.getReceiptEntries();
	}

	@Override
	public void setReceiptEntries(ArrayList<ReceiptEntry> receiptEntries) {
		_searchProducts.setText("");
		_receiptEntrySelecter.setReceiptEntries(receiptEntries);

	}

	@Override
	public BoundingBox getBoundingBox() {

		LonLat southWest = new LonLat(_osmMap.getExtent().getLowerLeftY(), _osmMap.getExtent().getLowerLeftX());
		LonLat northEast = new LonLat(_osmMap.getExtent().getUpperRightY(), _osmMap.getExtent().getUpperRightX());
		southWest.transform("EPSG:900913","EPSG:4326");
		northEast.transform("EPSG:900913","EPSG:4326");



		return new BoundingBox(
				southWest.lat(),
				southWest.lon(),
				northEast.lat(),
				northEast.lon());


	}

	@Override
	public void setShopSearchResults(List<Shop> shopResults) {
		_shopSearchSuggestVePa.clear();
		for(final Shop s:shopResults){

			if(s.getParent()!=null){
				Label foundAddress = new Label(s.getTitle()+" "+s.getAddress().getAddress());
				foundAddress.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						setShop(s);
					}
				});
				_shopSearchSuggestVePa.add(foundAddress);
			}else{
				Label foundAddress = new Label(s.getTitle()+" (Add Address)");
				foundAddress.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						_newAddressArea.clear();
						_searchMapArea.setVisible(false);
						_shopSearch.setEnabled(false);

						if(_addressSelecter==null)_addressSelecter = new AddressSelecter();
						LonLat t = _osmMap.getCenter();
						t.transform("EPSG:900913","EPSG:4326");
						_addressSelecter.setAddress(new Address("", t.lat(), t.lon()));

						_newAddressArea.add(new Label("Shop: "+s.getTitle()));
						_newAddressArea.add(_addressSelecter);
						_newAddressArea.add(new Button("Add Address", new ClickHandler() {
							@Override
							public void onClick(ClickEvent e) {
								Shop ia = new Shop(null, s.getTitle()+" - "+_addressSelecter.getAddress().getAddress());
								ia.setAddress(_addressSelecter.getAddress());
								ia.setParent(s);
								setShop(ia);
							}
						}));
						_newAddressArea.setVisible(true);



					}
				});
				_shopSearchSuggestVePa.add(foundAddress);
			}



			Log.debug("shopSearchResult: "+s.getTitle());
		}

		_shopSearchSuggestPop.showRelativeTo(_shopSearch);

		//_shopSearchSuggestPop.setPopupPosition( _shopSearch.getAbsoluteLeft()+300,_shopSearch.getAbsoluteTop());
		//_shopSearchSuggestPop.setPopupPosition(50, 50);

	}

	@Override
	public void setProductSearchResults(List<Product> productResults) {
		_productSearchSuggestVePa.clear();
		for(final Product p:productResults){
			Log.debug("shopProductResult: "+p.getTitle());
			for(final Package pa: p.getPackages()){
				Label clickProduct = new Label(pa.getProduct().getTitle()+" - "+pa.getQuantity().getQuantity()+""+pa.getQuantity().getUnit().getTitle());

				_productSearchSuggestVePa.add(clickProduct);

				clickProduct.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.dkk), pa));
					}
				});
			}
			Label newPackage = new Label(p.getTitle()+" - x "+p.getUnit().getTitle());
			_productSearchSuggestVePa.add(newPackage);
			newPackage.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					Package np = new Package(new Quantity(new BigDecimal("0.0"), p.getUnit()));
					np.setProduct(p);

					_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.dkk), np));
				}
			});
		}
		_productSearchSuggestPop.showRelativeTo(_searchProducts);


	}

	@Override
	public void setAddress(Address address) {
		Log.debug("new Address: "+address);
		LonLat l = new LonLat(address.getLng(), address.getLat());
		l.transform("EPSG:4326", "EPSG:900913");
		_osmMap.setCenter(l, 15);
	}




}
