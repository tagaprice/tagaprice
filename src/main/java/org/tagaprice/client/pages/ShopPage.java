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
 * Filename: ShopPage.java
 * Date: May 26, 2010
*/
package org.tagaprice.client.pages;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.HandlerManager;
import org.tagaprice.client.InfoBox;
import org.tagaprice.client.MorphWidgetErrorHandler;
import org.tagaprice.client.MyResources;
import org.tagaprice.client.RatingWidget;
import org.tagaprice.client.TaPManager;
import org.tagaprice.client.TypeWidget;
import org.tagaprice.client.TypeWidgetHandler;
import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.propertyhandler.DefaultPropertyHandler;
import org.tagaprice.client.propertyhandler.IPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.PropertyChangeHandler;
import org.tagaprice.client.widgets.MorphWidget;
import org.tagaprice.client.widgets.PriceMapWidget;
import org.tagaprice.client.widgets.ProgressWidget;
import org.tagaprice.client.widgets.PriceMapWidget.PriceMapType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShopPage extends Page {

	private ShopData shopData;
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	private HashMap<String, ArrayList<PropertyData>> hashProperties = new HashMap<String, ArrayList<PropertyData>>();
	private ArrayList<IPropertyHandler> handlerList = new ArrayList<IPropertyHandler>();
	private PropertyChangeHandler handler;
	private InfoBox bottomInfo = new InfoBox();
	private SimplePanel typeWidgetContainer = new SimplePanel();
	private SimplePanel propertyHandlerContainer = new SimplePanel();
	private PriceMapWidget priceMap;
	private MorphWidget titleMorph = new MorphWidget("", Datatype.STRING, true);
	private MapWidget mapWidget = new MapWidget();
	private Marker marker;
	private Geocoder geoCoder = new Geocoder();
	
	//Address
	private MorphWidget street = new MorphWidget("", Datatype.STRING, true);
	private MorphWidget zip = new MorphWidget("", Datatype.STRING, true);
	private MorphWidget county = new MorphWidget("", Datatype.STRING, true);
	private MorphWidget country = new MorphWidget("", Datatype.STRING, true);
	private Button showMapButton = new Button("Show on Map");
	
	
	public ShopPage(ShopData _shopData, Type _type){
		init(vePa1);
		this.shopData=_shopData;
		this.type=_type;
		
		
		//Move PropertyData to hashPropertyData
		for(PropertyData pd:this.shopData.getProperties()){
			if(hashProperties.get(pd.getName())==null){
				hashProperties.put(pd.getName(), new ArrayList<PropertyData>());
			}			
			hashProperties.get(pd.getName()).add(pd);
		}
			
		
		//Listener
		handler=new PropertyChangeHandler() {
			
			@Override
			public void onSuccess() {
				showSave();				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		};
		
		//style		
		vePa1.setWidth("100%");
		
		//Header
		HorizontalPanel hoPa1 = new HorizontalPanel();
		hoPa1.setWidth("100%");
		vePa1.add(titleMorph);		
		titleMorph.setText(shopData.getTitle());
		titleMorph.setWidth("100%");
		vePa1.add(hoPa1);
		ProgressWidget progressWidget = new ProgressWidget(new Image(MyResources.INSTANCE.productPriview()), shopData.getProgress());
		hoPa1.add(progressWidget);
		
		VerticalPanel vePa2 = new VerticalPanel();
		vePa2.setWidth("100%");
		hoPa1.add(vePa2);
		hoPa1.setCellWidth(vePa2, "100%");
		
		//Type
		vePa2.add(typeWidgetContainer);	
		drawTypeWidget();
		
		//Rating
		vePa2.add(new RatingWidget(shopData.getRating(), false));
		
		
		//Add Adress and map
		mapWidget.setZoomLevel(14);
		mapWidget.setWidth("100%");
		mapWidget.setHeight("200px");
		MarkerOptions makerOption = MarkerOptions.newInstance();
		makerOption.setDraggable(true);
		if(shopData.getAddress().getLat()!=null){
			mapWidget.setCenter(LatLng.newInstance(shopData.getAddress().getLat(),shopData.getAddress().getLng()));
			marker = new Marker(LatLng.newInstance(shopData.getAddress().getLat(),shopData.getAddress().getLng()), makerOption);
		}else{
			marker = new Marker(mapWidget.getCenter(), makerOption);
		}
		mapWidget.addOverlay(marker);
		vePa1.add(mapWidget);
		
		Grid adressGrid = new Grid(4, 2);
		adressGrid.setWidth("100%");
		adressGrid.getCellFormatter().setWidth(0, 0, "100%");
		
		//Name Grid
		adressGrid.setText(0, 0, "Street");
		adressGrid.setText(1, 0, "ZIP");
		adressGrid.setText(2, 0, "County");
		adressGrid.setText(3, 0, "Country");	
		
		//AddWidgets
		adressGrid.setWidget(0, 1, street);
		adressGrid.setWidget(1, 1, zip);
		adressGrid.setWidget(2, 1, county);
		adressGrid.setWidget(3, 1, country);
		
		//SetText
		street.setText(shopData.getAddress().getStreet());
		//zip.setText(shopData.getAddress().get);
		county.setText(shopData.getAddress().getCity());
		country.setText(shopData.getAddress().getCountry().getCode());
		
		//Width
		//street.setWidth("100%");
		//zip.setWidth("100%");
		//county.setWidth("100%");
		//country.setWidth("100%");
		
		adressGrid.getCellFormatter().setStyleName(0, 0, "RegistrationPage-Row");
		adressGrid.getCellFormatter().setStyleName(1, 0, "RegistrationPage-Row");
		adressGrid.getCellFormatter().setStyleName(2, 0, "RegistrationPage-Row");
		adressGrid.getCellFormatter().setStyleName(3, 0, "RegistrationPage-Row");
		adressGrid.getCellFormatter().setStyleName(0, 1, "RegistrationPage-Row");
		adressGrid.getCellFormatter().setStyleName(1, 1, "RegistrationPage-Row");
		adressGrid.getCellFormatter().setStyleName(2, 1, "RegistrationPage-Row");
		adressGrid.getCellFormatter().setStyleName(3, 1, "RegistrationPage-Row");
		vePa1.add(adressGrid);
		
		showMapButton.setWidth("100%");
		vePa1.add(showMapButton);
		showMapButton.setVisible(false);
		
		showMapButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showMapButton.setText("Searching....");
				geoCoder.getLocations(street.getText().trim()+", "
						+zip.getText().trim()+", "
						+county.getText().trim()+", "
						+country.getText().trim(), new LocationCallback() {
							
							@Override
							public void onSuccess(JsArray<Placemark> locations) {
								mapWidget.setCenter(locations.get(0).getPoint());
								marker.setLatLng(locations.get(0).getPoint());
								showMapButton.setText("Show on map");
								showMapButton.setVisible(false);
								
								shopData.getAddress().setCoordinates(
										locations.get(0).getPoint().getLatitude(),
										locations.get(0).getPoint().getLongitude());
								
								street.setText(locations.get(0).getStreet());
								zip.setText(locations.get(0).getPostalCode());
								county.setText(locations.get(0).getCounty());
								country.setText(locations.get(0).getCountry());
								
								
								//TODO Problem with Country
								shopData.getAddress().setAddress(
										street.getText().trim(), 
										locations.get(0).getCity(), 
										new Country(
												locations.get(0).getCountry().toLowerCase(), 
												locations.get(0).getCountry(), 
												locations.get(0).getCountry()));	
								
								
							}
							
							@Override
							public void onFailure(int statusCode) {
								showMapButton.setText("Show on map");
								System.out.println("can't find address");						
							}
						});
				
				
			}
		});
		
		
		//Listener
		//Map Lister
		marker.addMarkerDragEndHandler(new MarkerDragEndHandler() {
			
			@Override
			public void onDragEnd(MarkerDragEndEvent event) {
				shopData.getAddress().setCoordinates(event.getSender().getLatLng().getLatitude(), event.getSender().getLatLng().getLongitude());
				
				geoCoder.getLocations(event.getSender().getLatLng(), new LocationCallback() {
					
					@Override
					public void onSuccess(JsArray<Placemark> locations) {
						
						shopData.getAddress().setAddress(
								locations.get(0).getStreet(), 
								locations.get(0).getCity(), 
								new Country(
										locations.get(0).getCountry().toLowerCase(), 
										locations.get(0).getCountry(), 
										locations.get(0).getCountry()));
						
						street.setText(locations.get(0).getStreet());
						zip.setText(locations.get(0).getPostalCode());
						county.setText(locations.get(0).getCounty());
						country.setText(locations.get(0).getCountry());
						
					}
					
					@Override
					public void onFailure(int statusCode) {
						//TODO here
						System.out.println("not Address found");
						
					}
				});
				
				
				showSave();	
			}
		});
		
		street.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			public void onSuccess(Datatype errorType) {	showMapButton.setVisible(true);}			
			public void onError(Datatype errorType) {}			
			public void onEmpty() {showMapButton.setVisible(true);}
		});
		
		zip.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			public void onSuccess(Datatype errorType) {	showMapButton.setVisible(true);}			
			public void onError(Datatype errorType) {}			
			public void onEmpty() {showMapButton.setVisible(true);}
		});
		
		county.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			public void onSuccess(Datatype errorType) {	showMapButton.setVisible(true);}			
			public void onError(Datatype errorType) {}			
			public void onEmpty() {showMapButton.setVisible(true);}
		});
		
		country.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			public void onSuccess(Datatype errorType) {	showMapButton.setVisible(true);}			
			public void onError(Datatype errorType) {}			
			public void onEmpty() {showMapButton.setVisible(true);}
		});
		
		
		
		//title Lister
		titleMorph.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			
			@Override
			public void onSuccess(Datatype errorType) {
				
				if(!shopData.getTitle().equals(titleMorph.getText())){
					shopData.setTitle(titleMorph.getText());
					showSave();				
				}
			}
			
			@Override
			public void onError(Datatype errorType) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onEmpty() {
				// TODO Auto-generated method stub
			}
		});
		
		
		
		
		//Add Price
		if(shopData.getId()!=null){
			final SimplePanel priceMapContaier = new SimplePanel();
			priceMapContaier.setWidth("100%");
			vePa1.add(priceMapContaier);
			
			GWT.runAsync(new RunAsyncCallback() {
				
				@Override
				public void onSuccess() {
					priceMap = new PriceMapWidget(shopData.getId(),PriceMapType.SHOP);
					priceMapContaier.setWidget(priceMap);					
				}
				
				@Override
				public void onFailure(Throwable reason) {
					showInfo("Download Error at PriceWidget", BoxType.WARNINGBOX);
					
				}
			});
			
			
		}
		
		
		//Create and Register Handler
		vePa1.add(propertyHandlerContainer);
		propertyHandlerContainer.setWidth("100%");
		registerHandler();
		
		
		
		
		vePa1.add(bottomInfo);
	}
	
	

	private void drawTypeWidget(){
		typeWidgetContainer.setWidget(new TypeWidget(type, new TypeWidgetHandler() {			
			@Override
			public void onChange(Type newType) {
				
				
				
				
				//Get type and set type
				HandlerManager.getTypeHandler().get(newType, new AsyncCallback<Type>() {
					@Override
					public void onFailure(Throwable caught) {
						showInfo("ProductPage getTypeError", BoxType.WARNINGBOX);
					}

					@Override
					public void onSuccess(Type result) {
						type=result;
						drawTypeWidget();	
						registerHandler();
						showSave();	
					}

				});
				
				
				
			}
		}));
	}
	
	private void registerHandler(){
		handlerList.clear();
		
		for(String ks:hashProperties.keySet()){
			for(PropertyData pd:hashProperties.get(ks)){
				pd.setRead(false);
			}
		}
		
		
		VerticalPanel hVePa = new VerticalPanel();
		hVePa.setWidth("100%");
		
		
		//Add Properties
		for(PropertyGroup pg:this.type.getPropertyGroups()){
			
			if(pg.getType().equals(PropertyGroup.GroupType.NUTRITIONFACTS)){
				/*
				NutritionFactsPropertyHandler temp = new NutritionFactsPropertyHandler(hashProperties, pg, handler);
				handlerList.add(temp);
				hVePa.add(temp);
				*/
			}else if (pg.getType().equals(PropertyGroup.GroupType.LIST)){				
				ListPropertyHandler temp= new ListPropertyHandler(hashProperties, pg, handler);
				handlerList.add(temp);
				hVePa.add(temp);
				
			}	
			
		}
		
		
		DefaultPropertyHandler defH = new DefaultPropertyHandler(hashProperties, handler);
		handlerList.add(defH);
		hVePa.add(defH);
		
		
		
		propertyHandlerContainer.setWidget(hVePa);
	}
	
	private void showSave(){
		if(TaPManager.getInstance().isLoggedIn()!=null){
			showSaveLogin();

		}else{
			showSaveNotLogin();
		}
	}
	
	private void showSaveLogin(){
		HorizontalPanel bottomInfoHoPa = new HorizontalPanel();
		bottomInfoHoPa.setWidth("100%");
		bottomInfoHoPa.setHeight("100%");
		Button topAbort=new Button("Abort", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				TaPManager.getInstance().showShopPage(shopData.getId());
				
			}
		});
		final Button topSave=new Button("Save");	
		topSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				topSave.setText("Saving...");
				
				shopData.setProperties(hashToPropertyList(hashProperties));
				shopData.setTypeId(new Long(type.getId()));
				
				
				//Validate Data
				if(PropertyValidator.isValid(type, shopData.getProperties())){	
				
					HandlerManager.getShopHandler().save(shopData, new AsyncCallback<ShopData>() {
						
						@Override
						public void onSuccess(ShopData result) {
							bottomInfo.setVisible(false);
							topSave.setText("Save");
							if(shopData.getId()==null){
								History.newItem("shop/get&id="+result.getId());
							}else{
								shopData=result;
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							showInfo("Save Fail: "+caught, BoxType.WARNINGBOX);							
						}
					});
					
					
				}else{
					showInfo("Save Fail: Invalide Data", BoxType.WARNINGBOX);
				}
				
				
				//TODO test
				
			}
		});
		bottomInfoHoPa.add(topAbort);
		bottomInfoHoPa.add(topSave);	
		bottomInfoHoPa.setCellWidth(topAbort, "100%");
		bottomInfoHoPa.setCellHorizontalAlignment(topAbort, HasHorizontalAlignment.ALIGN_RIGHT);
		bottomInfoHoPa.setCellHorizontalAlignment(topSave, HasHorizontalAlignment.ALIGN_RIGHT);				
		bottomInfoHoPa.setCellVerticalAlignment(topAbort, HasVerticalAlignment.ALIGN_MIDDLE);
		bottomInfoHoPa.setCellVerticalAlignment(topSave, HasVerticalAlignment.ALIGN_MIDDLE);
		bottomInfo.showInfo(bottomInfoHoPa, BoxType.WARNINGBOX);
	}
	
	private void showSaveNotLogin(){
		HorizontalPanel bottomInfoHoPa = new HorizontalPanel();
		bottomInfoHoPa.setWidth("100%");
		//bottomInfoHoPa.setHeight("100%");
		Button topAbort=new Button("Abort", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(shopData.getId()==null){
					History.newItem("home/");
				}else{
					TaPManager.getInstance().showProductPage(shopData.getId());
				}
				
				
			}
		});
		Label pleaseLoginLabel = new Label("Please login for saving!");
		bottomInfoHoPa.add(pleaseLoginLabel);
		bottomInfoHoPa.add(topAbort);
		bottomInfoHoPa.setCellWidth(pleaseLoginLabel, "100%");
		bottomInfoHoPa.setCellHorizontalAlignment(topAbort, HasHorizontalAlignment.ALIGN_RIGHT);
		bottomInfoHoPa.setCellVerticalAlignment(topAbort, HasVerticalAlignment.ALIGN_MIDDLE);
		bottomInfo.showInfo(bottomInfoHoPa, BoxType.WARNINGBOX);
	}
	
	private SearchResult<PropertyData> hashToPropertyList(HashMap<String, ArrayList<PropertyData>> hashProperties){
		SearchResult<PropertyData> newList = new SearchResult<PropertyData>();
		
		for(String ks:hashProperties.keySet()){
			for(PropertyData pd:hashProperties.get(ks)){
				newList.add(pd);
				//System.out.println(pd.getName()+": "+pd.getValue());
			}
		}
		return newList;
	}



	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}
}