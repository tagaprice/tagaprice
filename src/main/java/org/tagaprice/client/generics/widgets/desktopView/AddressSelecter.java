package org.tagaprice.client.generics.widgets.desktopView;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.DragFeature;
import org.gwtopenmaps.openlayers.client.control.DragFeature.DragFeatureListener;
import org.gwtopenmaps.openlayers.client.control.DragFeatureOptions;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.tagaprice.client.generics.widgets.IAddressSelecter;
import org.tagaprice.shared.entities.Address;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.tagaprice.client.generics.MapQuest.MapquestCreator;
import org.tagaprice.client.generics.MapQuest.MapquestResponse;

public class AddressSelecter extends Composite implements IAddressSelecter {


	private VerticalPanel _vePa1 = new VerticalPanel();
	private MapOptions _defaultMapOptions = new MapOptions();
	private MapWidget _osmWidget; 
	private Map _osmMap;
	private Vector _layer;
	private LonLat _lonLat = new LonLat(16.37692,48.21426);
	private MorphWidget _addressBox = new MorphWidget();
	private MorphWidget _streeBox = new MorphWidget();
	private MorphWidget _postalcodeBox = new MorphWidget();
	private MorphWidget _cityBox = new MorphWidget();
	private MorphWidget _countryCodeBox = new MorphWidget();
	
	private VectorFeature _pointFeature;
	private VerticalPanel _addressListPanel = new VerticalPanel();
	private DragFeature _dragFeature;
	private boolean _readonly = true;
	private Grid _addressGrid = new Grid(4, 2);
	private Address _curAddress = new Address();
	private Button _showOnMapButton = new Button("Set marker to address");

