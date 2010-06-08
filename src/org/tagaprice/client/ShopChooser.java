/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: ShopChooser.java
 * Date: May 26, 2010
 */
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.SearchWidget.Filter;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MapDragHandler;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;
import com.google.gwt.maps.client.event.MapDragHandler.MapDragEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ShopChooser extends Composite{
	interface MyUiBinder extends UiBinder<VerticalPanel, ShopChooser>{}
	MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField VerticalPanel verticalPanel;

	private SearchWidget searchWidget;

	private final MapWidget map;
	private TaPManager tapManager;
	private ArrayList<ShopData> shops;
	private final ReceiptWidget parent;

	public ShopChooser(final ReceiptWidget parent){
		this.parent = parent;

		tapManager = new TaPManagerImpl();

		verticalPanel = new VerticalPanel();

		searchWidget = new SearchWidget(Filter.SHOP);
		verticalPanel.add(searchWidget);

		map = new MapWidget(LatLng.newInstance(48.2092, 16.3728 ), 11);
		map.setSize("500px", "500px");
		map.addMapType(MapType.getNormalMap());
		map.addControl(new LargeMapControl());
		//map.addOverlay(new Marker(LatLng.newInstance(48.2092, 16.3728 )));
		showShopsOnMap();

		verticalPanel.add(map);

		initWidget(verticalPanel);

		map.addMapClickHandler(new MapClickHandler() {

			@Override
			public void onClick(MapClickEvent event) {
				if(event.getOverlayLatLng()!=null){
					parent.setShop(tapManager.getShop(event.getOverlayLatLng().getLatitude(), event.getOverlayLatLng().getLongitude()));	
				}else{
					//create new shop at latlng
					
				}
			}
		});


		map.addMapDragEndHandler(new MapDragEndHandler() {

			@Override
			public void onDragEnd(MapDragEndEvent event) {
				showShopsOnMap();
			}
		});

	}

	private void showShopsOnMap(){
		map.clearOverlays();
		
		ArrayList<ShopData> result = tapManager.searchShops(map.getBounds(), searchWidget);
		searchWidget.setShopSuggestions(result);
		for(ShopData sd:result){
			if(((ShopData) sd).getAddress()!=null)
				map.addOverlay(new Marker(LatLng.newInstance(((ShopData) sd).getLat(), ((ShopData) sd).getLng())));

		}				

	}


}
