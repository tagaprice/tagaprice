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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PriceMapWidget extends Composite {

	public enum PriceMapType {PRODUCT, SHOP, PRODUCTGROUP, SHOPGROUP}
	
	private TitlePanel titlePanel;
	private VerticalPanel vePa1 = new VerticalPanel();
	private FlexTable priceTable = new FlexTable();
	private PriceMapType type;
	private MapWidget map;
	private long id;
	
	/**
	 * 
	 * @param id
	 * @param myType 
	 * 
	 * myType = PRODUCT (id=Product_id)
	 * Shows a map with shops in which you can find this product plus a price table.
	 *  
	 * myType = SHOP (id=shop_id)
	 * Shows a grid with all products you can find in a shop plus a price table.
	 * 
	 * 
	 * myType = PRODUCTGROUP (id=product_group_id (e.g. Milk id))
	 * Shows a map with shops in which you can find this product-types plus a price table.
	 * 
	 * myType = SHOPGROUP (id=shop_group_id (e.g. Billa id))
	 * Shows a map with all shops of shop_group. (Maybe this should be changed to Brand-Group)
	 */
	public PriceMapWidget(long id, PriceMapType myType){
		type=myType;
		this.id=id;
		vePa1.setWidth("100%");
		priceTable.setWidth("100%");	
		titlePanel=new TitlePanel("Pricetable", vePa1, Level.H2);
		
		
		if(type.equals(PriceMapType.PRODUCT) || type.equals(PriceMapType.PRODUCTGROUP)  || type.equals(PriceMapType.SHOPGROUP)){
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
		}else if (type.equals(PriceMapType.SHOP)){
			getPrices();
		}
		
		
		vePa1.add(priceTable);
		
		initWidget(titlePanel);
	}
	
	
	private void getPrices(){
		if (type.equals(PriceMapType.SHOP)){
			HandlerManager.getPriceHandler().get(id, null, type, new AsyncCallback<ArrayList<PriceData>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					System.out.println("Price Load Error");
				}

				@Override
				public void onSuccess(ArrayList<PriceData> result) {
					refreshData(result);
				}
			});
		}else{
			//Get Prices
			HandlerManager.getPriceHandler().get(
					id, 
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
			priceTable.setText(0, 0+pinOff, "Products");
			colOff=0;
		}else if(type.equals(PriceMapType.PRODUCT)){
			pinOff=0;
			priceTable.setText(0, 0+pinOff, "Shops");
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
		for(final PriceData pd:list){
			
			priceTable.setText(row, 0,"*");
			
			if(type.equals(PriceMapType.SHOP)){
				Label tempProductL = new Label(pd.getProductData().getTitle());
				priceTable.setWidget(row, 0+pinOff, tempProductL);
				tempProductL.addClickHandler(new ClickHandler() {				
					public void onClick(ClickEvent event) {
						History.newItem("product/get&id="+pd.getProductData().getId());						
					}
				});
			}else {
				Label tempShopL = new Label(pd.getShopData().getTitle());
				priceTable.setWidget(row, 0+pinOff, tempShopL);
				tempShopL.addClickHandler(new ClickHandler() {				
					public void onClick(ClickEvent event) {
						History.newItem("shop/get&id="+pd.getShopData().getId());						
					}
				});
			}
			priceTable.setWidget(row, 2+pinOff+colOff, new RatingWidget(pd.getProductData().getRating(), false));
			priceTable.setText(row, 3+pinOff+colOff, DateTimeFormat.getLongDateFormat().format(pd.getDate()));
			priceTable.setText(row, 4+pinOff+colOff, ""+(pd.getPrice().getPrice()/100.00)+""+pd.getPrice().getCurrency().getTitle());
			
			if(!type.equals(PriceMapType.SHOP))
				map.addOverlay(new Marker(LatLng.newInstance(pd.getShopData().getAddress().getLat(), pd.getShopData().getAddress().getLng())));
			
			
			row++;
		}
	}
}
