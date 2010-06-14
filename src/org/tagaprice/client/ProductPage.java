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
 * Filename: ProductWidget.java
 * Date: 19.05.2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.client.propertyhandler.IPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.NutritionFactsPropertyHandler;
import org.tagaprice.client.propertyhandler.PropertyChangeHandler;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.SearchResult;
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

public class ProductPage extends Composite {

	private ProductData productData;
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	private PropertyChangeHandler handler;
	private ArrayList<IPropertyHandler> handlerList = new ArrayList<IPropertyHandler>();
	private InfoBox bottomInfo = new InfoBox();
	private TaPManager TaPMng = TaPManagerImpl.getInstance();
	private PriceMapWidget priceMap;
	
	public ProductPage(ProductData _productData, Type _type) {
		initWidget(vePa1);
		this.productData=_productData;
		
		this.type=_type;
		
		
		
		//style
		vePa1.setWidth("100%");
		vePa1.add(new ProductPreview(this.productData, false));
		
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
						TaPManagerImpl.getInstance().showProductPage(productData.getId());
						
					}
				});
				final Button topSave=new Button("Save");	
				topSave.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						topSave.setText("Saving...");
						
						//productData.getProperties()
						SearchResult<PropertyData> newList = new SearchResult<PropertyData>();
						
						for(IPropertyHandler hl:handlerList){
							for(PropertyData pd:hl.getPropertyData()){
								newList.add(pd);
								//System.out.println(pd.getName()+": "+pd.getValue());
							}
						}
						productData.setProperties(newList);
						
						TaPManagerImpl.getInstance().saveProduct(productData, new AsyncCallback<ProductData>() {
							
							@Override
							public void onSuccess(ProductData result) {
								bottomInfo.setVisible(false);
								topSave.setText("Save");
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								TaPMng.getInfoBox().showInfo("SaveFail: "+caught, BoxType.WARNINGBOX);
								
							}
						});						
						
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
		
		
		//Add Price
		priceMap = new PriceMapWidget(productData.getId(),PriceMapType.SHOP);
		vePa1.add(priceMap);
		
		//Add Properties
		for(PropertyGroup pg:this.type.getPropertyGroups()){
			registerHandler(pg);
		}
		
		
		vePa1.add(bottomInfo);
	}
	
	
	private void registerHandler(PropertyGroup propGroup){
		
		if(propGroup.getType().equals(PropertyGroup.GroupType.NUTRITIONFACTS)){
			NutritionFactsPropertyHandler temp = new NutritionFactsPropertyHandler(this.productData.getProperties(), propGroup, handler);
			handlerList.add(temp);
			vePa1.add(temp);
		}else if (propGroup.getType().equals(PropertyGroup.GroupType.LIST)){
			ListPropertyHandler temp= new ListPropertyHandler(this.productData.getProperties(), propGroup, handler);
			handlerList.add(temp);
			vePa1.add(temp);
		}	
		
	}
	
	
}
