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

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.ImageBundle;
import org.tagaprice.client.TaPManager;
import org.tagaprice.client.propertyhandler.DefaultPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.IPropertyChangeHandler;
import org.tagaprice.client.widgets.InfoBoxWidget;
import org.tagaprice.client.widgets.MorphWidget;
import org.tagaprice.client.widgets.IMorphWidgetInfoHandler;
import org.tagaprice.client.widgets.PriceMapWidget;
import org.tagaprice.client.widgets.ProgressWidget;
import org.tagaprice.client.widgets.RatingWidget;
import org.tagaprice.client.widgets.TypeWidget;
import org.tagaprice.client.widgets.ITypeWidgetHandler;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.PriceMapWidget.PriceMapType;
import org.tagaprice.shared.SerializableArrayList;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Category;
import org.tagaprice.shared.entities.Country;
import org.tagaprice.shared.entities.Property;
import org.tagaprice.shared.entities.PropertyGroup;
import org.tagaprice.shared.entities.Shop;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;
import org.tagaprice.shared.utility.PropertyValidator;

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
import org.tagaprice.client.propertyhandler.*;


public class ShopPage extends APage {

	private Shop _shopData;
	private Category _type;
	private VerticalPanel _verticalPanel_1 = new VerticalPanel();
	private HashMap<String, ArrayList<Property>> _hashProperties = new HashMap<String, ArrayList<Property>>();
	private ArrayList<IPropertyHandler> _handlerList = new ArrayList<IPropertyHandler>();
	private IPropertyChangeHandler _handler;
	private InfoBoxWidget _bottomInfo = new InfoBoxWidget(false);
	private SimplePanel _typeWidgetContainer = new SimplePanel();
	private SimplePanel _propertyHandlerContainer = new SimplePanel();
	private PriceMapWidget _priceMap;
	private MorphWidget _titleMorph = new MorphWidget("", Datatype.STRING, true);
	private MapWidget _mapWidget = new MapWidget();
	private Marker _marker;
	private Geocoder _geoCoder = new Geocoder();

	
	//Address
	private MorphWidget _street = new MorphWidget("", Datatype.STRING, true);
	private MorphWidget _zip = new MorphWidget("", Datatype.STRING, true);
	private MorphWidget _county = new MorphWidget("", Datatype.STRING, true);
	private MorphWidget _country = new MorphWidget("", Datatype.STRING, true);
	private Button _showMapButton = new Button("Show on Map");
	
/**
 * The Constructor creates a shop page for a new shop with shop data and shop type	
 * @param shopData
 * @param _type
 */
	public ShopPage(Shop shopData, Category _type){
		init(_verticalPanel_1);
		this._shopData=shopData;
		this._type=_type;
		
		
		//Move PropertyData to hashPropertyData
		for(Property pd:this._shopData.getProperties()){
			if(_hashProperties.get(pd.getName())==null){
				_hashProperties.put(pd.getName(), new ArrayList<Property>());
			}			
			_hashProperties.get(pd.getName()).add(pd);
		}
			
		
		//Listener
		_handler=new IPropertyChangeHandler() {
			
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
		_verticalPanel_1.setWidth("100%");
		
		//Header
		HorizontalPanel hoPa1 = new HorizontalPanel();
		hoPa1.setWidth("100%");
		_verticalPanel_1.add(_titleMorph);		
		_titleMorph.setText(_shopData.getTitle());
		_titleMorph.setWidth("100%");
		_verticalPanel_1.add(hoPa1);
		ProgressWidget progressWidget = new ProgressWidget(new Image(ImageBundle.INSTANCE.productPriview()), _shopData.getProgress());
		hoPa1.add(progressWidget);
		
		VerticalPanel vePa2 = new VerticalPanel();
		vePa2.setWidth("100%");
		hoPa1.add(vePa2);
		hoPa1.setCellWidth(vePa2, "100%");
		
		//Type
		vePa2.add(_typeWidgetContainer);	
		drawTypeWidget();
		
		//Rating
		vePa2.add(new RatingWidget(_shopData.getRating(), false));
		
		
		//Add Adress and map
		_mapWidget.setZoomLevel(14);
		_mapWidget.setWidth("100%");
		_mapWidget.setHeight("200px");
		MarkerOptions makerOption = MarkerOptions.newInstance();
		makerOption.setDraggable(true);
		if(_shopData.getAddress().getLat()!=null){
			_mapWidget.setCenter(LatLng.newInstance(_shopData.getAddress().getLat(),_shopData.getAddress().getLng()));
			_marker = new Marker(LatLng.newInstance(_shopData.getAddress().getLat(),_shopData.getAddress().getLng()), makerOption);
		}else{
			_marker = new Marker(_mapWidget.getCenter(), makerOption);
		}
		_mapWidget.addOverlay(_marker);
		_verticalPanel_1.add(_mapWidget);
		
		Grid adressGrid = new Grid(4, 2);
		adressGrid.setWidth("100%");
		adressGrid.getCellFormatter().setWidth(0, 0, "100%");
		
		//Name Grid
		adressGrid.setText(0, 0, "Street");
		adressGrid.setText(1, 0, "ZIP");
		adressGrid.setText(2, 0, "County");
		adressGrid.setText(3, 0, "Country");	
		
		
		adressGrid.setWidget(0, 1, _street);
		adressGrid.setWidget(1, 1, _zip);
		adressGrid.setWidget(2, 1, _county);
		adressGrid.setWidget(3, 1, _country);
		
		
		_street.setText(_shopData.getAddress().getStreet());
		//zip.setText(shopData.getAddress().get);
		_county.setText(_shopData.getAddress().getCity());
		_country.setText(_shopData.getAddress().getCountry().getCode());
		
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
		_verticalPanel_1.add(adressGrid);
		
		_showMapButton.setWidth("100%");
		_verticalPanel_1.add(_showMapButton);
		_showMapButton.setVisible(false);
		
		_showMapButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				_showMapButton.setText("Searching....");

				_geoCoder.getLocations(_street.getValue().trim()+", "
						+_zip.getValue().trim()+", "
						+_county.getValue().trim()+", "
						+_country.getValue().trim(), new LocationCallback() {

							
							@Override
							public void onSuccess(JsArray<Placemark> locations) {
								_mapWidget.setCenter(locations.get(0).getPoint());
								_marker.setLatLng(locations.get(0).getPoint());
								_showMapButton.setText("Show on map");
								_showMapButton.setVisible(false);
								
								_shopData.getAddress().setCoordinates(
										locations.get(0).getPoint().getLatitude(),
										locations.get(0).getPoint().getLongitude());
								
								_street.setText(locations.get(0).getStreet());
								_zip.setText(locations.get(0).getPostalCode());
								_county.setText(locations.get(0).getCounty());
								_country.setText(locations.get(0).getCountry());
								
								
								//TODO Problem with Country

								_shopData.getAddress().setAddress(
										_street.getValue().trim(), 

										locations.get(0).getCity(), 
										new Country(
												locations.get(0).getCountry().toLowerCase(), 
												locations.get(0).getCountry(), 
												locations.get(0).getCountry()));	
								
								
							}
							
							@Override
							public void onFailure(int statusCode) {
								_showMapButton.setText("Show on map");
								System.out.println("can't find address");						
							}
						});
				
				
			}
		});
		
		
		//Listener
		//Map Lister
		_marker.addMarkerDragEndHandler(new MarkerDragEndHandler() {
			
			@Override
			public void onDragEnd(MarkerDragEndEvent event) {
				_shopData.getAddress().setCoordinates(event.getSender().getLatLng().getLatitude(), event.getSender().getLatLng().getLongitude());
				
				_geoCoder.getLocations(event.getSender().getLatLng(), new LocationCallback() {
					
					@Override
					public void onSuccess(JsArray<Placemark> locations) {
						
						_shopData.getAddress().setAddress(
								locations.get(0).getStreet(), 
								locations.get(0).getCity(), 
								new Country(
										locations.get(0).getCountry().toLowerCase(), 
										locations.get(0).getCountry(), 
										locations.get(0).getCountry()));
						
						_street.setText(locations.get(0).getStreet());
						_zip.setText(locations.get(0).getPostalCode());
						_county.setText(locations.get(0).getCounty());
						_country.setText(locations.get(0).getCountry());
						
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

		_street.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {
			public void onSuccess(Datatype errorType) {	_showMapButton.setVisible(true);}			

			public void onError(Datatype errorType) {}			
			public void onEmpty() {_showMapButton.setVisible(true);}
		});
		
	_zip.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {
			public void onSuccess(Datatype errorType) {	_showMapButton.setVisible(true);}			

			public void onError(Datatype errorType) {}			
			public void onEmpty() {_showMapButton.setVisible(true);}
		});
		

		_county.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {
			public void onSuccess(Datatype errorType) {	_showMapButton.setVisible(true);}			

			public void onError(Datatype errorType) {}			
			public void onEmpty() {_showMapButton.setVisible(true);}
		});
		
		_country.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {
			public void onSuccess(Datatype errorType) {	_showMapButton.setVisible(true);}			


			public void onError(Datatype errorType) {}			
			public void onEmpty() {_showMapButton.setVisible(true);}
		});
		
		
		
		//title Lister


		_titleMorph.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {
			
			@Override
			public void onSuccess(Datatype errorType) {
	
				if(!_shopData.getTitle().equals(_titleMorph.getValue())){
					_shopData.setTitle(_titleMorph.getValue());

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
		if(_shopData.getId()!=null){
			final SimplePanel priceMapContaier = new SimplePanel();
			priceMapContaier.setWidth("100%");
			_verticalPanel_1.add(priceMapContaier);
			
			GWT.runAsync(new RunAsyncCallback() {
				
				@Override
				public void onSuccess() {
					_priceMap = new PriceMapWidget(_shopData.getId(),PriceMapType.SHOP);
					priceMapContaier.setWidget(_priceMap);					
				}
				
				@Override
				public void onFailure(Throwable reason) {
					showInfo("Download Error at PriceWidget", BoxType.WARNINGBOX);
					
				}
			});
			
			
		}
		
		
		//Create and Register Handler
		_verticalPanel_1.add(_propertyHandlerContainer);
		_propertyHandlerContainer.setWidth("100%");
		registerHandler();
		
		
		
		
		_verticalPanel_1.add(_bottomInfo);
	}
	
	

	private void drawTypeWidget(){
		_typeWidgetContainer.setWidget(new TypeWidget(_type, new ITypeWidgetHandler() {			

			@Override
			public void onChange(Category newType) {
				
				
				
				
				//Get type and set type
				RPCHandlerManager.getTypeHandler().get(newType, new AsyncCallback<Category>() {
					@Override
					public void onFailure(Throwable caught) {
						showInfo("ProductPage getTypeError", BoxType.WARNINGBOX);
					}

					@Override
					public void onSuccess(Category result) {
						_type=result;
						drawTypeWidget();	
						registerHandler();
						showSave();	
					}

				});
				
				
				
			}
		}));
	}
	
	private void registerHandler(){
		_handlerList.clear();
		
		for(String ks:_hashProperties.keySet()){
			for(Property pd:_hashProperties.get(ks)){
				pd.setRead(false);
			}
		}
		
		
		VerticalPanel hVePa = new VerticalPanel();
		hVePa.setWidth("100%");
		
		
		//Add Properties
		for(PropertyGroup pg:this._type.getPropertyGroups()){
			
			if(pg.getType().equals(PropertyGroup.GroupType.NUTRITIONFACTS)){
				/*
				NutritionFactsPropertyHandler temp = new NutritionFactsPropertyHandler(hashProperties, pg, handler);
				handlerList.add(temp);
				hVePa.add(temp);
				*/
			}else if (pg.getType().equals(PropertyGroup.GroupType.LIST)){				
				ListPropertyHandler temp= new ListPropertyHandler(_hashProperties, pg, _handler);
				_handlerList.add(temp);
				hVePa.add(temp);
				
			}	
			
		}
		
		
		DefaultPropertyHandler defH = new DefaultPropertyHandler(_hashProperties, _handler);
		_handlerList.add(defH);
		hVePa.add(defH);
		
		
		
		_propertyHandlerContainer.setWidget(hVePa);
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
				TaPManager.getInstance().showShopPage(_shopData.getId());
				
			}
		});
		final Button topSave=new Button("Save");	
		topSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				topSave.setText("Saving...");
				
				_shopData.setProperties(hashToPropertyList(_hashProperties));
				_shopData.setTypeId(new Long(_type.getId()));
				
				
				//Validate Data
				if(PropertyValidator.isValid(_type, _shopData.getProperties())){	
				
					RPCHandlerManager.getShopHandler().save(_shopData, new AsyncCallback<Shop>() {
						
						@Override
						public void onSuccess(Shop result) {
							_bottomInfo.setVisible(false);
							topSave.setText("Save");
							if(_shopData.getId()==null){
								History.newItem("shop/get&id="+result.getId());
							}else{
								_shopData=result;
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
		_bottomInfo.showInfo(bottomInfoHoPa, BoxType.WARNINGBOX);
	}
	
	private void showSaveNotLogin(){
		HorizontalPanel bottomInfoHoPa = new HorizontalPanel();
		bottomInfoHoPa.setWidth("100%");
		//bottomInfoHoPa.setHeight("100%");
		Button topAbort=new Button("Abort", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(_shopData.getId()==null){
					History.newItem("home/");
				}else{
					TaPManager.getInstance().showProductPage(_shopData.getId());
				}
				
				
			}
		});
		Label pleaseLoginLabel = new Label("Please login for saving!");
		bottomInfoHoPa.add(pleaseLoginLabel);
		bottomInfoHoPa.add(topAbort);
		bottomInfoHoPa.setCellWidth(pleaseLoginLabel, "100%");
		bottomInfoHoPa.setCellHorizontalAlignment(topAbort, HasHorizontalAlignment.ALIGN_RIGHT);
		bottomInfoHoPa.setCellVerticalAlignment(topAbort, HasVerticalAlignment.ALIGN_MIDDLE);
		_bottomInfo.showInfo(bottomInfoHoPa, BoxType.WARNINGBOX);
	}
	
	private SerializableArrayList<Property> hashToPropertyList(HashMap<String, ArrayList<Property>> hashProperties){
		SerializableArrayList<Property> newList = new SerializableArrayList<Property>();
		
		for(String ks:hashProperties.keySet()){
			for(Property pd:hashProperties.get(ks)){
				newList.add(pd);
				//System.out.println(pd.getName()+": "+pd.getValue());
			}
		}
		return newList;
	}

/**
 * Set the position
 */

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}
}