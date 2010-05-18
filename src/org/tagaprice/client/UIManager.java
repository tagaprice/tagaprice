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
 * Filename: UIManager.java
 * Date: 18.05.2010
*/
package org.tagaprice.client;

import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class UIManager extends Composite implements UIManagerImpl {
	
	DockPanel myDock = new DockPanel();
	Label newReceipt = new Label("Receipt(0)");
	Label newDraft = new Label("Drafts(0)");
	HorizontalPanel menu = new HorizontalPanel();
	TaPManagerImpl myMng = TaPManager.getInstance();
	TitlePanel myTitlePan = new TitlePanel("Home", new Label("HomeSeite"));

	public UIManager() {
		initWidget(myDock);
		init();
	}
	
	private void init(){
		myDock.add(menu, DockPanel.NORTH);
		myDock.setWidth("400px");
		
		menu.add(newReceipt);
		menu.add(new Label("___|___"));
		menu.add(newDraft);
		
		//Center
		myDock.add(myTitlePan, DockPanel.CENTER);
		
		newReceipt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ReceiptWidget tempReceipt = new ReceiptWidget(myMng.getReceipt(0, true),true); 
				
				myTitlePan.setTitleWidet(
						"Kassazettel eintragen", 
						tempReceipt);
				
				
				
				myMng.saveReceipt(tempReceipt.getReceiptContainer(), true);
				
			}
		});
	}
	
	/**
	 * Only a test method. 
	 */
	public void refreshDraft(){
		newDraft.setText("Draft(1)");
	}
	
	/**
	 * Only a test method. 
	 */
	public void refreshSave(){
		newDraft.setText("Draft(0)");
		newReceipt.setText("Receipt(1)");
	}
	
}
