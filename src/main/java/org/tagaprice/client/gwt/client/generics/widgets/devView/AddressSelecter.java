package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
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
import org.tagaprice.client.gwt.client.generics.widgets.CountrySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.IAddressSelecter;
import org.tagaprice.client.gwt.shared.entities.Address;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Subsidiary;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.ISubsidiary;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter {

	VerticalPanel vePaTemp = new VerticalPanel();

	TextBox _street = new TextBox();
	TextBox _zip = new TextBox();
	TextBox _city = new TextBox();
	//TextBox _country = new TextBox();
	CountrySelecter _country = new CountrySelecter();
	Label _lat = new Label();
	Label _lng = new Label();
	Geocoder coder = new Geocoder();
	ISubsidiary _subsidiary;

	MarkerOptions _mOptions = MarkerOptions.newInstance();
	Marker marker;
	MapWidget mapWidget;

	//OSM
	Map osmMap;
	Point point;
	Vector layer;

	public AddressSelecter() {

		HorizontalPanel hoPaTemp = new HorizontalPanel();
		_mOptions.setDraggable(true);

		marker = new Marker(LatLng.newInstance(48.21426, 16.37692), _mOptions);
		marker.setDraggingEnabled(true);

		mapWidget = new MapWidget(marker.getLatLng(), 13);
		mapWidget.setSize("200px", "200px");
		mapWidget.addOverlay(marker);


		//******** INIT OSM ************/
		MapOptions defaultMapOptions = new MapOptions();
		LonLat lonLat = new LonLat(16.37692,48.21426);
		org.gwtopenmaps.openlayers.client.MapWidget omapWidget = new org.gwtopenmaps.openlayers.client.MapWidget("200px", "200px", defaultMapOptions);
		OSM osm_2 = OSM.Mapnik("Mapnik");   // Label for menu 'LayerSwitcher'
		osm_2.setIsBaseLayer(true);
		osmMap = omapWidget.getMap();
		osmMap.addLayer(osm_2);
		lonLat.transform("EPSG:4326", "EPSG:900913");
		osmMap.setCenter(lonLat, 12);

		//******** INIT OSM Vector ************/
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

		// Create the layer
		layer = new Vector("Vector Layer", vectorOptions);

		point = new Point(lonLat.lon(), lonLat.lat());
		VectorFeature pointFeature = new VectorFeature(point);
		layer.addFeature(pointFeature);
		DragFeatureOptions dragFeatureOptions = new DragFeatureOptions();

		DragFeature dragFeature = new DragFeature(layer, dragFeatureOptions);
		osmMap.addControl(dragFeature);
		dragFeature.activate();

		osmMap.addLayer(layer);

		dragFeatureOptions.onComplete(new DragFeatureListener() {

			@Override
			public void onDragEvent(VectorFeature vectorFeature, Pixel pixel) {
				LonLat l = vectorFeature.getCenterLonLat();
				l.transform("EPSG:900913","EPSG:4326");
				setLatLng(LatLng.newInstance(l.lat(), l.lon()));
				System.out.println("dragEnd: lat: "+vectorFeature.getCenterLonLat().lat()+", lng: "+vectorFeature.getCenterLonLat().lon());

				coder.getLocations(LatLng.newInstance(l.lat(), l.lon()), new LocationCallback() {

					@Override
					public void onSuccess(JsArray<Placemark> locations) {
						_street.setText(locations.get(0).getStreet());
						_zip.setText(locations.get(0).getPostalCode());
						_city.setText(locations.get(0).getCity());
						_country.setCountry(Country.valueOf(locations.get(0).getCountry()));
						//_country.setText(locations.get(0).getCountry());
					}

					@Override
					public void onFailure(int statusCode) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		//*********** END *******

		vePaTemp.add(new HTML("<hr />"));
		Grid tempGrid = new Grid(6, 2);
		tempGrid.setWidget(0, 0, new Label("street"));
		tempGrid.setWidget(1, 0, new Label("zip"));
		tempGrid.setWidget(2, 0, new Label("city"));
		tempGrid.setWidget(3, 0, new Label("country"));
		tempGrid.setWidget(4, 0, new Label("lat"));
		tempGrid.setWidget(5, 0, new Label("lng"));

		tempGrid.setWidget(0, 1, _street);
		tempGrid.setWidget(1, 1, _zip);
		tempGrid.setWidget(2, 1, _city);
		tempGrid.setWidget(3, 1, _country);
		tempGrid.setWidget(4, 1, _lat);
		tempGrid.setWidget(5, 1, _lng);

		hoPaTemp.add(tempGrid);
		hoPaTemp.add(mapWidget);
		hoPaTemp.add(omapWidget);
		vePaTemp.add(hoPaTemp);

		initWidget(vePaTemp);



	}

	@Override
	public void setAddress(ISubsidiary address){
		_subsidiary=address;
		_street.setText(_subsidiary.getAddress().getStreet());
		_zip.setText(_subsidiary.getAddress().getPostalcode());
		_city.setText(_subsidiary.getAddress().getCity());
		_country.setCountry(_subsidiary.getAddress().getCountry());


		setLatLng(LatLng.newInstance(_subsidiary.getAddress().getLat(), _subsidiary.getAddress().getLng()));
	}

	@Override
	public ISubsidiary getAddress(){
		if(_subsidiary==null)_subsidiary = new Subsidiary();
		_subsidiary.getAddress().setStreet(_street.getText());
		_subsidiary.getAddress().setPostalcode(_zip.getText());
		_subsidiary.getAddress().setCity(_city.getText());
		_subsidiary.getAddress().setCountry(_country.getCountry());
		_subsidiary.getAddress().setLat(marker.getLatLng().getLatitude());
		_subsidiary.getAddress().setLng(marker.getLatLng().getLongitude());


		return _subsidiary;
	}

	private void setLatLng(LatLng latLng){
		_lat.setText(""+latLng.getLatitude());
		_lng.setText(""+latLng.getLongitude());
		marker.setLatLng(latLng);
		mapWidget.setCenter(marker.getLatLng());

		//osm
		LonLat t = new LonLat(latLng.getLongitude(), latLng.getLatitude());
		t.transform("EPSG:4326", "EPSG:900913");
		osmMap.setCenter(t);

		point.setXY(t.lon(), t.lat());
		layer.redraw();


	}

	@Override
	public void setCurrentAddress(Address address) {
		_street.setText(address.getStreet());
		_zip.setText(address.getPostalcode());
		_city.setText(address.getCity());
		_country.setCountry(address.getCountry());

		setLatLng(LatLng.newInstance(address.getLat(), address.getLng()));

	}


}
