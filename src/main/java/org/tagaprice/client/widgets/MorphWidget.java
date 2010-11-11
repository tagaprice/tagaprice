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
package org.tagaprice.client.widgets;
import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Creates an editable label. 
 * 
 */
public class MorphWidget extends SimplePanel {

	TextBox textBox = new TextBox();
	Datatype type;
	MorphWidgetInfoHandler handler;

	/**
	 * 
	 * @param _text 
	 * @param _isEditable Sets text editable.
	 */
	public MorphWidget(String text, Datatype _type, boolean isEditable) {
		setWidget(textBox);
		type=_type;
		this.setStyleName("MorphWidget");
		setWidth("140px");
		
		setText(text);
		
		textBox.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				textBox.setStyleName("MorphWidgetEmpty");
			}
		});
		
		textBox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				setAutoStyle();
				
				if(!textBox.getText().isEmpty()){	
					//TypeControll
					if(type.equals(Datatype.INT)){
						try{
							Integer.parseInt(textBox.getText());
							callSuccess(Datatype.INT);
						} 
						catch(NumberFormatException nfe) {	
							callError(Datatype.INT);
						}
					}else if(type.equals(Datatype.DOUBLE)){
						try{
							Double.parseDouble(textBox.getText());
							callSuccess(Datatype.DOUBLE);
						} 
						catch(NumberFormatException nfe) {		
							callError(Datatype.DOUBLE);
						}
					}else if(type.equals(Datatype.STRING)){
						//Do nothing
						callSuccess(Datatype.STRING);
					}
				}
					
				
				
				
				
				//If Text is empty and wasn't empty before.
				if(textBox.getText().isEmpty())
					callEmpty();


			}
		});		
		
		
	}
	
	private void setAutoStyle(){
		if(textBox.getText().isEmpty()){
			textBox.setStyleName("MorphWidgetEmpty");
		}else{
			textBox.setStyleName("MorphWidgetNotEmpty");
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
		if(!text.isEmpty()){
			textBox.setStyleName("MorphWidgetNotEmpty");
		}else{
			textBox.setStyleName("MorphWidgetEmpty");
		}
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
	public HandlerRegistration addChangeHandler(ChangeHandler handler){
		return textBox.addChangeHandler(handler);
	}
	
	
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler){
		return textBox.addKeyUpHandler(handler);
	}
		
	
	/**
	 * 
	 * @param eHandler
	 */
	public void addMorphWidgetErrorHandler(MorphWidgetInfoHandler eHandler){
		handler=eHandler;
	}
	
	
	private void callError(Datatype errorType){
		if(handler!=null)
			handler.onError(errorType);
	}
	
	private void callSuccess(Datatype errorType){
		if(handler!=null)
			handler.onSuccess(errorType);
	}
	
	private void callEmpty(){
		if(handler!=null)
			handler.onEmpty();
	}
	
	
	
}