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
import org.tagaprice.client.propertyhandler.IPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.NutritionFactsPropertyHandler;
import org.tagaprice.client.propertyhandler.PropertyChangeHandler;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.TaPManagerImpl;
import org.tagaprice.shared.Type;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductPage extends Composite {

	private ProductData productData;
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	private PropertyChangeHandler handler;
	private ArrayList<IPropertyHandler> handlerList = new ArrayList<IPropertyHandler>();

	
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
				TaPManagerImpl.getInstance().getInfoBox().showInfo(new Button("Save Changes!"), BoxType.WARNINGBOX);
				
				
				
				/*
				for(IPropertyHandler hl:handlerList){
					for(PropertyData pd:hl.getPropertyData()){
						System.out.println(pd.getName()+": "+pd.getValue());
					}
				}
				// */

				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		for(PropertyGroup pg:this.type.getPropertyGroups()){
			registerHandler(pg);
		}
		
		
		
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
