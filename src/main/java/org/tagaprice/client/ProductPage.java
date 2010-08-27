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
import java.util.HashMap;
import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.client.propertyhandler.DefaultPropertyHandler;
import org.tagaprice.client.propertyhandler.IPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.PropertyChangeHandler;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductPage extends InfoBoxComposite {

	private ProductData productData;
	private HashMap<String, ArrayList<PropertyData>> hashProperties = new HashMap<String, ArrayList<PropertyData>>();
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	private PropertyChangeHandler handler;
	private ArrayList<IPropertyHandler> handlerList = new ArrayList<IPropertyHandler>();
	private InfoBox bottomInfo = new InfoBox();
	private PriceMapWidget priceMap;
	private SimplePanel typeWidgetContainer = new SimplePanel();
	private SimplePanel propertyHandlerContainer = new SimplePanel();
	private MorphWidget titleMorph = new MorphWidget("", Datatype.STRING, true);
	
	public ProductPage(ProductData _productData, Type _type) {
		init(vePa1);
		this.productData=_productData;
		
		this.type=_type;
		
		
		
		//Move PropertyData to hashPropertyData
		for(PropertyData pd:this.productData.getProperties()){
			if(hashProperties.get(pd.getName())==null){
				hashProperties.put(pd.getName(), new ArrayList<PropertyData>());
			}			
			hashProperties.get(pd.getName()).add(pd);
		}
		
		//style
		vePa1.setWidth("100%");
		
		//Header
		HorizontalPanel hoPa1 = new HorizontalPanel();
		hoPa1.setWidth("100%");
		vePa1.add(titleMorph);		
		titleMorph.setText(productData.getTitle());
		titleMorph.setWidth("100%");
		vePa1.add(hoPa1);
		ProgressWidget progressWidget = new ProgressWidget(new Image(MyResources.INSTANCE.productPriview()), productData.getProgress());
		hoPa1.add(progressWidget);
		
		VerticalPanel vePa2 = new VerticalPanel();
		vePa2.setWidth("100%");
		hoPa1.add(vePa2);
		hoPa1.setCellWidth(vePa2, "100%");
		
		//Type
		vePa2.add(typeWidgetContainer);	
		drawTypeWidget();

		
		//Rating
		vePa2.add(new RatingWidget(this.productData.getRating(), false));
		
		
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
		
		
		titleMorph.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			
			@Override
			public void onSuccess(Datatype errorType) {
				
				if(!productData.getTitle().equals(titleMorph.getText())){
					productData.setTitle(titleMorph.getText());
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
		if(productData.getId()!=null){
			final SimplePanel priceMapContaier = new SimplePanel();
			priceMapContaier.setWidth("100%");
			vePa1.add(priceMapContaier);
			GWT.runAsync(new RunAsyncCallback() {
				
				@Override
				public void onSuccess() {
					priceMap = new PriceMapWidget(productData.getId(),PriceMapType.PRODUCT);
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
				HandlerManager.getTypeHandler().get(newType.getLocaleId(), new AsyncCallback<Type>() {
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
				if(productData.getId()==null){
					History.newItem("home/");
				}else{
					TaPManager.getInstance().showProductPage(productData.getId());
				}
				
				
			}
		});
		final Button topSave=new Button("Save");	
		topSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				topSave.setText("Saving...");
				
				
				productData.setProperties(hashToPropertyList(hashProperties));
				productData.setTypeId(new Long(type.getLocaleId()));
				
				
				//Validate Data
				if(PropertyValidator.isValid(type, productData.getProperties())){				
					HandlerManager.getProductHandler().save(
							productData, 
							new AsyncCallback<ProductData>() {
						
						@Override
						public void onSuccess(ProductData result) {
							bottomInfo.setVisible(false);
							topSave.setText("Save");
							if(productData.getId()==null){
								History.newItem("product/get&id="+result.getId());
							}
							
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							showInfo("SaveFail: "+caught, BoxType.WARNINGBOX);
							
						}
					});	
				}else{
					showInfo("SaveFail: Invalide Data", BoxType.WARNINGBOX);
				}
				
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
