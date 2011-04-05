package org.tagaprice.client.gwt.client;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.DragFeature;
import org.gwtopenmaps.openlayers.client.control.DragFeature.DragFeatureListener;
import org.gwtopenmaps.openlayers.client.control.DragFeatureOptions;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.Layer;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.gwtopenmaps.openlayers.client.Pixel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;

public class OSMTry extends Composite {

	Map map;
	private TextArea reportArea = new TextArea() {
		{
			this.setHeight("20em");
		}
	};

	LonLat lonLat = new LonLat(16.374655723173, 48.216523857447);

	public OSMTry() {

		MapOptions defaultMapOptions = new MapOptions();
		MapWidget mapWidget = new MapWidget("250px", "200px", defaultMapOptions);


		OSM osm_2 = OSM.Mapnik("Mapnik");   // Label for menu 'LayerSwitcher'
		osm_2.setIsBaseLayer(true);

		map = mapWidget.getMap();
		map.addLayer(osm_2);

		lonLat.transform("EPSG:4326", "EPSG:900913");

		map.setCenter(lonLat, 12);

		MYMarker noDrag = new MYMarker(lonLat,true);
		//MYMarker drag = new MYMarker(lonLat,true);
		//drag.setDragable(true);

		map.addLayer(noDrag.createLayer());
		//map.addLayer(drag.createLayer());

		initWidget(mapWidget);
	}


	class MYMarker{

		LonLat _lonLat;
		DragFeature dragFeature;
		boolean _dragable;

		public MYMarker(LonLat lonLat, boolean dragable) {
			_lonLat=lonLat;
			_dragable=dragable;
		}


		public Layer createLayer() {
			// Create the vectorOptions
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
			Vector layer = new Vector("Vector Layer", vectorOptions);
			Point point = new Point(_lonLat.lon(), _lonLat.lat());
			VectorFeature pointFeature = new VectorFeature(point);
			layer.addFeature(pointFeature);
			DragFeatureOptions dragFeatureOptions = new DragFeatureOptions();


			dragFeature = new DragFeature(layer, dragFeatureOptions);


			map.addControl(dragFeature);
			if(_dragable)dragFeature.activate();
			else dragFeature.deactivate();


			dragFeatureOptions.onComplete(new DragFeatureListener() {
				@Override
				public void onDragEvent(VectorFeature vectorFeature, Pixel pixel) {

					vectorFeature.getCenterLonLat().transform("EPSG:900913", "EPSG:4326");
					System.out.println("dragEnd: lat: "+vectorFeature.getCenterLonLat().lat()+", lng: "+vectorFeature.getCenterLonLat().lon());

				}
			});

			return layer;

		}

	}
}


