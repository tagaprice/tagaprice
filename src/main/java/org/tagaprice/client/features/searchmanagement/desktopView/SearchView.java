package org.tagaprice.client.features.searchmanagement.desktopView;

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
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.features.searchmanagement.ISearchView;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.desktopView.PackagePreview;
import org.tagaprice.client.generics.widgets.desktopView.ShopPreview;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchView extends Composite implements ISearchView {

	private Presenter _presenter;
	private StdFrame _stdFrame = new StdFrame();
	private HorizontalPanel _searchHoPa = new HorizontalPanel();
	private TextBox _search = new TextBox();
	private TextBox _location = new TextBox();
	private PopupPanel _locationPop = new PopupPanel(true);
	private VerticalPanel _locationVePa = new VerticalPanel();
	private VerticalPanel _dynLocationVePa = new VerticalPanel();
	
	private VerticalPanel _resultsPanel = new VerticalPanel();
	private VerticalPanel _shopSearchPanel = new VerticalPanel();
	private VerticalPanel _productSearchPanel = new VerticalPanel();
	private VerticalPanel _mapPanel = new VerticalPanel();
	
	
	//map
	private MapOptions _osmShopOptions = new MapOptions();
	private MapWidget _osmShopWidget; 
	private Map _osmShopMap;
	private VectorOptions _osmVectorOptions = new VectorOptions();
	private Vector _osmLayer;
	private Address _curAddress;
	
	public SearchView() {

		//set search Header
		_searchHoPa.add(_search);
		_searchHoPa.add(_location);
		_search.setStyleName("header-search");
		_location.setStyleName("header-location");
		_location.setText("Your Location: ");
		
		
		
		//Implement Searching
		_search.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				LonLat southWest = new LonLat(_osmShopMap.getExtent().getLowerLeftX(), _osmShopMap.getExtent().getLowerLeftY());
				LonLat northEast = new LonLat(_osmShopMap.getExtent().getUpperRightX(), _osmShopMap.getExtent().getUpperRightY());
				southWest.transform("EPSG:900913","EPSG:4326");
				northEast.transform("EPSG:900913","EPSG:4326");
				
				_presenter.onSearch(_search.getText(),new BoundingBox(
						southWest.lat(),
						southWest.lon(),
						northEast.lat(),
						northEast.lon()));
			}
		});
		
		
		
		//locationPop
		_locationVePa.setWidth("200px");
		_locationVePa.setStyleName("popBackground");
		_locationPop.getElement().getStyle().setZIndex(2000);
		_locationPop.setWidget(_locationVePa);
		_location.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent arg0) {
				_locationPop.showRelativeTo(_location);
				
			}
		});
		
		//Current Location
		Label locText = new Label("Current Location");
		locText.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onFindGpsPosition();				
			}
		});
		_locationVePa.add(_dynLocationVePa);
		_locationVePa.add(locText);
		
		
		
		_stdFrame.setHeader(_searchHoPa);
		
		
		//setSearchResults
		
		//results
		_resultsPanel.setWidth("100%");
		_stdFrame.setBody(_resultsPanel, "300px");
		Label shopResultsLabel = new Label("Shop Results");
		shopResultsLabel.setStyleName("propertyHeader");
		_resultsPanel.add(shopResultsLabel);
		_shopSearchPanel.setWidth("100%");
		_resultsPanel.add(_shopSearchPanel);
		Label productResultsLabel = new Label("Product Results");
		productResultsLabel.setStyleName("propertyHeader");
		_resultsPanel.add(productResultsLabel);
		_productSearchPanel.setWidth("100%");
		_resultsPanel.add(_productSearchPanel);
		
		//map
		_mapPanel.setWidth("100%");
		_stdFrame.setBody(_mapPanel);
		
		//Shop Search
		
		//search map
		OSM osmLayler = OSM.Mapnik("Mapnik");
		osmLayler.setIsBaseLayer(true);
		
		_osmShopWidget = new MapWidget("100%", "300px", _osmShopOptions);
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
		_mapPanel.add(_osmShopWidget);
		
		_osmShopMap.addMapMoveEndListener(new MapMoveEndListener() {
			
			@Override
			public void onMapMoveEnd(MapMoveEndEvent eventObject) {
				LonLat c = _osmShopMap.getCenter();
				c.transform("EPSG:900913", "EPSG:4326");
				
				Log.debug("move: add: "+_curAddress +", point: "+c.lat()+":"+c.lon());
				
				
				if(_curAddress!=null){
					if(_curAddress.getPos().getLat()!=c.lat() || _curAddress.getPos().getLon()!=c.lon()){
						setAddress(new Address("Map Area","","","", c.lat(), c.lon()));
					}
				}else if(_curAddress==null){
					setAddress(new Address("Map Area","","","", c.lat(), c.lon()));
				}
				
			}
		});
		
				
		
			
			
		
		
		
		initWidget(_stdFrame);
	}
	


	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}



	@Override
	public void addSelectableAddress(List<Address> address) {
		_dynLocationVePa.clear();
		
		for(final Address a:address){
			Label locText = new Label(a.getStreet()+", "+a.getCity());
			locText.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					_presenter.setAddress(a);
					_locationPop.hide();
				}
			});
			_dynLocationVePa.add(locText);
		}
		
		
		
	}



	@Override
	public void setAddress(Address address) {
		_curAddress=address;
		_locationPop.hide();
		_location.setText(address.getStreet());
		LonLat lonLat = _curAddress.getPos().toLonLat();
		lonLat.transform( "EPSG:4326","EPSG:900913");
		_osmShopMap.setCenter(lonLat);
		

		if(!_search.getText().trim().isEmpty()){
			LonLat southWest = new LonLat(_osmShopMap.getExtent().getLowerLeftX(), _osmShopMap.getExtent().getLowerLeftY());
			LonLat northEast = new LonLat(_osmShopMap.getExtent().getUpperRightX(), _osmShopMap.getExtent().getUpperRightY());
			southWest.transform("EPSG:900913","EPSG:4326");
			northEast.transform("EPSG:900913","EPSG:4326");
			
			_presenter.onSearch(_search.getText(),new BoundingBox(
					southWest.lat(),
					southWest.lon(),
					northEast.lat(),
					northEast.lon()));
		
		}
	}



	@Override
	public void setSearchResults(List<Document> results) {
		Log.debug("Search successful: count: "+results.size());
		_shopSearchPanel.clear();
		_productSearchPanel.clear();
		_osmLayer.destroyFeatures();
		
		for(final Document document:results){
			if (document.getDocType().equals("shop")) {
				final Shop shop = Shop.fromDocument(document);
				ShopPreview dumpShop = new ShopPreview(shop);
				dumpShop.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {
						_presenter.goTo(new CreateShopPlace(
								shop.getId(), 
								null, 
								null, 
								null, 
								null, 
								""+_curAddress.getPos().getLat(), 
								""+_curAddress.getPos().getLon(), 
								""+_osmShopMap.getZoom()));
					}
				});
				_shopSearchPanel.add(dumpShop);
				
				
				
				//Simple points
				LonLat lonLat = shop.getAddress().getPos().toLonLat();
				lonLat.transform("EPSG:4326", "EPSG:900913");
				Point point = new Point(lonLat.lon(), lonLat.lat());
				VectorFeature pointFeature = new VectorFeature(point);

				_osmLayer.addFeature(pointFeature);
				
			}
			else if (document.getDocType().equals("product")) {
				final Product product = Product.fromDocument(document);
				PackagePreview packDump = new PackagePreview(product, null);
				packDump.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {
						_presenter.goTo(new CreateProductPlace(
								product.getId(), 
								null, 
								null, 
								null, 
								""+_curAddress.getPos().getLat(), 
								""+_curAddress.getPos().getLon(), 
								""+_osmShopMap.getZoom()));
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
				_presenter.goTo(new CreateShopPlace(
						null, 
						null, 
						null, 
						_search.getText(), 
						null, 
						""+_curAddress.getPos().getLat(), 
						""+_curAddress.getPos().getLon(), 
						""+_osmShopMap.getZoom()));		
			}
		});
		_shopSearchPanel.add(dumpShop);
						
		//new Product
		Product pr = new Product(null, "(new Product) "+_search.getText(), null, null);
		PackagePreview newPackDump = new PackagePreview(pr, null);
		newPackDump.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.goTo(new CreateProductPlace(
						null, 
						null, 
						null, 
						_search.getText(), 
						""+_curAddress.getPos().getLat(), 
						""+_curAddress.getPos().getLon(), 
						""+_osmShopMap.getZoom()));
				
			}
		});
		_productSearchPanel.add(newPackDump);
	}
	

}
