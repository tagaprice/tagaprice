package org.tagaprice.client.features.receiptmanagement.createReceipt.desktopView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.event.MapMoveEndListener;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.generics.MapQuest.MapquestCreator;
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
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.core.client.JsArray;
import org.tagaprice.client.generics.MapQuest.MapquestCreator;
import org.tagaprice.client.generics.MapQuest.MapquestResponse;

public class CreateReceiptViewImpl extends Composite implements ICreateReceiptView {

	private Presenter _presenter;
	private StdFrame _frame = new StdFrame();
	private HorizontalPanel _headPanel = new HorizontalPanel();
	private Label _receiptDateLabel = new Label();
	private Label _fullPrice = new Label("0.0€");
	private DateTimeFormat fmt = DateTimeFormat.getFormat(" [dd, MMMM yyyy]");
	private DatePicker _datePicker = new DatePicker();
	private PopupPanel _datePop = new PopupPanel(true);
	private VerticalPanel _bodyPanel = new VerticalPanel();
	private SimplePanel _shopPanel = new SimplePanel();
	private ReceiptEntrySelecter _receiptEntrySelecter = new ReceiptEntrySelecter();
	private Shop _currShop=null;
	private Map _osmMap;
	private Label _receiptEntriesLabel = new Label("Receiptentries");
	
	private TextBox _location = new TextBox();
	private PopupPanel _locationPop = new PopupPanel(true);
	private PopupPanel _seachLoctionPop = new PopupPanel(true);
	private VerticalPanel _locationVePa = new VerticalPanel();
	
	//search
	private TextBox _shopSearchText = new TextBox();
	private TextBox _productSearchText = new TextBox();
	private PopupPanel _productSearchPopup = new PopupPanel(true);
	private VerticalPanel _productSearchResultPanel = new VerticalPanel();
	private VerticalPanel _shopSearchResultPanel = new VerticalPanel();
	private Address _curAddress;
	private VectorOptions _osmVectorOptions = new VectorOptions();
	private Vector _osmLayer;
	private VerticalPanel _searchShopPa = new VerticalPanel();
	private HorizontalPanel _responseMapHoPa = new HorizontalPanel();
	private VerticalPanel _dynLocationVePa = new VerticalPanel();
	
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
		
		
		//buttons
		_frame.setButtonsVisible(true);
		
