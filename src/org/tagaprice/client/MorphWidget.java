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
 * Filename: MorphWidget.java
 * Date: 14.05.2010
*/
package org.tagaprice.client;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Creates an editable label. 
 * 
 */
public class MorphWidget extends SimplePanel {

	Label label = new Label();
	TextBox textBox = new TextBox();

	/**
	 * 
	 * @param _text 
	 * @param _isEditable Sets text editable.
	 */
	public MorphWidget(String _text, boolean _isEditable) {
		setWidget(label);
		label.setText(_text);
		textBox.setText(_text);
		this.setStyleName("MorphWidget");
		
		textBox.setWidth("100%");
		label.setWidth("100%");
		
		
		
		if(_isEditable){
			//css
			label.setStyleName("MorphWidgetText");
			textBox.setStyleName("MorphWidgetBox");
			
			label.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(textBox==null){
						textBox=new TextBox();
					}
					
					textBox.setText(label.getText());
					setWidget(textBox);
					textBox.setFocus(true);
				}
			});
			
			textBox.addBlurHandler(new BlurHandler() {
				
				@Override
				public void onBlur(BlurEvent event) {
					if(textBox==null){
						textBox=new TextBox();
					}
					label.setText(textBox.getText());
					setWidget(label);
	
				}
			});
		}
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getText(){
		return textBox.getText();
	}
	
	/**
	 * 
	 * @param text
	 */
	public void setText(String text){
		textBox.setText(text);
		label.setText(text);
	}
	
	
	/**
	 * 
	 */
	public void setWidth(String width){
		textBox.setWidth(width);
		//label.setWidth(width);
	}
	
	/**
	 * 
	 * @param handler
	 */
	public void addChangeHandler(ChangeHandler handler){
		textBox.addChangeHandler(handler);
	}
}