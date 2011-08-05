package org.tagaprice.client.features.receiptmanagement.createReceipt.desktopView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.generics.widgets.ReceiptEntrySelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.desktopView.ShopPreview;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class CreateReceiptViewImpl extends Composite implements ICreateReceiptView {

	private Presenter _presenter;
	private StdFrame _frame = new StdFrame();
	private HorizontalPanel _headPanel = new HorizontalPanel();
	private Label _receiptDateLabel = new Label();
	private Label _fullPrice = new Label("0.0€");
	private DateTimeFormat fmt = DateTimeFormat.getFormat(" [dd, MMMM yyyy]");
	private DatePicker _datePicker = new DatePicker();
	private boolean _readonly = true;
	private PopupPanel _datePop = new PopupPanel(true);
	private VerticalPanel _bodyPanel = new VerticalPanel();
	private SimplePanel _shopPanel = new SimplePanel();
	private ReceiptEntrySelecter _receiptEntrySelecter = new ReceiptEntrySelecter();
	private Shop _currShop=null;
	private Map _osmMap;
	private Label _receiptEntriesLabel = new Label("Receiptentries");
	
	
	//search
	private TextBox _shopSearchText = new TextBox();
	private TextBox _productSearchText = new TextBox();
	private PopupPanel _productSearchPopup = new PopupPanel(true);
	private VerticalPanel _productSearchResultPanel = new VerticalPanel();
	private VerticalPanel _shopSearchResultPanel = new VerticalPanel();
	
	public CreateReceiptViewImpl() {
		
		
		//head
		_headPanel.setWidth("100%");
		_headPanel.add(new Label("Receipt"));
		
		
		//Date
		_headPanel.add(_receiptDateLabel);
		_headPanel.setCellWidth(_receiptDateLabel, "100%");
		_datePop.setWidget(_datePicker);
		_datePop.setStyleName("popBackground");
		_receiptDateLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_datePop.showRelativeTo(_receiptDateLabel);
			}
		});
		
		_datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> date) {
				setDate(date.getValue());	
				_datePop.hide();
			}
		});

		_headPanel.add(_fullPrice);
		_frame.setHeader(_headPanel);
		
		
		//body
		_bodyPanel.setWidth("100%");
		_frame.setBody(_bodyPanel);
		
		
		//Select Shop
		Label _shopLabel = new Label("Shop");
		_shopLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.goTo(new CreateShopPlace(null, null, false));
				
			}
		});
		_shopLabel.setStyleName("propertyHeader");
		_bodyPanel.add(_shopLabel);
		
		//Search and select Shop
		_shopPanel.setWidth("100%");
		_bodyPanel.add(_shopPanel);
		
		
		
		//Select Products
		_receiptEntriesLabel.setStyleName("propertyHeader");
		_bodyPanel.add(_receiptEntriesLabel);
		
		
		//ReceiptEntries
		_bodyPanel.add(_receiptEntrySelecter);
		
		
		//entry search
		_productSearchText.setStyleName("receiptSearchBox");
		_bodyPanel.add(_productSearchText);
		_productSearchText.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				_presenter.productSearchStringHasChanged(_productSearchText.getText());				
			}
		});
		_productSearchPopup.setStyleName("popBackground");
		_productSearchPopup.setWidget(_productSearchResultPanel);
		
		initWidget(_frame);
	}
	
	private void drawShopSelected(){
		ShopPreview _preview = new ShopPreview(_currShop);
		_preview.setWidth("500px");
		_shopPanel.setWidget(_preview);
		//_shopPanel.setWidget(new Label("shop selected: "+_currShop.getTitle()));
	}
	
	private void drawShopSearch(){
		//TODO Make this nice late ;-)
		HorizontalPanel searchShopHoPa = new HorizontalPanel();
		searchShopHoPa.setWidth("100%");	
		
		//SearchPart
		VerticalPanel searchBoxVePa = new VerticalPanel();
		searchBoxVePa.setWidth("100%");
		
		
		//SearchPart TextBox
		_shopSearchText.setStyleName("receiptSearchBox");
		//searchText.setWidth("100%");
		searchBoxVePa.add(_shopSearchText);
		_shopSearchText.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				_presenter.shopSearchStringHasChanged(_shopSearchText.getText());				
			}
		});
		
		//SearchResult
		_shopSearchResultPanel.setWidth("100%");
		searchBoxVePa.add(_shopSearchResultPanel);
		searchShopHoPa.add(searchBoxVePa);
		setProductSearchVisible(false);
		
		//SearchPart Map
		MapOptions defaultMapOptions = new MapOptions();
		MapWidget omapWidget = new MapWidget("100%", "200px", defaultMapOptions);
		OSM osm_2 = OSM.Mapnik("Mapnik");
		osm_2.setIsBaseLayer(true);
		_osmMap = omapWidget.getMap();
		_osmMap.addLayer(osm_2);
		_osmMap.zoomTo(16);
		searchShopHoPa.add(omapWidget);
		searchShopHoPa.setCellWidth(omapWidget, "300px");
		
		_shopPanel.setWidget(searchShopHoPa);
	}
	
	@Override
	public Date getDate() {
		return _datePicker.getValue();
	}

	@Override
	public void setDate(Date date) {
		_receiptDateLabel.setText(fmt.format(date));
		_datePicker.setValue(date,true);
	}

	@Override
	public Shop getShop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShop(Shop shop) {
		_currShop=shop;
		
		if(_currShop==null){
			drawShopSearch();
			setProductSearchVisible(false);
		}else{
			drawShopSelected();
			setProductSearchVisible(true);
		}
		
		
	}
	
	private void setProductSearchVisible(boolean visible){
		_receiptEntriesLabel.setVisible(visible);
		_receiptEntrySelecter.setVisible(visible);
		_productSearchText.setVisible(visible);
	}

	@Override
	public void setAddress(Address address) {
		Log.debug("new Address: "+address);
		LonLat l = new LonLat(address.getLng(), address.getLat());
		l.transform("EPSG:4326", "EPSG:900913");
		_osmMap.setCenter(l);
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
		_shopSearchResultPanel.clear();
		
		for(final Shop s:shopResults){


			Label foundAddress = new Label(s.getTitle()+" "+s.getAddress().getAddress());
			foundAddress.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					setShop(s);
				}
			});
			_shopSearchResultPanel.add(foundAddress);

			Label newAddress = new Label(s.getTitle()+" (Add Address)");
			newAddress.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					if(_presenter.getId()!=null)
						_presenter.goTo(new CreateShopPlace(_presenter.getId(), s.getId(), true));
					else _presenter.goTo(new CreateShopPlace("draft", s.getId(), true));
				}
			});

			_shopSearchResultPanel.add(newAddress);





			Log.debug("shopSearchResult: "+s.getTitle());
		}
		//new shop
		Label foundAddress = new Label("New Shop");
		foundAddress.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				if(_presenter.getId()!=null)
					_presenter.goTo(new CreateShopPlace(_presenter.getId(), null, true));
				else _presenter.goTo(new CreateShopPlace("draft", null, true));
			}
		});
		_shopSearchResultPanel.add(foundAddress);

		//_shopSearchSuggestPop.setPopupPosition( _shopSearch.getAbsoluteLeft()+300,_shopSearch.getAbsoluteTop());
		//_shopSearchSuggestPop.setPopupPosition(50, 50);
		
	}

	@Override
	public void setProductSearchResults(List<Product> productResults) {
		
		// TODO Auto-generated method stub
		
		_productSearchResultPanel.clear();
		for(final Product p:productResults){
			Log.debug("shopProductResult: "+p.getTitle());
			for(final Package pa: p.getPackages()){
				//Label clickProduct = new Label(pa.getProduct().getTitle()+" - "+pa.getQuantity().getQuantity()+""+pa.getQuantity().getUnit().getTitle());

				Label clickProduct = new Label(pa.getProduct().getTitle()+" - "+pa.getQuantity().getQuantity()+""+pa.getQuantity().getUnit().getTitle());

				_productSearchResultPanel.add(clickProduct);

				clickProduct.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.dkk), pa));
						_productSearchPopup.hide();
						_productSearchText.setText("");
					}
				});
			}
			//Label newPackage = new Label(p.getTitle()+" - x "+p.getUnit().getTitle());
			Label newPackage = new Label(p.getTitle()+" - x ");
			_productSearchResultPanel.add(newPackage);
			newPackage.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					Package np = new Package(new Quantity(new BigDecimal("0.0"), p.getUnit()));
					np.setProduct(p);

					_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.dkk), np));
					_productSearchPopup.hide();
					_productSearchText.setText("");
				}
			});
		}

		//new shop
		Label newShop = new Label("New Product");
		newShop.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				if(_presenter.getId()!=null)
					_presenter.goTo(new CreateProductPlace(_presenter.getId(), true));
				else _presenter.goTo(new CreateProductPlace("draft", true));
				
				_productSearchPopup.hide();
				_productSearchText.setText("");
			}
		});
		_productSearchResultPanel.add(newShop);
		_productSearchPopup.showRelativeTo(_productSearchText);		
	}

	@Override
	public ArrayList<ReceiptEntry> getReceiptEntries() {
		return _receiptEntrySelecter.getReceiptEntries();
	}

	@Override
	public void setReceiptEntries(ArrayList<ReceiptEntry> receiptEntries) {
		_receiptEntrySelecter.setReceiptEntries(receiptEntries);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

}
