package org.tagaprice.client.generics.widgets;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods a StdFrame has.
 *
 */
public interface IStdFrame extends IsWidget {

	/**
	 * Set the header widget
	 * @param header
	 */
	public void setHeader(IsWidget header);
	
	
	/**
	 * Set the body widget
	 * @param body
	 */
	public void setBody(IsWidget body);
	
}
