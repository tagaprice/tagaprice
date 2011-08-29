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
import org.tagaprice.client.generics.widgets.desktopView.PackagePreview;
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
import com.google.gwt.user.client.ui.Button;
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
	
	//edit buttons
	private HorizontalPanel _statisticHeadPanel = new HorizontalPanel();
	private Button _cancelButton = new Button("cancel");
	private Button _saveButton = new Button("save");
	private Button _editButton = new Button("edit");
	
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
		
		//Add Save button
		//cancel button
		_cancelButton.setStyleName("stdButton cancel");
		_statisticHeadPanel.add(_cancelButton);
		_statisticHeadPanel.setCellHorizontalAlignment(_cancelButton, HorizontalPanel.ALIGN_RIGHT);
		_cancelButton.setVisible(!_readonly);
		_cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				//TODO implement cancel
				//setReadOnly(true);
			}
		});
		
		
		//saveButton
		_saveButton.setStyleName("stdButton save");
		_statisticHeadPanel.add(_saveButton);
		_statisticHeadPanel.setCellHorizontalAlignment(_saveButton, HorizontalPanel.ALIGN_RIGHT);
		_saveButton.setVisible(_readonly);
		_saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent();		
			}
		});
		
		
		//editButton
		_editButton.setStyleName("stdButton");
		//_statisticHeadPanel.setWidth("100%");
		_statisticHeadPanel.add(_editButton);
		_statisticHeadPanel.setCellHorizontalAlignment(_editButton, HorizontalPanel.ALIGN_RIGHT);
		_editButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				//setReadOnly(false);
			}
		});
		_editButton.setVisible(!_readonly);
		
		
		HorizontalPanel justDoRight = new HorizontalPanel();
		justDoRight.setWidth("100%");
		justDoRight.add(_statisticHeadPanel);
		justDoRight.setCellHorizontalAlignment(_statisticHeadPanel, HorizontalPanel.ALIGN_RIGHT);
		_headPanel.add(justDoRight);
		
		_frame.setHeader(_headPanel);
		
		
		//body
		_bodyPanel.setWidth("100%");
		_frame.setBody(_bodyPanel);
		
		
		//Select Shop
		Label _shopLabel = new Label("Shop");
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

		_productSearchPopup.setWidth("520px");
		_productSearchPopup.setStyleName("popBackground");
		_productSearchPopup.setWidget(_productSearchResultPanel);
		
		initWidget(_frame);
	}
	
	private void drawShopSelected(){
		HorizontalPanel dHoPa = new HorizontalPanel();
		//dHoPa.setWidth("600px");
		ShopPreview _preview = new ShopPreview(_currShop);
		
		
		//Del button
		Button delShop = new Button("remove", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setShop(null);	
			}
		});
		delShop.setStyleName("stdButton cancel");
		//_preview.addHoverWidget(delShop);
		
		
		_preview.setWidth("500px");
		dHoPa.add(_preview);
		
		
		dHoPa.add(delShop);
		
		
		_shopPanel.setWidget(dHoPa);
		//_shopPanel.setWidget(new Label("shop selected: "+_currShop.getTitle()));
	}
	
	private void drawShopSearch(){
		HorizontalPanel searchShopHoPa = new HorizontalPanel();
		searchShopHoPa.setWidth("100%");	
		
		//SearchPart
		VerticalPanel searchBoxVePa = new VerticalPanel();
		searchBoxVePa.setWidth("500px");
		
		
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
		return _currShop;
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
		

		_presenter.checkSave();
		
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
			HorizontalPanel takeShop = new HorizontalPanel();
			takeShop.setWidth("100%");
			
			
			ShopPreview foundShops = new ShopPreview(s);
			
			//add addButton
			Button addButton = new Button("use",new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					setShop(s);		
					
				}
			} );
			addButton.setStyleName("stdButton save");
			foundShops.addHoverWidget(addButton);
			
			takeShop.add(foundShops);
			/*
			foundAddress.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					setShop(s);
				}
			});
			_shopSearchResultPanel.add(foundAddress);
			*/
			/*
			Button addAsShop = new Button("+");
			addAsShop.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					setShop(s);					
				}
			});
			takeShop.add(addAsShop);
			takeShop.setCellWidth(addAsShop, "30px");
			*/
			/*
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
			 */
			
			_shopSearchResultPanel.add(foundShops);



			Log.debug("shopSearchResult: "+s.getTitle());
		}
		
		//new shop
		Shop ns = new Shop(null, null, null, "(new Shop)"+_shopSearchText.getText(), null);
		ShopPreview dumpShop = new ShopPreview(ns);
		dumpShop.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if(_presenter.getId()!=null)
					_presenter.goTo(new CreateShopPlace(null, null, _presenter.getId(), _shopSearchText.getText(), null, null, null, null));
				else _presenter.goTo(new CreateShopPlace(null, null, "draft", _shopSearchText.getText(), null, null, null, null));
				
				_shopSearchText.setText("");							
			}
		});
		_shopSearchResultPanel.add(dumpShop);
		
		
		
	}

	@Override
	public void setProductSearchResults(List<Product> productResults) {
		
		
		_productSearchResultPanel.clear();
		for(final Product p:productResults){
			Log.debug("shopProductResult: "+p.getTitle());
			for(final Package pa: p.getPackages()){
				//HorizontalPanel hoPaFoundPackage = new HorizontalPanel();
				//hoPaFoundPackage.setWidth("100%");
				
				PackagePreview foundProduct = new PackagePreview(pa.getProduct(), pa);
				
				//add button
				Button addButton = new Button("use",new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {
						_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.euro), pa));
						_productSearchPopup.hide();
						_productSearchText.setText("");						
					}
				});
				addButton.setStyleName("stdButton save");
				foundProduct.addHoverWidget(addButton);
				
				//add new package
				Button addPackageButton = new Button("new package", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {

						Package np = new Package(new Quantity(new BigDecimal("0.0"), p.getUnit()));
						np.setProduct(p);

						_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.euro), np));
						_productSearchPopup.hide();
						_productSearchText.setText("");	
						
					}
				});
				addPackageButton.setStyleName("stdButton save");
				foundProduct.addHoverWidget(addPackageButton);
				
				_productSearchResultPanel.add(foundProduct);
				
			}
			
			if(p.getPackages().size()==0){
				
				PackagePreview foundProduct = new PackagePreview(p, null);
				
			
				
				
				//add button
				Button addButton = new Button("new package",new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {
						Package np = new Package(new Quantity(new BigDecimal("0.0"), p.getUnit()));
						np.setProduct(p);
	
						_receiptEntrySelecter.addReceiptEntrie(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.euro), np));
						_productSearchPopup.hide();
						_productSearchText.setText("");						
					}
				});
				addButton.setStyleName("stdButton save");
				foundProduct.addHoverWidget(addButton);
				
				_productSearchResultPanel.add(foundProduct);
			}
			
		}

		
		//new Product
		Product pr = new Product(null, "(new Product) "+_productSearchText.getText(), null, null);
		PackagePreview newPackDump = new PackagePreview(pr, null);
		newPackDump.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {				
				if(_presenter.getId()!=null)
					_presenter.goTo(new CreateProductPlace(null, null, _presenter.getId(), _productSearchText.getText()));
				else _presenter.goTo(new CreateProductPlace(null, null, "draft", _productSearchText.getText()));
				
				_productSearchPopup.hide();
				_productSearchText.setText("");
				
			}
		});
		
		_productSearchResultPanel.add(newPackDump);
		

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
