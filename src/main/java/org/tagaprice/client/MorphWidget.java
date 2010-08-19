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
import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
	Datatype type;
	MorphWidgetErrorHandler handler;

	/**
	 * 
	 * @param _text 
	 * @param _isEditable Sets text editable.
	 */
	public MorphWidget(String text, Datatype _type, boolean isEditable) {
		setWidget(label);
		label.setText(text);
		textBox.setText(text);
		type=_type;
		this.setStyleName("MorphWidget");
		
		setWidth("80px");
		
		
		
		if(isEditable){
			//css
			if(!text.isEmpty()){
				label.setStyleName("MorphWidgetText");
			}else{
				label.setStyleName("MorphWidgetBox");
			}
			textBox.setStyleName("MorphWidgetBox");
			
			addDomHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(textBox==null){
						textBox=new TextBox();
					}
					
					textBox.setText(label.getText());
					setWidget(textBox);
					textBox.setFocus(true);
				}
			}, ClickEvent.getType());
			
			
			
			textBox.addBlurHandler(new BlurHandler() {
				
				@Override
				public void onBlur(BlurEvent event) {
					if(textBox==null){
						textBox=new TextBox();
					}
					
					if(!textBox.getText().isEmpty()){
						label.setStyleName("MorphWidgetText");
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
					}else{
						label.setStyleName("MorphWidgetBox");
					}
					
					
					//If Text is empty and wasn't empty before.
					if(textBox.getText().isEmpty() && !label.getText().isEmpty())
						callEmpty();
					
					
					
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
		if(!text.isEmpty()){
			label.setStyleName("MorphWidgetText");
		}else{
			label.setStyleName("MorphWidgetBox");
		}
	}
	
	
	/**
	 * 
	 */
	public void setWidth(String width){
		textBox.setWidth(width);
		label.setWidth(width);
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
	public void addMorphWidgetErrorHandler(MorphWidgetErrorHandler eHandler){
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