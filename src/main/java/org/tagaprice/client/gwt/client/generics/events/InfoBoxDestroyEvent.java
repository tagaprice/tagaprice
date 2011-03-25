package org.tagaprice.client.gwt.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Destroy an Info or all informations from a sender
 *
 */
public class InfoBoxDestroyEvent extends GwtEvent<InfoBoxEventHandler> {

	public static Type<InfoBoxEventHandler> TYPE = new Type<InfoBoxEventHandler>();

	private final Class<?> _class;

	/**
	 * Destroy all events from a specific class.
	 * @param dclass sender class
	 */
	public InfoBoxDestroyEvent(Class<?> dclass) {
		_class=dclass;
	}



	/**
	 * @return the class
	 */
	public Class<?> getDestroyerClass() {
		return _class;
	}



	@Override
	protected void dispatch(InfoBoxEventHandler handler) {
		handler.onDestroyInfo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<InfoBoxEventHandler> getAssociatedType() {
		return InfoBoxDestroyEvent.TYPE;
	}

}
