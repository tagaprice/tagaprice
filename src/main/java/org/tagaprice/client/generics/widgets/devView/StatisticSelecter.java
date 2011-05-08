package org.tagaprice.client.generics.widgets.devView;

import java.util.Date;
import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.event.MapMoveEndListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.tagaprice.client.generics.widgets.IStatisticChangeHandler;
import org.tagaprice.client.generics.widgets.IStatisticSelecter;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class StatisticSelecter extends Composite implements IStatisticSelecter {

	VerticalPanel vePa1 = new VerticalPanel();
	DatePicker beginDate = new DatePicker();
	DatePicker endDate = new DatePicker();

	VerticalPanel resultList = new VerticalPanel();

	//OSM
	Map _osmMap;
	Vector layer = new Vector("shops");
	IStatisticChangeHandler _handler;

	public StatisticSelecter() {
		initWidget(vePa1);

		//inti Maps
		MapOptions defaultMapOptions = new MapOptions();
		MapWidget omapWidget = new MapWidget("300px", "200px", defaultMapOptions);
		OSM osm_2 = OSM.Mapnik("Mapnik");   // Label for menu 'LayerSwitcher'
		osm_2.setIsBaseLayer(true);
		_osmMap = omapWidget.getMap();
		_osmMap.addLayer(osm_2);
		vePa1.add(omapWidget);

		LonLat lonLat = new LonLat(16.37692,48.21426);
		lonLat.transform("EPSG:4326", "EPSG:900913");
		_osmMap.setCenter(lonLat, 15);
		_osmMap.addLayer(layer);

		//Datens
		vePa1.add(new Label("Set date range"));
		HorizontalPanel hoPa1 = new HorizontalPanel();
		hoPa1.add(beginDate);
		hoPa1.add(endDate);
		setDate(new Date(), new Date());
		vePa1.add(hoPa1);


		//Add resultList
		vePa1.add(new Label("DetailList"));
		vePa1.add(resultList);



		_osmMap.addMapMoveEndListener(new MapMoveEndListener() {

			@Override
			public void onMapMoveEnd(MapMoveEndEvent eventObject) {
				sendSomethingChanged();
			}
		});


		beginDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> arg0) {
				sendSomethingChanged();
			}
		});

		endDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> arg0) {
				sendSomethingChanged();
			}
		});




	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		layer.destroyFeatures();
		resultList.clear();
		for(StatisticResult s:results){
			resultList.add(new Label(
					s.getPrice().getPrice().toString()+""+
					s.getPrice().getCurrency()+" "+
					s.getQuantity().getQuantity().toString()+""+
					s.getQuantity().getUnit().getTitle()+" "+
					s.getShop().getTitle()+""+Math.random()));

			LonLat l = new LonLat(s.getShop().getAddress().getLng(), s.getShop().getAddress().getLat());
			l.transform("EPSG:4326", "EPSG:900913");
			Point point = new Point(l.lon(), l.lat());
			VectorFeature pointFeature = new VectorFeature(point);
			layer.addFeature(pointFeature);
		}

	}

	@Override
	public void setDate(Date begin, Date end) {
		beginDate.setValue(begin);
		endDate.setValue(end);
	}

	@Override
	public void addStatisticChangeHandler(IStatisticChangeHandler handler) {
		_handler=handler;
	}

	private void sendSomethingChanged(){
		if(_handler!=null){

			LonLat southWest = new LonLat(_osmMap.getExtent().getLowerLeftY(), _osmMap.getExtent().getLowerLeftX());
			LonLat northEast = new LonLat(_osmMap.getExtent().getUpperRightY(), _osmMap.getExtent().getUpperRightX());
			southWest.transform("EPSG:900913","EPSG:4326");
			northEast.transform("EPSG:900913","EPSG:4326");


			_handler.onChange(new BoundingBox(
					southWest.lat(),
					southWest.lon(),
					northEast.lat(),
					northEast.lon()), beginDate.getValue(), endDate.getValue());
		}
	}

}
