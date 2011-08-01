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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter {

	private VerticalPanel _vePa1 = new VerticalPanel();
	private MapOptions _defaultMapOptions = new MapOptions();
	private MapWidget _osmWidget = new MapWidget("100%", "140px", _defaultMapOptions); 
	private Map _osmMap;
	private Vector _layer;
	private LonLat _lonLat = new LonLat(16.37692,48.21426);
	private TextBox _addressBox = new TextBox();
	private VectorFeature _pointFeature;
	private Point _point = new Point(_lonLat.lon(), _lonLat.lat());
	private PopupPanel _addressListPop = new PopupPanel(true);
	private VerticalPanel _addressListPanel = new VerticalPanel();

	public AddressSelecter() {
		_vePa1.setWidth("100%");
		initWidget(_vePa1);
		
		
		//Init Maps
		OSM osmLayler = OSM.Mapnik("Mapnik");
		osmLayler.setIsBaseLayer(true);
		_osmMap = _osmWidget.getMap();
		_osmMap.addLayer(osmLayler);
		
		//set Pos
		_lonLat.transform("EPSG:4326", "EPSG:900913");
		_osmMap.setCenter(_lonLat, 16);
		
		
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
				LonLat l = vectorFeature.getCenterLonLat();
				l.transform("EPSG:900913","EPSG:4326");
				Log.debug("Drag Marker: "+l.lat()+", "+l.lon());
				//test
				Address mock = new Address("Flossgasse 1A", l.lat(), l.lon());
				setAddress(mock);
				
				//get Possible streetnames
				_addressListPanel.clear();
				_addressListPanel.add(new Label("Elfriede-Gerstl-Steg"));
				_addressListPanel.add(new Label("Kleine Ungarbrücke"));
				_addressListPanel.add(new Label("Zedlitzgasse"));
				_addressListPanel.add(new Label("Weiskirchnerstraße"));
				
				_addressListPop.showRelativeTo(_osmWidget);
			}
		});
		
		
		
		DragFeature dragFeature = new DragFeature(_layer, dragFeatureOptions);
		_osmMap.addControl(dragFeature);
		dragFeature.activate();
		_osmMap.addLayer(_layer);
		_pointFeature = new VectorFeature(new Point(_lonLat.lon(), _lonLat.lat()));
		
		_layer.addFeature(_pointFeature);
		_vePa1.add(_osmWidget);
		
		//Add address text
		_vePa1.add(_addressBox);
		
		
		
		
		//Init address list popup
		_addressListPanel.setWidth("100%");
		_addressListPop.setWidget(_addressListPanel);
		
	}
	
	@Override
	public void setCurrentAddress(Address address) {
		
		
		
	}

	@Override
	public void setAddress(Address address) {
		_lonLat = new LonLat(address.getLng(), address.getLat());
		_lonLat.transform("EPSG:4326", "EPSG:900913");
		_osmMap.setCenter(_lonLat);
		
		_point.setXY(_lonLat.lon(), _lonLat.lat());
		
	}

	@Override
	public Address getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
