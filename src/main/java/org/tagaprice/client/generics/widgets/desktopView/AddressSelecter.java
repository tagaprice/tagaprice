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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	private PopupPanel _addressListPop = new PopupPanel(true);
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
		_addressListPop.setWidget(_addressListPanel);
		
		
		
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
				
				I_SEARCH_SERVICE_ASYNC.searchAddress(l.lat(), l.lon(), new AsyncCallback<List<Address>>() {
					
					@Override
					public void onSuccess(List<Address> r) {
						for(final Address a:r){
							Label _ad = new Label(a.getAddress());
							_ad.setStyleName("entry");
							
							_ad.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent arg0) {
									_addressListPop.hide();
									setAddress(a);									
								}
							});
							_addressListPanel.add(_ad);
						}
						
						Label _ad = new Label("Can't find streetname");
						_ad.setStyleName("entry");
						_ad.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent arg0) {
								_addressListPop.hide();
								setAddress(new Address("Add street name", l.lat(), l.lon()));									
							}
						});
						_addressListPanel.add(_ad);
						
					}
					
					@Override
					public void onFailure(Throwable e) {
						Log.error(e.toString());						
					}
				});
				
				_addressListPop.setWidth(_osmWidget.getOffsetWidth()+"px");
				_addressListPop.showRelativeTo(_osmWidget);
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