	public AddressSelecter() {
		Log.debug("Start AddressSelecter");
		_vePa1.setWidth("100%");
		initWidget(_vePa1);
		
		
		//Init Maps
		OSM osmLayler = OSM.Mapnik("Mapnik");
		osmLayler.setIsBaseLayer(true);
		_osmWidget = new MapWidget("100%", "140px", _defaultMapOptions);
		_osmMap = _osmWidget.getMap();
		_osmMap.addLayer(osmLayler);
		
		_osmMap.zoomTo(16);
		
		
		_vePa1.add(_osmWidget);
		
		//Add address text
		_addressGrid.setWidth("100%");
		_addressGrid.setStyleName("propertyGrid");
		_addressGrid.setWidget(0, 0, new Label("Street"));
		_addressGrid.setWidget(1, 0, new Label("ZIP"));
		_addressGrid.setWidget(2, 0, new Label("City"));
		_addressGrid.setWidget(3, 0, new Label("Country"));
		
		_addressGrid.getCellFormatter().setStyleName(0, 0, "namecell");
		_addressGrid.getCellFormatter().setStyleName(0, 1, "valuecell");
		_addressGrid.getCellFormatter().setStyleName(1, 1, "valuecell");
		_addressGrid.getCellFormatter().setStyleName(2, 1, "valuecell");
		_addressGrid.getCellFormatter().setStyleName(3, 1, "valuecell");
		
		_addressBox.setReadOnly(true);
		_addressGrid.setWidget(0, 1, _streeBox);
		_addressGrid.setWidget(1, 1, _postalcodeBox);
		_addressGrid.setWidget(2, 1, _cityBox);
		_addressGrid.setWidget(3, 1, _countryCodeBox);
		
		
		_vePa1.add(_addressGrid);
		
		//Show Button
		_vePa1.add(_showOnMapButton);
		_showOnMapButton.setVisible(!_readonly);
		
		_showOnMapButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				Log.debug("Search for address");
				JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
				jsonp.setCallbackParam("json_callback");
				
				String url = "http://open.mapquestapi.com/nominatim/v1/search?format=json&addressdetails=1&q=";
				String q="";
				if(!_streeBox.getValue().isEmpty())
					q+=_streeBox.getValue()+" ";
				
				/*
				if(!_postalcodeBox.getValue().isEmpty())
					q+=_postalcodeBox.getValue()+" ";
				*/
				
				if(!_cityBox.getValue().isEmpty())
					q+=_cityBox.getValue()+" ";
				
				/*
				if(!q.isEmpty())
					q=q.substring(0, q.length()-1);
				*/
				
				url+=q;
				
				//country code
				if(!_countryCodeBox.getValue().isEmpty())
					url+="&countrycodes="+_countryCodeBox.getValue().trim();
				
				
				jsonp.requestObject(url, new AsyncCallback<JsArray<MapquestResponse>>() {

					@Override
					public void onFailure(Throwable e) {
						Log.error("MapquestPointToAdressFailure: "+e);						
					}

					@Override
					public void onSuccess(JsArray<MapquestResponse> response) {
					
						if(response.length()>0){
							MapquestResponse temp = response.get(0);
							
							
							Address at = MapquestCreator.getAddressByMapquestResponse(temp);
							
							
							if(at!=null){
								at.getPos().setLat(Double.parseDouble(temp.getLat()));
								at.getPos().setLon(Double.parseDouble(temp.getLon()));
								setAddress(at);
							}
									
							
						}
					}

				});
			}
		});
		
		
		//Init address list popup
		_addressListPanel.setWidth("100%");
		_addressListPanel.setStyleName("searchPopup");
		
		
		
	}
	

	@Override
	public void setAddress(Address address) {
		_curAddress=address;
		_osmMap.removeOverlayLayers();
		
		_lonLat = address.getPos().toLonLat();

		//set Pos
		_lonLat.transform("EPSG:4326", "EPSG:900913");
		_osmMap.setCenter(_lonLat);
		
		
		//Create Moveable marker
		VectorOptions vectorOptions = new VectorOptions();
		//Style
		Style style = new Style();
		style.setStrokeColor("#000000");
		style.setStrokeWidth(3);
		style.setFillColor("#FF0000");
		style.setFillOpacity(0.5);
		style.setPointRadius(10);
		style.setStrokeOpacity(1.0);
		vectorOptions.setStyle(style);
		_layer = new Vector("Marker", vectorOptions);
		
		//Add dragfeatures
		DragFeatureOptions dragFeatureOptions = new DragFeatureOptions();
		dragFeatureOptions.onComplete(new DragFeatureListener() {
			
			@Override
			public void onDragEvent(VectorFeature vectorFeature, Pixel pixel) {
				final LonLat lonLat = vectorFeature.getCenterLonLat();
				lonLat.transform("EPSG:900913","EPSG:4326");
				Log.debug("Drag Marker: "+lonLat.lat()+", "+lonLat.lon());
				
				//get Possible streetnames
				_addressListPanel.clear();
				
				//try jsonP qwt
				String url = "http://open.mapquestapi.com/nominatim/v1/reverse?format=json&lat="+lonLat.lat()+"&lon="+lonLat.lon();
				
				JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
				jsonp.setCallbackParam("json_callback");
				jsonp.requestObject(url, new AsyncCallback<MapquestResponse>() {
					
					@Override
					public void onSuccess(MapquestResponse response) {
					
						Address at = MapquestCreator.getAddressByMapquestResponse(response);
						
						if(at!=null){
							at.getPos().setLat(lonLat.lat());
							at.getPos().setLon(lonLat.lon());
							setAddress(at);
						}
						
						
						
					}
					
					@Override
					public void onFailure(Throwable e) {
						Log.error("MapquestPointToAdressFailure: "+e);
					}
				});
				

			}
		});
		
		
		
		_dragFeature = new DragFeature(_layer, dragFeatureOptions);
		_osmMap.addControl(_dragFeature);
		//dragFeature.activate();
		_osmMap.addLayer(_layer);
		_pointFeature = new VectorFeature(new Point(_lonLat.lon(), _lonLat.lat()));
		
		_layer.addFeature(_pointFeature);
		
		setReadOnly(_readonly);

		_streeBox.setValue(address.getStreet());
		_postalcodeBox.setValue(address.getPostalcode());
		_cityBox.setValue(address.getCity());
		_countryCodeBox.setValue(address.getCountrycode());
		
	}

	@Override
	public Address getAddress() {
		LonLat l = _osmMap.getCenter();
		l.transform("EPSG:900913","EPSG:4326");
		//return new Address(street, postalcode, city, countrycode,  l.lat(), l.lon());
		//return new Address(_addressBox.getValue(), l.lat(), l.lon());
		return _curAddress;
	}
	

	@Override
	public void setReadOnly(boolean read) {
		if(read){
			_readonly=true;
			_dragFeature.deactivate();
		}else{
			_readonly=false;
			_dragFeature.activate();
		}
		
		//_addressBox.setReadOnly(_readonly);
		_postalcodeBox.setReadOnly(_readonly);
		_streeBox.setReadOnly(_readonly);
		_cityBox.setReadOnly(_readonly);
		_countryCodeBox.setReadOnly(_readonly);
		_showOnMapButton.setVisible(!_readonly);
	}


	@Override
	public boolean isReadOnly() {
		return _readonly;
	}

}


