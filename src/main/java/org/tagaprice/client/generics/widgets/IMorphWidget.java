package org.tagaprice.client.generics.widgets;

import com.google.gwt.user.client.ui.IsWidget;

public interface IMorphWidget extends IsWidget {

	enum Type {STRING, INTEGER, DOUBLE};
	
	/**
	 * Set the value of the MorphWidget. Must not be called before configMorph
	 * @param value a valid argument
	 */
	public void setValue(String value);
	
	
	public String getValue();
	
	//TODO implement config
	/**
	 * 
	 * Config the widget
	 */
	public void config(Type type, boolean notNull, String exampleText, boolean alignRight, boolean isHeadline);
	
	/**
	 * Set morphwidget readonly
	 * @param read
	 */
	public void setReadOnly(boolean read);
	
	public boolean isReadOnly();
}
