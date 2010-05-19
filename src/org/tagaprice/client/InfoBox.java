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
 * Filename: InfoBox.java
 * Date: 19.05.2010
*/
package org.tagaprice.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Shows a success, warning or error info.
 *
 */
public class InfoBox {
	PopupPanel infoBox = new PopupPanel(false);
	Label infoText = new Label();
	public static final String ERRORBOX = "ERRORBOX"; 
	public static final String SUCCESSBOX = "SUCCESSBOX";
	public static final String WARNINGBOX = "WARNINGBOX";

	public InfoBox() {
		infoBox.setPopupPosition(0, 0);
		infoBox.setSize(Window.getClientWidth()+"px", "50px");
		infoBox.setAnimationEnabled(true);
		infoBox.setWidget(infoText);
	}
	
	public void showInfo(String text, String type){
		if(type.equals(ERRORBOX)){
			infoBox.setStyleName("InfoBox-Error");
		}else if(type.equals(SUCCESSBOX)){
			infoBox.setStyleName("InfoBox-Success");
		}else{
			infoBox.setStyleName("InfoBox-Warning");
		}
		
		infoText.setText(text);
		infoBox.show();
		
		Timer t = new Timer() {
			public void run() {
				infoBox.hide();
			}
		};
		
		t.schedule(1500);

	}
}
