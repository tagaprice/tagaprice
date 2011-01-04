package org.tagaprice.client.gwt.client;

import com.google.gwt.core.client.GWT;

/**
 * This class implements static classes and methods used from everywhere in the project.
 *
 */
public  class FactoryManager {

	private static final ClientFactory CLIENT_FACTORY  = GWT.create(ClientFactory.class);
	private static final IWidgetFactory WIDGET_FACTORY = GWT.create(IWidgetFactory.class);

	/**
	 * Return the {@link ClientFactory}
	 * @return the {@link ClientFactory}
	 */
	public static ClientFactory getClientFactory(){
		return FactoryManager.CLIENT_FACTORY;
	}


	/**
	 * Return the {@link IWidgetFactory}
	 * @return the {@link IWidgetFactory}
	 */
	public static IWidgetFactory getWidgetFactory(){
		return FactoryManager.WIDGET_FACTORY;
	}
}
