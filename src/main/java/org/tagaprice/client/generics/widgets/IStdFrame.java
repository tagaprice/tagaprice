package org.tagaprice.client.generics.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * This interface defines the primary methods a StdFrame has.
 *
 */
public interface IStdFrame extends IsWidget {

	/**
	 * Set the header widget
	 * @param header
	 */
	public void setHeader(Widget header);
	
	
	/**
	 * Set the body widget
	 * @param body
	 */
	public void setBody(Widget body);
	
	
	public void setBody(Widget body, String width);
	
	
	public void setButtonsVisible(boolean visible);
	
	public void addCancleClickHandler(ClickHandler handler);
	
	public void addSaveClickHandler(ClickHandler handler);
	
	public void addEditClickHandler(ClickHandler handler);
	
	public void setReadOnly(boolean readonly);
}
