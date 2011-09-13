package org.tagaprice.client.generics.widgets;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

public interface IPropertyDefaultSelecter extends IsWidget {

	public void setPropertyList(Map<String, Object> propertyList);
}
