package org.tagaprice.client.generics.widgets;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is an PropertyDefaultSelecter wrapper class. This class implements the right screen design.
 * Is is also possible to use this class in UIBuilder
 * 
 */
public class PropertyDefaultSelecter extends Composite implements IPropertyDefaultSelecter {

	private IPropertyDefaultSelecter properteyDefaultSelecter = GWT.create(IPropertyDefaultSelecter.class);
	
	public PropertyDefaultSelecter() {
		initWidget(properteyDefaultSelecter.asWidget());
	}
	
	@Override
	public void setPropertyList(Map<String, Object> propertyList) {
		properteyDefaultSelecter.setPropertyList(propertyList);		
	}

}
