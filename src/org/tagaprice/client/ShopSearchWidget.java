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

import org.tagaprice.client.NewPreview.Filter;
import org.tagaprice.shared.ShopData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ShopSearchWidget extends SearchWidget{
	interface MyUiBinder extends UiBinder<VerticalPanel, ShopSearchWidget>{}
	MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	//	@UiField VerticalPanel suggestPanel;

	private final MapWidget map;
	//private ArrayList<ShopData> shopsData;
	private final ReceiptWidget parent;
	private ListWidget<ShopPreview> shopList; 


	public ShopSearchWidget(ReceiptWidget parentReceipt){
		super();
		parent = parentReceipt;

		map = new MapWidget(LatLng.newInstance(48.2092, 16.3728 ), 11);
		map.setSize("400px", "200px");
		map.addMapType(MapType.getNormalMap());
		map.addControl(new LargeMapControl());
		basePanel.add(map);

		addTextBox();

		suggestPanel = new VerticalPanel();
		shopList =new ListWidget<ShopPreview>();
		suggestPanel.add(shopList);
		basePanel.add(suggestPanel);

		showShopsOnMap(null);

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
				showShopsOnMap(textBox.getText());
			}
		});

	}

	@Override
	protected ListWidget<ShopPreview> getSuggestionList() {	
		return shopList;
	}

	/**
	 * 
	 * **/
	private void showShopsOnMap(String searchString){
		map.clearOverlays();
		ArrayList<ShopData> result;
		if(searchString==null){
			result = tapManager.searchShops(map.getBounds(), this);
		} else{
			result = tapManager.searchShops(map.getBounds(), searchString, this);
		} 
		setShopSuggestions(result);
		for(ShopData sd:result){
			if(((ShopData) sd).getAddress()!=null)
				map.addOverlay(new Marker(LatLng.newInstance(((ShopData) sd).getLat(), ((ShopData) sd).getLng())));

		}				

	}


	public void setShopSuggestions(ArrayList<ShopData> suggestData){
		shopList.populateShopList(suggestData);
		shopList.addSuggestion(new NewPreview(Filter.SHOP, parent));
		suggestPanel.setVisible(true);	
	}

	@Override
	protected void handleEnterKey() {
		if(shopList.getSelectionPreview() instanceof ShopPreview)
		parent.setShop(((ShopPreview)shopList.getSelectionPreview()).getShopData());
		else parent.setNewShop();
	}

	@Override
	protected void sendSearchRequest(String searchString) {
		showShopsOnMap(searchString);

	}





}
