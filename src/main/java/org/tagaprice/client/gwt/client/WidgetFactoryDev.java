package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.generics.widgets.ICategorySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.devView.CategorySelecter;

/**
 * This WidgetFactory Implements only the dev view of the widgets
 *
 */
public class WidgetFactoryDev implements IWidgetFactory {

	@Override
	public ICategorySelecter getNewInstanceOfICategorySelecter() {

		return new CategorySelecter();
	}

}
