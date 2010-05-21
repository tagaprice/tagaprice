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

import org.tagaprice.shared.ServerResponse.StatusCode;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Shows a success, warning or error info.
 * 
 */
public class InfoBox extends Composite {
	SimplePanel infoBox = new SimplePanel();
	//PopupPanel infoBox = new PopupPanel(false);
	Label infoText = new Label();
	public enum BoxType {
		ERRORBOX, SUCCESSBOX, WARNINGBOX
	}
	protected BoxType type;


	public InfoBox() {
		initWidget(infoBox);
		infoBox.setSize("100%", "50px");
		infoBox.setVisible(false);		
		infoBox.setWidget(infoText);
	}
	
	public void showInfo(String text, BoxType type){
		this.type=type;
		
		
		if(this.type == BoxType.ERRORBOX){
			infoBox.setStyleName("InfoBox-Error");
		}else if(this.type == BoxType.SUCCESSBOX){
			infoBox.setStyleName("InfoBox-Success");
		}else if(this.type == BoxType.WARNINGBOX){
			infoBox.setStyleName("InfoBox-Warning");
		}else{
			infoBox.setStyleName("InfoBox-Warning");
		}
		
		infoText.setText(text);
		infoBox.setVisible(true);
		
		Timer t = new Timer() {
			public void run() {				
				infoBox.setVisible(false);
			}
		};
		
		t.schedule(1500);

	}
}