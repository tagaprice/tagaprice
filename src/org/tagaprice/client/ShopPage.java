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

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.propertyhandler.IPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.NutritionFactsPropertyHandler;
import org.tagaprice.client.propertyhandler.PropertyChangeHandler;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShopPage extends Composite {

	private ShopData shopData;
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	private ArrayList<IPropertyHandler> handlerList = new ArrayList<IPropertyHandler>();
	private PropertyChangeHandler handler;
	private InfoBox bottomInfo = new InfoBox();
	
	public ShopPage(ShopData _shopData, Type _type){
		initWidget(vePa1);
		this.shopData=_shopData;
		this.type=_type;
		
		
		//style
		vePa1.setWidth("100%");
		vePa1.add(new ShopPreview(this.shopData, true));
		
		//Listener
		handler=new PropertyChangeHandler() {
			
			@Override
			public void onSuccess() {
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
						
						//TODO Saving						
						
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
				
				//
				//TaPManagerImpl.getInstance().getInfoBox().showInfo(new Button("Save Changes!"), BoxType.WARNINGBOX);
				
				
				
				

				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		};
		
		//Add Properties
		for(PropertyGroup pg:this.type.getPropertyGroups()){
			registerHandler(pg);
		}
		
		vePa1.add(bottomInfo);
	}
	
	private void registerHandler(PropertyGroup propGroup){
		
		if(propGroup.getType().equals(PropertyGroup.GroupType.NUTRITIONFACTS)){
			//TODO
			/*
			NutritionFactsPropertyHandler temp = new NutritionFactsPropertyHandler(this.shopData.getProperties(), propGroup, handler);
			handlerList.add(temp);
			vePa1.add(temp);
			*/
		}else if (propGroup.getType().equals(PropertyGroup.GroupType.LIST)){
			//TODO
			//ListPropertyHandler temp= new ListPropertyHandler(this.shopData.getProperties(), propGroup, handler);
			//handlerList.add(temp);
			//vePa1.add(temp);
		}			
	}
}