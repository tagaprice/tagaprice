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
 * Filename: PreviewWidget.java
 * Date: May 19, 2010
*/
package org.tagaprice.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;

public abstract class AEntityPreview extends Composite{
	
	public AEntityPreview(){

		addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				click();			
			}
		});
	}
	
	public abstract void click();
	
	
	public void addClickHandler(ClickHandler handler){
		addDomHandler(handler, ClickEvent.getType());
	}

	
	
}
