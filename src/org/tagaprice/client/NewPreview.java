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
 * Filename: NewPreview.java
 * Date: May 27, 2010
*/
package org.tagaprice.client;

import org.tagaprice.shared.Entity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;

public class NewPreview extends EntityPreview{

	Label label;
	
	public NewPreview(String newPreviewType){
	
		label = new Label(newPreviewType);
		label.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO create new Product/Shop
			}	
		});

		initWidget(label);
	}
	
	
	
	
}
