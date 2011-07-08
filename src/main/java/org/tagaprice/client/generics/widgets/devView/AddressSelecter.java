package org.tagaprice.client.generics.widgets.devView;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter {
	ISearchServiceAsync I_SEARCH_SERVICE_ASYNC = GWT.create(ISearchService.class);
	VerticalPanel vePaTemp = new VerticalPanel();

	TextBox _addressBox = new TextBox();
	Label _lat = new Label();
	Label _lng = new Label();
	Address _adddress;


	//OSM
	Map osmMap;
	//Point point;
	VectorOptions vectorOptions = new VectorOptions();
	Vector layer = new Vector("Marker", vectorOptions);
	DragFeatureOptions dragFeatureOptions = new DragFeatureOptions();

	public AddressSelecter() {

		HorizontalPanel hoPaTemp = new HorizontalPanel();



		//******** INIT OSM ************/
		MapOptions defaultMapOptions = new MapOptions();
		//LonLat lonLat = new LonLat(16.37692,48.21426);
		MapWidget omapWidget = new MapWidget("200px", "200px", defaultMapOptions);
		OSM osm_2 = OSM.Mapnik("Mapnik");   // Label for menu 'LayerSwitcher'
		osm_2.setIsBaseLayer(true);
		osmMap = omapWidget.getMap();
		osmMap.addLayer(osm_2);
		//lonLat.transform("EPSG:4326", "EPSG:900913");
		//osmMap.setCenter(lonLat, 15);

		//******** INIT OSM Vector ************/
		
		//Style
		Style style = new Style();
		style.setStrokeColor("#000000");
		style.setStrokeWidth(3);
		style.setFillColor("#FF0000");
		style.setFillOpacity(0.5);
		style.setPointRadius(10);
		style.setStrokeOpacity(1.0);
		vectorOptions.setStyle(style);

		// Create the layer
		

		//point = new Point(lonLat.lon(), lonLat.lat());
		//VectorFeature pointFeature = new VectorFeature(point);
		//layer.addFeature(pointFeature);
		

		DragFeature dragFeature = new DragFeature(layer, dragFeatureOptions);
		osmMap.addControl(dragFeature);
		dragFeature.activate();

		osmMap.addLayer(layer);




		dragFeatureOptions.onComplete(new DragFeatureListener() {

			@Override
			public void onDragEvent(VectorFeature vectorFeature, Pixel pixel) {
				LonLat l = vectorFeature.getCenterLonLat();
				setLatLng(l);
				l.transform("EPSG:900913","EPSG:4326");

				System.out.println("dragEnd: lat: "+vectorFeature.getCenterLonLat().lat()+", lng: "+vectorFeature.getCenterLonLat().lon());


				I_SEARCH_SERVICE_ASYNC.searchAddress(l.lat(), l.lon(), new AsyncCallback<Address>() {

					@Override
					public void onSuccess(Address address) {
						Log.debug(address.getAddress());
						_addressBox.setText(address.getAddress());

					}

					@Override
					public void onFailure(Throwable e) {
						Log.error(e.toString());

					}
				});


			}
		});

		//*********** END *******

		vePaTemp.add(new HTML("<hr />"));
		Grid tempGrid = new Grid(6, 2);
		tempGrid.setWidget(0, 0, new Label("address"));
		tempGrid.setWidget(4, 0, new Label("lat"));
		tempGrid.setWidget(5, 0, new Label("lng"));

		tempGrid.setWidget(0, 1, _addressBox);
		tempGrid.setWidget(4, 1, _lat);
		tempGrid.setWidget(5, 1, _lng);

		hoPaTemp.add(tempGrid);
		hoPaTemp.add(omapWidget);
		vePaTemp.add(hoPaTemp);

		initWidget(vePaTemp);



	}

	@Override
	public void setAddress(Address address){
		_adddress=address;
		_addressBox.setText(_adddress.getAddress());

		LonLat l = new LonLat(_adddress.getLng(), _adddress.getLat());
		l.transform("EPSG:4326", "EPSG:900913");
		setLatLng(l);
	}

	@Override
	public Address getAddress(){
		if(_adddress==null)_adddress = new Address();
		_adddress.setAddress(_addressBox.getText());

		LonLat l = osmMap.getCenter();
		l.transform("EPSG:900913","EPSG:4326");

		_adddress.setLat(l.lat());
		_adddress.setLng(l.lon());


		return _adddress;
	}

	private void setLatLng(LonLat lonLat){
		layer.destroyFeatures();
		System.out.println("setLatLng: "+lonLat);
		_lat.setText(""+lonLat.lat());
		_lng.setText(""+lonLat.lon());

		osmMap.setCenter(lonLat);

		
		
		VectorFeature pointFeature = new VectorFeature(new Point(lonLat.lon(), lonLat.lat()));
		layer.addFeature(pointFeature);
		
		/*
		if(point==null){
			point= new Point(lonLat.lon(), lonLat.lat());
			VectorFeature pointFeature = new VectorFeature(point);
			layer.addFeature(pointFeature);
			//layer.redraw();
		}else{
			point.setXY(lonLat.lon(), lonLat.lat());

		}
		*/
		//layer.redraw();

	}

	@Override
	public void setCurrentAddress(Address address) {
		_addressBox.setText(address.getAddress());

		LonLat l = new LonLat(address.getLng(),address.getLat());
		l.transform("EPSG:4326", "EPSG:900913");
		setLatLng(l);

	}


}