		//saveButton
		_frame.addSaveClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent();		
			}
		});
		
		
		_frame.setReadOnly(false);
		
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
		
		
		//Draw everyhting
		{
			
			_searchShopPa.setWidth("100%");
			
			HorizontalPanel locationShopHoPa = new HorizontalPanel();
			//locationShopHoPa.setWidth("100%");
			_searchShopPa.add(locationShopHoPa);
			
			
			_responseMapHoPa.setWidth("100%");
			_searchShopPa.add(_responseMapHoPa);
			_responseMapHoPa.setVisible(false);
			
			
			//SearchPart
			VerticalPanel searchBoxVePa = new VerticalPanel();
			searchBoxVePa.setWidth("500px");
			
			
			//SearchPart TextBox
			_shopSearchText.setStyleName("receiptSearchBox");
			_shopSearchText.setEnabled(false);
			_shopSearchText.setText("Please select location first. -->");
			//searchText.setWidth("100%");
			locationShopHoPa.add(_shopSearchText);
			_shopSearchText.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent arg0) {
					_presenter.shopSearchStringHasChanged(_shopSearchText.getText());				
				}
			});
			
			//locatioSearch
			_location.setStyleName("receiptLocationBox");
			_location.setText("Your Location:");
			locationShopHoPa.add(_location);
			
			
			//locationPop
			_locationVePa.setWidth("240px");
			_locationVePa.setStyleName("popBackground");
			_locationPop.setWidget(_locationVePa);
			_locationPop.getElement().getStyle().setZIndex(2000);
			_location.addFocusHandler(new FocusHandler() {
				
				@Override
				public void onFocus(FocusEvent arg0) {
					_locationPop.showRelativeTo(_location);
				}
			});
			_locationVePa.add(_dynLocationVePa);
			
			//search location
			_seachLoctionPop.getElement().getStyle().setZIndex(2000);
			_location.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent key) {
					if(key.getNativeKeyCode() == 13){
						_locationPop.hide();
						
						JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
						jsonp.setCallbackParam("json_callback");
						String url = "http://open.mapquestapi.com/nominatim/v1/search?format=json&addressdetails=1&q=";
						url+=_location.getText();
						
						jsonp.requestObject(url, new AsyncCallback<JsArray<MapquestResponse>>() {

							@Override
							public void onFailure(Throwable e) {
								Log.error("MapquestPointToAdressFailure: "+e);						
							}

							@Override
							public void onSuccess(JsArray<MapquestResponse> response) {
							
								VerticalPanel vePa = new VerticalPanel();
								vePa.setWidth("200px");
								vePa.setStyleName("popBackground");
								_seachLoctionPop.setWidget(vePa);
								
								for(int i=0;i<response.length();i++){
									final Address at = MapquestCreator.getAddressByMapquestResponse(response.get(i));
									at.getPos().setLat(Double.parseDouble(response.get(i).getLat()));
									at.getPos().setLon(Double.parseDouble(response.get(i).getLon()));
									Label l = new Label(at.getStreet()+", "+at.getCity()+", "+at.getCountrycode());
									vePa.add(l);
									
									l.addClickHandler(new ClickHandler() {
										
										@Override
										public void onClick(ClickEvent arg0) {
											addSelectableAddress(at);
											setAddress(at);
											_seachLoctionPop.hide();
										}
									});
									
								}
								
								_seachLoctionPop.showRelativeTo(_location);
								
							}

						});
					}
					
				}
			});
			
			Label locText = new Label("Current Location");
			locText.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					
					//get current location
					Geolocation.getGeolocation().getCurrentPosition(new PositionCallback() {
						
						@Override
						public void onSuccess(Position position) {								
							setLocation(new Address("Current location", position.getCoords().getLatitude(), position.getCoords().getLongitude()));
							setLocation(new Address("Current location", position.getCoords().getLatitude(), position.getCoords().getLongitude()));
						}
						
						@Override
						public void onFailure(PositionError error) {
							Log.error("Could not find position:" + error);
							//_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptViewImpl.class, "Could not find position", INFOTYPE.ERROR));								
						}
					});
					
					
				}
			});
			_locationVePa.add(locText);
				
			
			
			
			//SearchResult
			_shopSearchResultPanel.setWidth("300px");
			//searchBoxVePa.add(_shopSearchResultPanel);
			_responseMapHoPa.add(_shopSearchResultPanel);
			//responseMapHoPa.setCellWidth(_shopSearchResultPanel, "540px");
			setProductSearchVisible(false);
			
			
			
			
			//SearchPart Map
			MapOptions defaultMapOptions = new MapOptions();
			MapWidget omapWidget = new MapWidget("100%", "300px", defaultMapOptions);
			OSM osm_2 = OSM.Mapnik("Mapnik");
			osm_2.setIsBaseLayer(true);
			_osmMap = omapWidget.getMap();
			_osmMap.addLayer(osm_2);
			_osmMap.zoomTo(13);
			
			
			//******** INIT OSM Vector ************/
			//Style
			Style style = new Style();
			style.setStrokeColor("#000000");
			style.setStrokeWidth(2);
			style.setFillColor("#00FF00");
			style.setFillOpacity(0.5);
			style.setPointRadius(8);
			style.setStrokeOpacity(0.8);
			_osmVectorOptions.setStyle(style);	
			
			_osmLayer = new Vector("shopResults",_osmVectorOptions);
			_osmMap.addLayer(_osmLayer);
			
			
			_responseMapHoPa.add(omapWidget);
			_responseMapHoPa.setCellWidth(omapWidget, "100%");
			
			
			_osmMap.addMapMoveEndListener(new MapMoveEndListener() {
				
				@Override
				public void onMapMoveEnd(MapMoveEndEvent eventObject) {
					LonLat c = _osmMap.getCenter();
					c.transform("EPSG:900913", "EPSG:4326");
					
					Log.debug("move: add: "+_curAddress +", point: "+c.lat()+":"+c.lon());
					
					
					if(_curAddress!=null){
						if(_curAddress.getPos().getLat()!=c.lat() || _curAddress.getPos().getLon()!=c.lon()){
							
							setLocation(new Address("Map Area", c.lat(), c.lon()));
						}
					}else if(_curAddress==null){
						setLocation(new Address("Map Area", c.lat(), c.lon()));
					}				
				}
			});
			
			//_shopPanel.setWidget(searchShopHoPa);
			
			//_shopPanel.setWidget(_searchShopPa);
		}
	}
	
	@Override
	public void setSelectableAddress(List<Address> address) {
		
		_dynLocationVePa.clear();
		
		for(final Address a:address){
			Label locText = new Label(a.getStreet()+", "+a.getCity());
			locText.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					setAddress(a);
					setAddress(a);
					_locationPop.hide();
				}
			});
			_dynLocationVePa.add(locText);
		}
	}
	
	private void addSelectableAddress(final Address address){
		Label locText = new Label(address.getStreet()+", "+address.getCity());
		locText.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setAddress(address);
				setAddress(address);
				_locationPop.hide();
			}
		});
		_dynLocationVePa.add(locText);
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
			_shopPanel.setWidget(_searchShopPa);
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
		LonLat lonLat = address.getPos().toLonLat();
		lonLat.transform("EPSG:4326", "EPSG:900913");
		_osmMap.setCenter(lonLat);
		

		_shopSearchText.setEnabled(true);
		_shopSearchText.setText("");
		_responseMapHoPa.setVisible(true);
	}
	
	private void setLocation(Address address){
		Log.debug("setLocation. "+address);
		_curAddress = address;	
		_locationPop.hide();
		_location.setText(_curAddress.getAddress());
		LonLat lonLat = _curAddress.getPos().toLonLat();
		lonLat.transform( "EPSG:4326","EPSG:900913");
		_osmMap.setCenter(lonLat);
		//_searchPopup.showRelativeTo(_search);
		
		
		if(_shopSearchText.isEnabled())
			_presenter.shopSearchStringHasChanged(_shopSearchText.getText());
		else{
			_shopSearchText.setEnabled(true);
			_shopSearchText.setText("");
			_responseMapHoPa.setVisible(true);
		}
	}

	@Override
	public BoundingBox getBoundingBox() {

		LonLat southWest = new LonLat(_osmMap.getExtent().getLowerLeftX(), _osmMap.getExtent().getLowerLeftY());
		LonLat northEast = new LonLat(_osmMap.getExtent().getUpperRightX(), _osmMap.getExtent().getUpperRightY());
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
		_osmLayer.destroyFeatures();
		
		
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
			
			//add marker
			//Simple points
			LonLat lonLat = s.getAddress().getPos().toLonLat();
			lonLat.transform("EPSG:4326", "EPSG:900913");
			Point point = new Point(lonLat.lon(), lonLat.lat());
			VectorFeature pointFeature = new VectorFeature(point);

			_osmLayer.addFeature(pointFeature);
			
			
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
					_presenter.goTo(new CreateShopPlace(null, null, _presenter.getId(), _shopSearchText.getText(), null, ""+_curAddress.getPos().getLat(), ""+_curAddress.getPos().getLon(), ""+_osmMap.getZoom()));
				else _presenter.goTo(new CreateShopPlace(null, null, "draft", _shopSearchText.getText(), null, ""+_curAddress.getPos().getLat(), ""+_curAddress.getPos().getLon(), ""+_osmMap.getZoom()));
				
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
					_presenter.goTo(new CreateProductPlace(null, null, _presenter.getId(), _productSearchText.getText(), null, null, null));
				else _presenter.goTo(new CreateProductPlace(null, null, "draft", _productSearchText.getText(), null, null, null));
				
				_productSearchPopup.hide();
				_productSearchText.setText("");
				
			}
		});
		
		_productSearchResultPanel.add(newPackDump);
		

		_productSearchPopup.showRelativeTo(_productSearchText);		
	}

	@Override
	public List<ReceiptEntry> getReceiptEntries() {
		return _receiptEntrySelecter.getReceiptEntries();
	}

	@Override
	public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {
		_receiptEntrySelecter.setReceiptEntries(receiptEntries);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

}
