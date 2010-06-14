/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: PriceMapWidget.java
 * Date: 02.06.2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.TitlePanel.Level;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.PriceData;

import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MapMoveEndHandler;
import com.google.gwt.maps.client.event.MapDragEndHandler.MapDragEndEvent;
import com.google.gwt.maps.client.event.MapMoveEndHandler.MapMoveEndEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PriceMapWidget extends Composite {

	public enum PriceMapType {PRODUCT, SHOP, PRODUCTGROUP, SHOPGROUP}
	
	private TitlePanel titlePanel;
	private VerticalPanel vePa1 = new VerticalPanel();
	private FlexTable priceTable = new FlexTable();
	private PriceMapType type;
	private MapWidget map;
	private TaPManager mng = TaPManagerImpl.getInstance();
	private long id;
	
	/**
	 * 
	 * @param id
	 * @param myType
	 */
	public PriceMapWidget(long id, PriceMapType myType){
		type=myType;
		this.id=id;
		vePa1.setWidth("100%");
		priceTable.setWidth("100%");	
		titlePanel=new TitlePanel("Pricetable", vePa1, Level.H2);
		
		
		if(type.equals(PriceMapType.SHOP) || type.equals(PriceMapType.PRODUCTGROUP)  || type.equals(PriceMapType.SHOPGROUP)){
			Geolocation myGeo = Geolocation.getGeolocation();
			map=new MapWidget();
			map.setZoomLevel(14);
			myGeo.getCurrentPosition(new PositionCallback() {
				
				@Override
				public void onSuccess(Position position) {					
					map.setCenter(LatLng.newInstance(position.getCoords().getLatitude(), position.getCoords().getLongitude()));
					getPrices();
				}
				
				@Override
				public void onFailure(PositionError error) {
					// TODO Auto-generated method stub
					System.out.println("position error");
				}
			});
			
			
			
			map.setWidth("100%");
			map.setHeight("200px");
			vePa1.add(map);
			
			//MapMoveListen			
			map.addMapDragEndHandler(new MapDragEndHandler() {
				
				@Override
				public void onDragEnd(MapDragEndEvent event) {
					getPrices();
				}
			});
		}
		
		
		vePa1.add(priceTable);
		
		initWidget(titlePanel);
	}
	
	
	private void getPrices(){
		//Get Prices
		mng.getPrice(id, 
				new BoundingBox(
						map.getBounds().getSouthWest().getLatitude(), 
						map.getBounds().getSouthWest().getLongitude(), 
						map.getBounds().getNorthEast().getLatitude(), 
						map.getBounds().getNorthEast().getLongitude()), 
				type, new AsyncCallback<ArrayList<PriceData>>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println("Price Load Error");
					}

					@Override
					public void onSuccess(ArrayList<PriceData> result) {
						// TODO Auto-generated method stub
						refreshData(result);
					}
			
		});
	}
	
	private void refreshData(ArrayList<PriceData> list){
		int pinOff=1;
		int colOff=0;
		
		//CreateTable
		priceTable.removeAllRows();
		
		//Set Header
		
		if(type.equals(PriceMapType.SHOP)){
			priceTable.setText(0, 0, "Pin");
			pinOff=1;
			priceTable.setText(0, 0+pinOff, "Shops");
			colOff=0;
		}else if(type.equals(PriceMapType.PRODUCT)){
			pinOff=0;
			priceTable.setText(0, 0+pinOff, "Product");
			colOff=0;
		}else{
			priceTable.setText(0, 0, "Ping");
			pinOff=1;
			priceTable.setText(0, 0+pinOff, "Product");
			priceTable.setText(0, 1+pinOff, "Shops");
			colOff=1;
		}
		
		priceTable.setText(0, 2+pinOff+colOff, "Quality");
		priceTable.setText(0, 3+pinOff+colOff, "Date");
		priceTable.setText(0, 4+pinOff+colOff, "Price");
		
		
		
		//Create Table
		if(map!=null)
			map.clearOverlays();
		
		int row=1;
		for(PriceData pd:list){
			priceTable.setText(row, 0,"*");
			priceTable.setText(row, 0+pinOff, pd.getShopData().getName());
			priceTable.setWidget(row, 2+pinOff+colOff, new RatingWidget(pd.getProductData().getRating(), false));
			priceTable.setText(row, 3+pinOff+colOff, "1-1-01");
			priceTable.setText(row, 4+pinOff+colOff, ""+pd.getProductData().getPrice().getPrice());
			
			map.addOverlay(new Marker(LatLng.newInstance(pd.getShopData().getAddress().getLat(), pd.getShopData().getAddress().getLng())));
			
			row++;
		}
	}
}
