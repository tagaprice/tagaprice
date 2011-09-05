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
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;
import org.tagaprice.shared.rpc.searchmanagement.ISearchServiceAsync;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
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

public class AddressSelecter extends Composite implements IAddressSelecter {

	private ISearchServiceAsync I_SEARCH_SERVICE_ASYNC = GWT.create(ISearchService.class);

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
				
				System.out.println("url: "+url);
				
				jsonp.requestObject(url, new AsyncCallback<JsArray<MapquestResponse>>() {

					@Override
					public void onFailure(Throwable e) {
						Log.error("MapquestPointToAdressFailure: "+e);						
					}

					@Override
					public void onSuccess(JsArray<MapquestResponse> response) {
						// TODO Auto-generated method stub
						
						if(response.length()>0){
							MapquestResponse temp = response.get(0);
							Address at = new Address();
							
							at.getPos().setLat(Double.parseDouble(temp.getLat()));
							at.getPos().setLon(Double.parseDouble(temp.getLon()));
							
							if(temp.getAddress()!=null){
								String responseString = "";
								
								if(temp.getAddress().getRoad()!=null){
									responseString+=temp.getAddress().getRoad();
									at.setStreet(temp.getAddress().getRoad());
								}
									
								if(temp.getAddress().getPedestrian()!=null){
									responseString+=temp.getAddress().getPedestrian();
									at.setStreet(temp.getAddress().getPedestrian());
								}
								
								if(temp.getAddress().getFootway()!=null){
									responseString+=temp.getAddress().getFootway();
									at.setStreet(temp.getAddress().getFootway());
								}
								
								if(temp.getAddress().getHouse_number()!=null){
									responseString+=" "+temp.getAddress().getHouse_number()+",";
									if(at.getStreet()!=null)
										at.setStreet(at.getStreet()+" "+temp.getAddress().getHouse_number());
								}
								
								if(temp.getAddress().getPostcode()!=null){
									responseString+=" "+temp.getAddress().getPostcode()+",";
									at.setPostalcode(temp.getAddress().getPostcode());
								}
								
								if(temp.getAddress().getCity()!=null){
									responseString+=" "+temp.getAddress().getCity()+",";
									at.setCity(temp.getAddress().getCity());
								}
									
								if(temp.getAddress().getCountry_code()!=null){
									responseString+=" "+temp.getAddress().getCountry_code()+",";
									at.setCountrycode(temp.getAddress().getCountry_code());
								}
								
								//TODO create better address 
								//Address at = new Address(responseString,Double.parseDouble(response.getLat()), Double.parseDouble(response.getLon()));
								at.setAddress(responseString);
								
								
								setAddress(at);
							}
						}
						/*
						for(int i=0;i<response.length();i++){
							System.out.println("d: "+response.get(i).getLat()+","+response.get(i).getLon()+" "+response.get(i).getAddress().print());
						}
						*/
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
					
						Address at = new Address();
						
						at.getPos().setLat(lonLat.lat());
						at.getPos().setLon(lonLat.lon());
						
						if(response.getAddress()!=null){
							String responseString = "";
							
							if(response.getAddress().getRoad()!=null){
								responseString+=response.getAddress().getRoad();
								at.setStreet(response.getAddress().getRoad());
							}
								
							if(response.getAddress().getPedestrian()!=null){
								responseString+=response.getAddress().getPedestrian();
								at.setStreet(response.getAddress().getPedestrian());
							}
							
							if(response.getAddress().getFootway()!=null){
								responseString+=response.getAddress().getFootway();
								at.setStreet(response.getAddress().getFootway());
							}
							
							if(response.getAddress().getHouse_number()!=null){
								responseString+=" "+response.getAddress().getHouse_number()+",";
								if(at.getStreet()!=null)
									at.setStreet(at.getStreet()+" "+response.getAddress().getHouse_number());
							}
							
							if(response.getAddress().getPostcode()!=null){
								responseString+=" "+response.getAddress().getPostcode()+",";
								at.setPostalcode(response.getAddress().getPostcode());
							}
							
							if(response.getAddress().getCity()!=null){
								responseString+=" "+response.getAddress().getCity()+",";
								at.setCity(response.getAddress().getCity());
							}
								
							if(response.getAddress().getCountry_code()!=null){
								responseString+=" "+response.getAddress().getCountry_code()+",";
								at.setCountrycode(response.getAddress().getCountry_code());
							}
							
							//TODO create better address 
							//Address at = new Address(responseString,Double.parseDouble(response.getLat()), Double.parseDouble(response.getLon()));
							at.setAddress(responseString);
							
							
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

		_addressBox.setValue(address.getAddress());
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