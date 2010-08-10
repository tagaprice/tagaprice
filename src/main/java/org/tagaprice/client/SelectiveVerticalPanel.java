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
 * Filename: SelectiveVerticalPanel.java
 * Date: 14.05.2010
*/
package org.tagaprice.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Vertical panel with edit buttons.
 *
 */
public class SelectiveVerticalPanel extends Composite {

	public enum SelectionType {PLUSBUTTON, MINUSBUTTON, NOBUTTON}
	
	VerticalPanel vePa1 = new VerticalPanel();
	ImageResource topImage;
	ImageResource bottomImage;
	SelectiveVerticalPanelHandler externalHandler;
	SelectionType _selectionType;
	
	/**
	 * 
	 * @param selectionType Is the ImageType (e.g. SelectiveVerticalPanel.PLUSBUTTON)
	 */
	public @UiConstructor SelectiveVerticalPanel(SelectionType selectionType) {
		initWidget(vePa1);
		vePa1.setWidth("100%");
		_selectionType=selectionType;
		
		if(_selectionType.equals(SelectionType.PLUSBUTTON)){
			topImage=(MyResources.INSTANCE.plusActive());
			bottomImage=(MyResources.INSTANCE.plusInactive());
		}else if(_selectionType.equals(SelectionType.MINUSBUTTON)){
			topImage=(MyResources.INSTANCE.minusActive());
			bottomImage=(MyResources.INSTANCE.minusInactive());
		}else if(_selectionType.equals(SelectionType.NOBUTTON)){
			//Do nothing here!
		}
		
	}
	
	/**
	 * Adds a new child widget to the panel.
	 * @param w
	 */
	public void add(final Widget w){
		final HorizontalPanel hoPa = new HorizontalPanel();
		hoPa.setWidth("100%");
		
		
		if(!_selectionType.equals(SelectionType.NOBUTTON)){
			PushButton puBa = new PushButton(new Image(topImage), new Image(bottomImage));
				
			//insert Button
			hoPa.add(puBa);
			hoPa.setCellWidth(puBa, MyResources.INSTANCE.minusActive().getWidth()+"px");
			hoPa.setCellVerticalAlignment(puBa, HasVerticalAlignment.ALIGN_MIDDLE);
			
			//handler
			puBa.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					if(externalHandler!=null)
						externalHandler.onClick(w,vePa1.getWidgetIndex(hoPa));
					
				}
			});
		}
		
		
		hoPa.add(w);
		vePa1.add(hoPa);
		
		
		
	}
	
	/**
	 * 
	 * @param handler
	 */
	public void addSelectiveVerticalPanelHandler(SelectiveVerticalPanelHandler handler){
		externalHandler=handler;
	}
	
	
	/**
	 * 
	 * @param index
	 */
	public void removeWidget(int index){
		vePa1.remove(index);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getWidgetCount(){
		return vePa1.getWidgetCount();
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Widget getWidget(int index){
		return ((HorizontalPanel)vePa1.getWidget(index)).getWidget(1);
	}
	
	/**
	 * 
	 */
	public void clear(){
		vePa1.clear();
	}
}
