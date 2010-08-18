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
package org.tagaprice.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.client.propertyhandler.DefaultPropertyHandler;
import org.tagaprice.client.propertyhandler.IPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.PropertyChangeHandler;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShopPage extends InfoBoxComposite {

	private ShopData shopData;
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	private HashMap<String, ArrayList<PropertyData>> hashProperties = new HashMap<String, ArrayList<PropertyData>>();
	private ArrayList<IPropertyHandler> handlerList = new ArrayList<IPropertyHandler>();
	private PropertyChangeHandler handler;
	private InfoBox bottomInfo = new InfoBox();
	private SimplePanel typeWidgetContainer = new SimplePanel();
	private SimplePanel propertyHandlerContainer = new SimplePanel();
	private TaPManager TaPMng = TaPManagerImpl.getInstance();
	private PriceMapWidget priceMap;
	
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
		//Type
		vePa1.add(typeWidgetContainer);	
		drawTypeWidget();
		
		vePa1.setWidth("100%");
		vePa1.add(new ShopPreview(this.shopData, true));
		
		//Add Price
		priceMap = new PriceMapWidget(shopData.getId(),PriceMapType.SHOP);
		vePa1.add(priceMap);
		
		
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
				TaPMng.getType(newType.getLocaleId(), new AsyncCallback<Type>() {
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
		HorizontalPanel bottomInfoHoPa = new HorizontalPanel();
		bottomInfoHoPa.setWidth("100%");
		bottomInfoHoPa.setHeight("100%");
		Button topAbort=new Button("Abort", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				TaPManagerImpl.getInstance().showShopPage(shopData.getId());
				
			}
		});
		final Button topSave=new Button("Save");	
		topSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				topSave.setText("Saving...");
				
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
}