package org.tagaprice.client.desktopView;

import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.event.MapMoveEndListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.IUi;
import org.tagaprice.client.features.accountmanagement.login.LoginPresenter;
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.LoginChangeEventHandler;
import org.tagaprice.client.generics.widgets.InfoBox;
import org.tagaprice.client.generics.widgets.desktopView.PackagePreview;
import org.tagaprice.client.generics.widgets.desktopView.ShopPreview;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UIDesktop implements IUi {

	private VerticalPanel vePa1 = new VerticalPanel();
	private HorizontalPanel menu = new HorizontalPanel();
	private SimplePanel center = new SimplePanel();
	private HorizontalPanel bottom = new HorizontalPanel();
	private InfoBox _infoBox = new InfoBox();
	private HorizontalPanel _searchHoPa = new HorizontalPanel();
	private TextBox _search = new TextBox();
	private TextBox _location = new TextBox();
	private PopupPanel _locationPop = new PopupPanel(true);
	private VerticalPanel _locationVePa = new VerticalPanel();
	private PopupPanel _infoBoxPopUp = new PopupPanel();
	private ActivityManager _activityManager;
	private ClientFactory _clientFactory;
	private PopupPanel loginPop = new PopupPanel(true);
	private Address _curAddress;
	
	private PopupPanel _searchPopup = new PopupPanel(true);
	private VerticalPanel _shopProductSearchPanel = new VerticalPanel();
	private VerticalPanel _shopSearchPanel = new VerticalPanel();
	private VerticalPanel _productSearchPanel = new VerticalPanel();
	private int _searchCount=0;
	
	//map
	private MapOptions _osmShopOptions = new MapOptions();
	private MapWidget _osmShopWidget; 
	private Map _osmShopMap;
	private VectorOptions _osmVectorOptions = new VectorOptions();
	private Vector _osmLayer;

	private void init(){
		{
		vePa1.setWidth("100%");
		
		//infobox
		//Add InfoBox Popup
		_infoBoxPopUp.setWidget(_infoBox);
		_infoBoxPopUp.show();
		


		//INfo test
		//TODO Find out why setWidth(100%) is not working
		_infoBox.setWidth((Window.getClientWidth()-20)+"px");
		
		
		
		
		//menu
		//menu.setSize("100%", "30px");
		menu.setStyleName("header");
		vePa1.add(menu);
		
		//search
		_searchHoPa.add(_search);
		_searchHoPa.add(_location);
		SimplePanel nothing = new SimplePanel();
		menu.add(nothing);
		menu.setCellWidth(nothing, "1%");
		
		
		
		
		//search
		_search.setStyleName("header-search");
		_search.setText("");
		menu.add(_searchHoPa);
		menu.setCellHorizontalAlignment(_searchHoPa, HorizontalPanel.ALIGN_CENTER);
		
		
		//location Search
		//_location.setWidth("200px");
		
		_location.setStyleName("header-location");
		_location.setText("Your Location: ");
		
		_shopProductSearchPanel.setStyleName("popBackground");
		_shopProductSearchPanel.setWidth("750px");
		
		_productSearchPanel.setWidth("100%");
		
		//locationPop
		_locationVePa.setWidth("200px");
		_locationVePa.setStyleName("popBackground");
		_locationPop.setWidget(_locationVePa);
		_location.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent arg0) {
				_locationPop.showRelativeTo(_location);
				
			}
		});
		
		
		//TODO Add old locations from session or loggedin user
		{
			{
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
								_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(UIDesktop.class, "Could not find position", INFOTYPE.ERROR));								
							}
						});
						
						
					}
				});
				_locationVePa.add(locText);
			}
			
			{
				Label locText = new Label("Flossgasse, Wien");
				locText.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {
						setLocation(new Address("Flossgasse, Wien", 48.21657, 16.37456));
						setLocation(new Address("Flossgasse, Wien", 48.21657, 16.37456));
					}
				});
				_locationVePa.add(locText);
			}
			
			{
				Label locText = new Label("Anywhere");
				locText.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {
						setLocation(new Address("Anywhere", 0, 0));
						setLocation(new Address("Anywhere", 0, 0));
					}
				});
				_locationVePa.add(locText);
			}
			
		}
		
		
		//Shop Search
		HorizontalPanel searchHoPaWithMap = new HorizontalPanel();
		searchHoPaWithMap.setWidth("100%");
		_shopSearchPanel.setWidth("100%");
		searchHoPaWithMap.add(_shopSearchPanel);
		
		
		
		//search map
		OSM osmLayler = OSM.Mapnik("Mapnik");
		osmLayler.setIsBaseLayer(true);
		_osmShopWidget = new MapWidget("100%", "200px", _osmShopOptions);
		_osmShopMap = _osmShopWidget.getMap();
		_osmShopMap.addLayer(osmLayler);
		
		
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
		_osmShopMap.addLayer(_osmLayer);
		_osmShopMap.zoomTo(13);
		searchHoPaWithMap.add(_osmShopWidget);
		searchHoPaWithMap.setCellWidth(_osmShopWidget, "300px");
		
		_shopProductSearchPanel.add(searchHoPaWithMap);

		_shopProductSearchPanel.add(_productSearchPanel);
		
		
		
		_osmShopMap.addMapMoveEndListener(new MapMoveEndListener() {
			
			@Override
			public void onMapMoveEnd(MapMoveEndEvent eventObject) {
				

				LonLat c = _osmShopMap.getCenter();
				c.transform("EPSG:900913", "EPSG:4326");
				
				Log.debug("move: add: "+_curAddress +", point: "+c.lat()+":"+c.lon());
				
				
				if(_curAddress!=null){
					if(_curAddress.getLat()!=c.lat() || _curAddress.getLon()!=c.lon()){
						
						setLocation(new Address("Map Area", c.lat(), c.lon()));
					}
				}else if(_curAddress==null){
					setLocation(new Address("Map Area", c.lat(), c.lon()));
				}
					
				
			}
		});
		
		
		_searchPopup.getElement().getStyle().setZIndex(2000);
		_searchPopup.setWidget(_shopProductSearchPanel);
		
		
		//Open pop
		_search.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
					_searchPopup.showRelativeTo(_search);
			}
		});
		
		//Implement Searching
		_search.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				search(_search.getText());
			}
		});
		
			
		
		//final Label add Receipt
		final Label addReceipt = new Label("add Receipt");
		addReceipt.setStyleName("login");
		menu.add(addReceipt);
		menu.setCellHorizontalAlignment(addReceipt, HorizontalPanel.ALIGN_RIGHT);
		menu.setCellWidth(addReceipt, "1%");
		addReceipt.setVisible(false);
		addReceipt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_clientFactory.getPlaceController().goTo(new CreateReceiptPlace());
			}
		});
		
		
		//login
		final Label login = new Label("Sign in");
		login.setStyleName("login");
		//Button login = new Button("Sign in");
		//$(login).as(gwtquery.plugins.ui.Ui.Ui).button(gwtquery.plugins.ui.widgets.Button.Options.create().icons(gwtquery.plugins.ui.widgets.Button.Icons.create().secondary("ui-icon-triangle-1-s"))); //
		menu.add(login);
		menu.setCellHorizontalAlignment(login, HorizontalPanel.ALIGN_RIGHT);
		menu.setCellWidth(login, "1%");
		
		
		loginPop.getElement().getStyle().setZIndex(2000);

		final LoginPresenter loginPres = new LoginPresenter(_clientFactory);
		
		final VerticalPanel vePaLoggedIn = new VerticalPanel();
		vePaLoggedIn.setStyleName("loginView");
		vePaLoggedIn.add(new HTML("<a href=\"#ListReceipts:/show\">My Receipts</a>"));
		vePaLoggedIn.add(new HTML("<a href=\"#CreateReceipt:/create\">add Receipts</a>"));
		HTML logout = new HTML("<a>Logout</a>");
		logout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_clientFactory.getAccountPersistor().logout();						
			}
		});
		vePaLoggedIn.add(logout);
		
		login.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if(_clientFactory.getAccountPersistor().isLoggedIn()){
					loginPop.setWidget(vePaLoggedIn);
				}else{
					loginPop.setWidget(loginPres.getView());
				}
				loginPop.showRelativeTo(login);				
			}
		});
		
		
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE , 
				new LoginChangeEventHandler() {
					
					@Override
					public void onLoginChange(LoginChangeEvent event) {
						if(_clientFactory.getAccountPersistor().isLoggedIn()){
							login.setText("Budget Book");
							addReceipt.setVisible(true);
						}else{
							login.setText("Sign in");
							addReceipt.setVisible(false);
						}
						
					}
				});
		
		
		
		
		
		//center
		center.setStyleName("center");
		vePa1.add(center);
		vePa1.setCellHorizontalAlignment(center, VerticalPanel.ALIGN_CENTER);
		
		//center.setHeight((Window.getClientHeight()-240-120)+"px");
		//vePa1.setCellHeight(center, "100%");
		
		
		
		//bottom
		bottom.setStyleName("bottom");
		vePa1.add(bottom);
		
		//bottom Text
		HorizontalPanel bottomText = new HorizontalPanel();
		bottomText.setStyleName("bottom-text");
		HTML lefthtml = new HTML("" +
				"<h2>Licence</h2> " +
				"<a href=\"http://creativecommons.org/licenses/by-sa/3.0/\">Creative Commons Attribution-ShareAlike 3.0 Unported License</a> <br /> " +
				"<a href=\"http://www.gnu.org/licenses/agpl.html\">AGPLv3</a> " +
				"<h2>Blog</h2> " +
				"<a href=\"http://blog.tagaprice.org/\">blog.tagaprice.org</a> " +
				"<h2>Development</h2> " +
				"<a href=\"http://github.com/tagaprice\">api.tagaprice.org</a> <br /> " +
				"<a href=\"http://github.com/tagaprice\">http://github.com/tagaprice</a>");
		HTML righthtml = new HTML("" +
				"<h2>Email</h2> " +
				"team[at]tagaprice[dot]org " +
				"<h2>Twitter</h2> " +
				"<a href=\"http://twitter.com/tagaprice\">@tagaprice</a>");
		bottomText.add(lefthtml);
		bottomText.add(righthtml);
		bottom.add(bottomText);
		bottom.setCellHorizontalAlignment(bottomText, HorizontalPanel.ALIGN_CENTER);
				
		
		//add your stdlinks for debug
		bottomText.add(new HTML(
				"<h2>Debug</h2> " + 
				"<a href=\"#Start:null\">home</a> <br /> "
				//+ "<a href=\"#CreateProduct:/create\">New Product</a> <br /> "
				+ "<a href=\"#ListProducts:/show\">List Products</a> <br /> "
				//+ "<a href=\"#CreateShop:/create\">New Shop</a> <br /> "
				+ "<a href=\"#ListShops:\">List Shops</a> <br /> "
				//+ "<a href=\"#CreateReceipt:/create\">New Receipt</a> <br /> "
				//+ "<a href=\"#ListReceipts:/show\">List Receipts</a> <br /> "
				//+ "<a href=\"#Register:/REGISTER\">Sign Up</a> <br /> "
				));
				
	}
		
		
	
		//Set Popvisilb
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {
			@Override
			public void onLoginChange(LoginChangeEvent event) {
				loginPop.hide();
			}
		});


		_activityManager.setDisplay(center);


	}


	@Override
	public void initUI(ActivityManager activityManager, ClientFactory clientFactory) {
		_activityManager=activityManager;
		_clientFactory=clientFactory;
		init();

		RootPanel.get().add(vePa1);

	}


	@Override
	public InfoBox getInfoBox() {
		return _infoBox;
	}
	
	private void setLocation(Address address){
		Log.debug("setLocation. "+address);
		_curAddress = address;	
		_locationPop.hide();
		_location.setText(_curAddress.getAddress());
		LonLat c = new LonLat(_curAddress.getLon(), _curAddress.getLat());
		c.transform( "EPSG:4326","EPSG:900913");
		_osmShopMap.setCenter(c);
		_searchPopup.showRelativeTo(_search);
		
		search(_search.getText());
	}
	
	private void search(String searchCritera){
		
		
		_searchCount++;
		
		final int curSearchCount=_searchCount;
		
		LonLat southWest = new LonLat(_osmShopMap.getExtent().getLowerLeftX(), _osmShopMap.getExtent().getLowerLeftY());
		LonLat northEast = new LonLat(_osmShopMap.getExtent().getUpperRightX(), _osmShopMap.getExtent().getUpperRightY());
		southWest.transform("EPSG:900913","EPSG:4326");
		northEast.transform("EPSG:900913","EPSG:4326");
		
		_clientFactory.getSearchService().search(searchCritera, new BoundingBox(
				southWest.lat(),
				southWest.lon(),
				northEast.lat(),
				northEast.lon()), 
				new AsyncCallback<List<Document>>() {
			
			@Override
			public void onSuccess(List<Document> response) {
				if(curSearchCount==_searchCount){
					Log.debug("Search successful: count: "+response.size());
					_shopSearchPanel.clear();
					_productSearchPanel.clear();
					
					_osmLayer.destroyFeatures();
					for(final Document document:response){
						if (document.getDocType().equals("shop")) {
							final Shop shop = Shop.fromDocument(document);
							ShopPreview dumpShop = new ShopPreview(shop);
							dumpShop.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent arg0) {
									_clientFactory.getPlaceController().goTo(new CreateShopPlace(
											shop.getId(), 
											null, 
											null, 
											null, 
											null, 
											""+_curAddress.getLat(), 
											""+_curAddress.getLon(), 
											""+_osmShopMap.getZoom()));
									_searchPopup.hide();
									_search.setText("");
								}
							});
							_shopSearchPanel.add(dumpShop);
							
							
							System.out.println("shopAddres:"+shop.getAddress());
							
							//Simple points
							LonLat l = new LonLat(shop.getAddress().getLon(), shop.getAddress().getLat());
							l.transform("EPSG:4326", "EPSG:900913");
							Point point = new Point(l.lon(), l.lat());
							VectorFeature pointFeature = new VectorFeature(point);

							_osmLayer.addFeature(pointFeature);
							
						}
						else if (document.getDocType().equals("product")) {
							final Product product = Product.fromDocument(document);
							PackagePreview packDump = new PackagePreview(product, null);
							packDump.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent arg0) {
									_clientFactory.getPlaceController().goTo(new CreateProductPlace(
											product.getId(), 
											null, 
											null, 
											null, 
											""+_curAddress.getLat(), 
											""+_curAddress.getLon(), 
											""+_osmShopMap.getZoom()));
									_searchPopup.hide();
									_search.setText("");
								}
							});
							_productSearchPanel.add(packDump);
						}
					}	
					
					// new shop
					Shop ns = new Shop(null, null, null, "(new Shop)"+_search.getText(), null);
					ShopPreview dumpShop = new ShopPreview(ns);
					dumpShop.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent arg0) {
							_clientFactory.getPlaceController().goTo(new CreateShopPlace(
									null, 
									null, 
									null, 
									_search.getText(), 
									null, 
									""+_curAddress.getLat(), 
									""+_curAddress.getLon(), 
									""+_osmShopMap.getZoom()));
							_searchPopup.hide();
							_search.setText("");							
						}
					});
					_shopSearchPanel.add(dumpShop);
									
					//new Product
					Product pr = new Product(null, "(new Product) "+_search.getText(), null, null);
					PackagePreview newPackDump = new PackagePreview(pr, null);
					newPackDump.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent arg0) {
							_clientFactory.getPlaceController().goTo(new CreateProductPlace(
									null, 
									null, 
									null, 
									_search.getText(), 
									""+_curAddress.getLat(), 
									""+_curAddress.getLon(), 
									""+_osmShopMap.getZoom()));
							_searchPopup.hide();
							_search.setText("");
							
						}
					});
					
					_productSearchPanel.add(newPackDump);
					

					_searchPopup.showRelativeTo(_search);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				try{
					throw caught;
				}catch (DaoException e){
					Log.warn(e.getMessage());
				}catch (Throwable e){
					Log.error(e.getMessage());
				}				
			}
		});
	}
}
