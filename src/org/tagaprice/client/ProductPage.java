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

import org.tagaprice.client.propertyhandler.DefaultPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.NutritionFactsPropertyHandler;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;
import org.tagaprice.shared.Type;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductPage extends Composite {

	private ProductData productData;
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	private TaPManager myMng = TaPManagerImpl.getInstance();
	
	public ProductPage(ProductData _productData) {
		initWidget(vePa1);
		this.productData=_productData;
		myMng.getType(productData.getTypeId(), new AsyncCallback<Type>() {
			
			@Override
			public void onSuccess(Type result) {
				// TODO Auto-generated method stub
				type=result;
				vePa1.add(new ProductPreview(productData, false));
				
				
				for(PropertyGroup pg:type.getPropertyGroups()){
					registerHandler(pg);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		//style
		vePa1.setWidth("100%");
		
		
		//Is displaying the non uses properties.
		//vePa1.add(new DefaultPropertyHandler(this.productData.getProperties(), null));
		
	}
	
	
	private void registerHandler(PropertyGroup propGroup){
		
		if(propGroup.getType().equals(PropertyGroup.GroupType.NUTRITIONFACTS))
			vePa1.add(new NutritionFactsPropertyHandler(this.productData.getProperties(), propGroup));
		else if (propGroup.getType().equals(PropertyGroup.GroupType.LIST))
			vePa1.add(new ListPropertyHandler(this.productData.getProperties(), propGroup));

		

		
		
	}
	
	
}
