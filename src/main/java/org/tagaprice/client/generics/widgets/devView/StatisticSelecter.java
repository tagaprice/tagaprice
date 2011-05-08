package org.tagaprice.client.generics.widgets.devView;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.tagaprice.client.generics.widgets.IStatisticSelecter;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;

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
	Map osmMap;
	Vector layer = new Vector("shops");

	public StatisticSelecter() {
		initWidget(vePa1);

		//inti Maps
		MapOptions defaultMapOptions = new MapOptions();
		MapWidget omapWidget = new MapWidget("300px", "200px", defaultMapOptions);
		OSM osm_2 = OSM.Mapnik("Mapnik");   // Label for menu 'LayerSwitcher'
		osm_2.setIsBaseLayer(true);
		osmMap = omapWidget.getMap();
		osmMap.addLayer(osm_2);
		vePa1.add(omapWidget);

		LonLat lonLat = new LonLat(16.37692,48.21426);
		lonLat.transform("EPSG:4326", "EPSG:900913");
		osmMap.setCenter(lonLat, 15);
		osmMap.addLayer(layer);

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



		osmMap.addMapMoveEndListener(new MapMoveEndListener() {

			@Override
			public void onMapMoveEnd(MapMoveEndEvent eventObject) {
				System.out.println("mapMoved: ");
			}
		});


		//Test Data

		ArrayList<StatisticResult> test = new ArrayList<StatisticResult>();
		{
			Shop s1 = new Shop(null, "Billa - Blumauergasse 1B");
			s1.setAddress(new Address("Blumauergasse 1B", 48.21890, 16.38197));
			test.add(new StatisticResult(
					new Date(),
					s1,
					null,
					new Quantity(new BigDecimal("500"),new Unit(null, "ml")),
					new Price(new BigDecimal("20.3"), Currency.euro)));



		}
		{
			Shop s1 = new Shop(null, "Billa - Holzhausergasse 9");
			s1.setAddress(new Address("Holzhausergasse 9", 48.21977, 16.38901));
			test.add(new StatisticResult(
					new Date(),
					s1,
					null,
					new Quantity(new BigDecimal("200"),new Unit(null, "ml")),
					new Price(new BigDecimal("15"), Currency.euro)));
		}
		setStatisticResults(test);

	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		for(StatisticResult s:results){
			resultList.add(new Label(
					s.getPrice().getPrice().toString()+""+
					s.getPrice().getCurrency()+" "+
					s.getQuantity().getQuantity().toString()+""+
					s.getQuantity().getUnit().getTitle()+" "+
					s.getShop().getTitle()));

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

}
