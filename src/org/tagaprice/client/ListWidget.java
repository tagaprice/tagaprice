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
 * Filename: ListWidget.java
 * Date: May 17, 2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ShopData;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListWidget<T extends EntityPreview> extends Composite{

	private VerticalPanel verticalPanel;
	
	public ListWidget(){
		verticalPanel=new VerticalPanel();
		initWidget(verticalPanel);
	}
	
	public ListWidget(ArrayList<Entity> entityData){
		this();
		populateList(entityData);
	}
	
	private void populateList(ArrayList<Entity> entityData){
		verticalPanel.clear();
		
		for(Entity e: entityData){
			verticalPanel.add(createPreview(e));
		}
		
	}
	
	private EntityPreview createPreview(Entity e){
		EntityPreview rc = null;
		
		if(e instanceof ShopData){
			rc= new ShopPreview((ShopData)e, false);
		}else if(e instanceof ProductData){
			rc=new ProductPreview((ProductData) e, false);
		}
		
		return rc;
	}
	
	
}

