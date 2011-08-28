package org.tagaprice.client.generics.widgets.desktopView;

import java.util.List;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.ZIndexBase;
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
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;
import org.tagaprice.shared.rpc.searchmanagement.ISearchServiceAsync;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter {

	private ISearchServiceAsync I_SEARCH_SERVICE_ASYNC = GWT.create(ISearchService.class);

	private VerticalPanel _vePa1 = new VerticalPanel();
	private MapOptions _defaultMapOptions = new MapOptions();
	private MapWidget _osmWidget; 
	private Map _osmMap;
	private Vector _layer;
	private LonLat _lonLat = new LonLat(16.37692,48.21426);
	private MorphWidget _addressBox = new MorphWidget();
	private VectorFeature _pointFeature;
	private VerticalPanel _addressListPanel = new VerticalPanel();
	private DragFeature _dragFeature;
	private boolean _readonly = true;
	private Grid _addressGrid = new Grid(1, 2);

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
		_addressGrid.getCellFormatter().setStyleName(0, 0, "namecell");
		_addressGrid.getCellFormatter().setStyleName(0, 1, "valuecell");
		_addressGrid.setWidget(0, 1, _addressBox);
		_vePa1.add(_addressGrid);
		
		
		
		
		//Init address list popup
		_addressListPanel.setWidth("100%");
		_addressListPanel.setStyleName("searchPopup");
		
		
		
	}
	

	@Override
	public void setAddress(Address address) {
		_osmMap.removeOverlayLayers();
		
		_lonLat = new LonLat(address.getLng(), address.getLat());

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
				final LonLat l = vectorFeature.getCenterLonLat();
				l.transform("EPSG:900913","EPSG:4326");
				Log.debug("Drag Marker: "+l.lat()+", "+l.lon());
				
				//get Possible streetnames
				_addressListPanel.clear();
				
				//try jsonP qwt
				String url = "http://open.mapquestapi.com/nominatim/v1/reverse?format=json&lat="+l.lat()+"&lon="+l.lon();
				
				System.out.println("requestUrl: "+url);
				JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
				jsonp.setCallbackParam("json_callback");
				jsonp.requestObject(url, new AsyncCallback<MapquestResponse>() {
					
					@Override
					public void onSuccess(MapquestResponse response) {
					
						
						if(response.getAddress()!=null){
							String responseString = "";
							
							if(response.getAddress().getRoad()!=null)
								responseString+=response.getAddress().getRoad();
							
							if(response.getAddress().getPedestrian()!=null)
								responseString+=response.getAddress().getPedestrian();
							
							if(response.getAddress().getFootway()!=null)
								responseString+=response.getAddress().getFootway();
							
							if(response.getAddress().getHouse_number()!=null)
								responseString+=" "+response.getAddress().getHouse_number()+",";
							
							if(response.getAddress().getPostcode()!=null)
								responseString+=" "+response.getAddress().getPostcode()+",";
							
							if(response.getAddress().getCity()!=null)
								responseString+=" "+response.getAddress().getCity()+",";
							
							if(response.getAddress().getCountry_code()!=null)
								responseString+=" "+response.getAddress().getCountry_code()+",";
							
							//TODO create better address 
							setAddress(new Address(responseString,Double.parseDouble(response.getLat()), Double.parseDouble(response.getLon())));
							System.out.println(responseString);
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

		_addressBox.setValue(address.getAddress());
		
	}

	@Override
	public Address getAddress() {
		LonLat l = _osmMap.getCenter();
		l.transform("EPSG:900913","EPSG:4326");
		return new Address(_addressBox.getValue(), l.lat(), l.lon());
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
		
		_addressBox.setReadOnly(_readonly);
		
	}


	@Override
	public boolean isReadOnly() {
		return _readonly;
	}

}


class MapquestResponse extends JavaScriptObject{
	
	protected MapquestResponse() {}
	
	public final native String getLat() /*-{
		return this.lat;
	}-*/;

	public final native void setLat(String lat) /*-{
		this.lat = lat;
	}-*/;
	
	public final native String getLon() /*-{
		return this.lon;
	}-*/;
	
	public final native void setLon(String lon) /*-{
		this.lon = lon;
	}-*/;
	
	
	public final native MapquestAddress getAddress()/*-{
		return this.address;
	}-*/;
	
	public final native void setAddress(MapquestAddress address)/*-{
		this.address = address;
	}-*/;
	
	
}


class MapquestAddress extends JavaScriptObject{
	
	protected MapquestAddress(){}
	
	public final native String getHouse_number() /*-{
		return this.house_number;
	}-*/;

	public final native void setHouse_number(String house_number) /*-{
		this.house_number = house_number;
	}-*/;
	
	
	public final native String getRoad() /*-{
		return this.road;
	}-*/;
	
	public final native void setRoad(String road) /*-{
		this.road = road;
	}-*/;
	
	public final native String getPedestrian() /*-{
		return this.pedestrian;
	}-*/;
	
	public final native void setPedestrian(String pedestrian) /*-{
		this.pedestrian = pedestrian;
	}-*/;
	
	public final native String getFootway() /*-{
		return this.footway;
	}-*/;
	
	public final native void setFootway(String footway) /*-{
		this.footway = footway;
	}-*/;
	
	public final native String getCity() /*-{
		return this.city;
	}-*/;
	
	public final native void setCity(String city) /*-{
		this.city = city;
	}-*/;
	
	public final native String getPostcode() /*-{
		return this.postcode;
	}-*/;
	
	public final native void setPostcode(String postcode) /*-{
		this.postcode = postcode;
	}-*/;
	
	public final native String getCountry() /*-{
		return this.country;
	}-*/;
	
	public final native void setCountry(String country) /*-{
		this.country = country;
	}-*/;
	
	public final native String getCountry_code() /*-{
		return this.country_code;
	}-*/;
	
	public final native void setCountry_code(String country_code) /*-{
		this.country_code = country_code;
	}-*/;
	
	public final native String print()/*-{
		
		return "house_number: "
		+this.house_number
		+", road: "+this.road
		+", pedestrian: "+this.pedestrian
		+", footway: "+this.footway
		+", suburb: "+this.suburb
		+", city_district: "+this.city_district
		+", city: "+this.city
		+", county: "+this.county
		+", region: "+this.region
		+", state: "+this.state
		+", postcode: "+this.postcode
		+", country: "+this.country
		+", country_code: "+this.country_code
		;
	}-*/;
	
	
	
	//address types
	/*
	 * house_number
	 * road
	 * pedestrian
	 * footway
	 * suburb
	 * city_district
	 * city
	 * county
	 * region
	 * state
	 * postcode
	 * country
	 * country_code
	 */
	
}