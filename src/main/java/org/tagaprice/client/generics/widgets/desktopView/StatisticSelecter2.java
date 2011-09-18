package org.tagaprice.client.generics.widgets.desktopView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.StyleMap;
import org.gwtopenmaps.openlayers.client.event.MapMoveEndListener;
import org.gwtopenmaps.openlayers.client.event.MapZoomListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.tagaprice.client.generics.widgets.IStatisticChangeHandler;
import org.tagaprice.client.generics.widgets.IStatisticSelecter;
import org.tagaprice.shared.entities.Address.LatLon;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import org.tagaprice.shared.entities.productmanagement.Package;

public class StatisticSelecter2 extends Composite implements IStatisticSelecter {

	private TYPE _type = null;
	private VerticalPanel _vePa = new VerticalPanel();
	private DatePicker _beginDate = new DatePicker();
	private DatePicker _endDate = new DatePicker();
	private IStatisticChangeHandler _handler;

	private VectorOptions _osmVectorOptions = new VectorOptions();
	private MapOptions _osmMapOptions = new MapOptions();
	private Map _osmMap;
	private MapWidget _osmMapWidget;
	private Vector _osmMarkerLayer;
	private SimplePanel _tablepanel = new SimplePanel();

	public StatisticSelecter2() {
		_vePa.setWidth("100%");
		initWidget(_vePa);

		// map
		// search map
		OSM osmLayler = OSM.Mapnik("Mapnik");
		osmLayler.setIsBaseLayer(true);

		_osmMapWidget = new MapWidget("100%", "200px", _osmMapOptions);
		_osmMap = _osmMapWidget.getMap();
		_osmMap.addLayer(osmLayler);

		// ******** INIT OSM Vector ************/
		// Style
		// Create the style for each purpose
		Style styleNormal = createStyle("#FF0000");
		Style styleSelected = createStyle("#43aabe");
		Style styleHighlighted = createStyle("#43aabe");
		// Create the StyleMap to handle all styles
		StyleMap styleMapVector = new StyleMap(styleNormal, styleSelected,
				styleHighlighted);

		_osmVectorOptions.setStyleMap(styleMapVector);

		_osmMarkerLayer = new Vector("shopResults", _osmVectorOptions);
		_osmMap.addLayer(_osmMarkerLayer);	
		_osmMap.zoomTo(13);
		
		
		_osmMap.addMapZoomListener(new MapZoomListener() {
			
			@Override
			public void onMapZoom(MapZoomEvent eventObject) {
				sendChangeRequest();				
			}
		});

		_osmMap.addMapMoveEndListener(new MapMoveEndListener() {

			@Override
			public void onMapMoveEnd(MapMoveEndEvent eventObject) {
				sendChangeRequest();
			}
		});


		_beginDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> arg0) {
				sendChangeRequest();
			}
		});

		_endDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> arg0) {
				sendChangeRequest();
			}
		});
		

		_vePa.add(_osmMapWidget);
		_tablepanel.setWidth("100%");
		_tablepanel.setWidget(new Label("loading..."));
		_vePa.add(_tablepanel);
		
	}

	private Style createStyle(String fillColor) {
			Style style = new Style();
			style.setStrokeColor("#000000");
			style.setStrokeWidth(3);
			style.setFillColor(fillColor);
			style.setFillOpacity(0.5);
			style.setPointRadius(10);
			style.setStrokeOpacity(1.0);
		return style;

	}

	@Override
	public void setLatLon(double lat, double lon) {
		LonLat lonLat = new LonLat(lon,lat);
		lonLat.transform("EPSG:4326", "EPSG:900913");	
		_osmMap.setCenter(lonLat);

	}

	private  LatLon getLatLon(){
		LonLat ll = _osmMap.getCenter();
		ll.transform("EPSG:900913", "EPSG:4326");
		return new LatLon(ll.lat(), ll.lon());
		
	}
	
	@Override
	public void setType(TYPE type) {
		_type = type;
	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		drawProductCategory(results);
	}

	private void drawProductCategory(List<StatisticResult> results){
		
		
		_osmMarkerLayer.destroyFeatures();
		
		//shop product package
		final HashMap<String, HashMap<String, HashMap<String, StatisticResult>>> shopSortList = new HashMap<String, HashMap<String,HashMap<String, StatisticResult>>>();
		final HashMap<String, StatisticResult> shopList = new HashMap<String, StatisticResult>();
		final HashMap<String, StatisticResult> productList = new HashMap<String, StatisticResult>();
		for(StatisticResult sr:results){
			//shop
			if(shopSortList.get(sr.getShop().getId())==null){
				shopSortList.put(sr.getShop().getId(), new HashMap<String, HashMap<String, StatisticResult>>());
			}
			shopList.put(sr.getShop().getId(), sr);
			
			//product
			if(shopSortList.get(sr.getShop().getId()).get(sr.getProduct().getId())==null){
				shopSortList.get(sr.getShop().getId()).put(sr.getProduct().getId(), new HashMap<String, StatisticResult>());
			}
			productList.put(sr.getProduct().getId(), sr);
			
			//package
			if(shopSortList.get(sr.getShop().getId()).get(sr.getProduct().getId()).get(sr.getPackage().getId())==null){
				shopSortList.get(sr.getShop().getId()).get(sr.getProduct().getId()).put(sr.getPackage().getId(), sr);
			}else{
				//Overwrite old date
				if(shopSortList.get(sr.getShop().getId()).get(sr.getProduct().getId()).get(sr.getPackage().getId()).getDate().before(sr.getDate())){
					shopSortList.get(sr.getShop().getId()).get(sr.getProduct().getId()).put(sr.getPackage().getId(), sr);
				}
			}
			
		}
		
		
		
		FlexTable shopFlex = new FlexTable();
		shopFlex.setStyleName("statisticTable");
		shopFlex.setWidth("100%");
		
		final int shopProductC;
		
		if(_type.equals(TYPE.PRODUCT) || _type.equals(TYPE.SHOP)){
			shopProductC=0;
		}else{
			shopProductC=1;
		}
		
		if(!_type.equals(TYPE.SHOP))
				shopFlex.setText(0, 0, "Shop");
		if(!_type.equals(TYPE.PRODUCT))
			shopFlex.setText(0, 0+shopProductC, "Product");
		shopFlex.setText(0, 1+shopProductC, "Size");
		shopFlex.setText(0, 2+shopProductC, "Date");
		shopFlex.setText(0, 3+shopProductC, "Price");
		shopFlex.getRowFormatter().setStyleName(0, "statisticTable-head");
		
		//test output
		int rk=1;
		for(final String sk:shopSortList.keySet()){
			if(!_type.equals(TYPE.SHOP)){
				Label sl = new Label(shopList.get(sk).getShop().getTitle());
				sl.setStyleName("statisticTable-shop-cell-link");
				sl.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent arg0) {
						History.newItem("shop:/null/id/"+shopList.get(sk).getShop().getId()+"/lat/"+getLatLon().getLat()+"/lon/"+getLatLon().getLon());
					}
				});
				shopFlex.setWidget(rk, 0, sl);
			}
			if(!_type.equals(TYPE.SHOP))
				shopFlex.getCellFormatter().setStyleName(rk, 0, "statisticTable-shop-cell");
			if(!_type.equals(TYPE.PRODUCT))
				shopFlex.getCellFormatter().setStyleName(rk, 0+shopProductC, "statisticTable-shop-cell");
			shopFlex.getCellFormatter().setStyleName(rk, 1+shopProductC, "statisticTable-shop-cell");
			shopFlex.getCellFormatter().setStyleName(rk, 2+shopProductC, "statisticTable-shop-cell");
			shopFlex.getCellFormatter().setStyleName(rk, 3+shopProductC, "statisticTable-shop-cell");
			
			
			//add map Marker
			LonLat lonLat = shopList.get(sk).getShop().getAddress().getPos().toLonLat();
			lonLat.transform("EPSG:4326", "EPSG:900913");
			Point point = new Point(lonLat.lon(), lonLat.lat());
			VectorFeature pointFeature = new VectorFeature(point);
			
			_osmMarkerLayer.addFeature(pointFeature);
			
			
			rk++;
			for(final String prK: shopSortList.get(sk).keySet()){
				if(!_type.equals(TYPE.PRODUCT)){
					Label pl = new Label(productList.get(prK).getProduct().getTitle());
					pl.setStyleName("statisticTable-cell-link");
					pl.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent arg0) {
							History.newItem("product:/null/id/"+productList.get(prK).getProduct().getId()+"/lat/"+getLatLon().getLat()+"/lon/"+getLatLon().getLon());						
						}
					});
					shopFlex.setWidget(rk, 0+shopProductC, pl);
				}

				if(!_type.equals(TYPE.SHOP))
					shopFlex.getCellFormatter().setStyleName(rk, 0, "statisticTable-cell");
				if(!_type.equals(TYPE.PRODUCT))
					shopFlex.getCellFormatter().setStyleName(rk, 0+shopProductC, "statisticTable-cell");
				shopFlex.getCellFormatter().setStyleName(rk, 1+shopProductC, "statisticTable-cell");
				shopFlex.getCellFormatter().setStyleName(rk, 2+shopProductC, "statisticTable-cell");
				shopFlex.getCellFormatter().setStyleName(rk, 3+shopProductC, "statisticTable-cell");
				rk++;
				for(String paK: shopSortList.get(sk).get(prK).keySet()){
					shopFlex.setWidget(rk, 1+shopProductC, new Label(""+shopSortList.get(sk).get(prK).get(paK).getPackage().getQuantity().getQuantity()+"/"+shopSortList.get(sk).get(prK).get(paK).getPackage().getQuantity().getUnit().getTitle()));
					shopFlex.setWidget(rk, 2+shopProductC, new Label(""+shopSortList.get(sk).get(prK).get(paK).getDate()));
					shopFlex.setWidget(rk, 3+shopProductC, new Label(""+shopSortList.get(sk).get(prK).get(paK).getPrice().getPrice()+"/"+shopSortList.get(sk).get(prK).get(paK).getPrice().getCurrency()));

					if(!_type.equals(TYPE.SHOP))
						shopFlex.getCellFormatter().setStyleName(rk, 0, "statisticTable-cell");
					if(!_type.equals(TYPE.PRODUCT))
						shopFlex.getCellFormatter().setStyleName(rk, 0+shopProductC, "statisticTable-cell");
					shopFlex.getCellFormatter().setStyleName(rk, 1+shopProductC, "statisticTable-cell");
					shopFlex.getCellFormatter().setStyleName(rk, 2+shopProductC, "statisticTable-cell");
					shopFlex.getCellFormatter().setStyleName(rk, 3+shopProductC, "statisticTable-cell");
					rk++;
				}
			}
		}
		
		_tablepanel.setWidget(shopFlex);
		
		
	}
	
	private void drawProductCategory2(List<StatisticResult> results){
		
		CellTable<StatisticResult> table = new CellTable<StatisticResult>();
		

		//productColumn
		TextColumn<StatisticResult> productNameColumn = new TextColumn<StatisticResult>() {
		
			@Override
		      public String getValue(StatisticResult object) {
		        return object.getProduct().getTitle();
		      }
		};
		table.addColumn(productNameColumn, "Product");
		
		//ShopColumn
		TextColumn<StatisticResult> shopNameColomn = new TextColumn<StatisticResult>() {
		
			@Override
		      public String getValue(StatisticResult object) {
		        return object.getShop().getTitle();
		      }
		};
		table.addColumn(shopNameColomn, "Shop");
		
		//SizeColumn
		TextColumn<StatisticResult> sizeNameColumn = new TextColumn<StatisticResult>() {
			
			@Override
			public String getValue(StatisticResult object) {
				return object.getQuantity().getQuantity().toEngineeringString()+"/"+object.getQuantity().getUnit().getTitle();
			}
		};
		table.addColumn(sizeNameColumn, "Size");
		
		//DateColumn
		Column<StatisticResult, Date> sizeDateColumn = new Column<StatisticResult, Date>(
		        new DateCell()) {
		      @Override
		      public Date getValue(StatisticResult object) {
		        return object.getDate();
		      }
		    };
		table.addColumn(sizeDateColumn, "Date");
		
		//DateColumn
		TextColumn<StatisticResult> sizePriceColumn = new TextColumn<StatisticResult>() {
			
			@Override
			public String getValue(StatisticResult object) {
				return object.getPrice().getPrice().toEngineeringString()+" "+object.getPrice().getCurrency();
			}
		};
		table.addColumn(sizePriceColumn, "Prize");
		
		table.setRowData(results);
		table.setWidth("100%");
		_tablepanel.setWidget(table);
	}
	
	@Override
	public void setDate(Date begin, Date end) {
		_beginDate.setValue(begin);
		_endDate.setValue(end);

		_beginDate.setCurrentMonth(begin);
		_endDate.setCurrentMonth(end);

	}

	private void sendChangeRequest() {
		if (_handler != null) {
			_handler.onChange(getBoundingBox(), getBeginDate(), getEndDate());
		}
	}

	@Override
	public void addStatisticChangeHandler(IStatisticChangeHandler handler) {
		_handler = handler;
	}

	@Override
	public BoundingBox getBoundingBox() {
		LonLat southWest = new LonLat(_osmMap.getExtent().getLowerLeftX(), _osmMap.getExtent().getLowerLeftY());
		LonLat northEast = new LonLat(_osmMap.getExtent().getUpperRightX(), _osmMap.getExtent().getUpperRightY());
		southWest.transform("EPSG:900913","EPSG:4326");
		northEast.transform("EPSG:900913","EPSG:4326");
		return new BoundingBox(
				southWest.lat(),
				southWest.lon(),
				northEast.lat(),
				northEast.lon());
	}

	@Override
	public Date getBeginDate() {
		return _beginDate.getValue();
	}

	@Override
	public Date getEndDate() {
		return _endDate.getValue();
	}

	@Override
	public void setMapVisible(boolean visible) {
		_osmMapWidget.setVisible(visible);
	}

}
