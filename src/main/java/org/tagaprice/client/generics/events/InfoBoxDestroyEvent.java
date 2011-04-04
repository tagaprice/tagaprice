package org.tagaprice.client.gwt.client.generics.events;

import org.tagaprice.client.gwt.client.generics.events.InfoBoxShowEvent.INFOTYPE;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Destroy an Info or all informations from a sender
 *
 */
public class InfoBoxDestroyEvent extends GwtEvent<InfoBoxDestroyEventHandler> {

	public static Type<InfoBoxDestroyEventHandler> TYPE = new Type<InfoBoxDestroyEventHandler>();

	private final Class<?> _class;
	private INFOTYPE _type=null;

	/**
	 * Destroy all events from a specific class.
	 * @param dclass sender class
	 */
	public InfoBoxDestroyEvent(Class<?> dclass) {
		this(dclass, null);
	}

	/**
	 * Destroy all specific infos.
	 * @param dclass sender class
	 * @param type Destroy this types
	 */
	public InfoBoxDestroyEvent(Class<?> dclass, INFOTYPE type){
		_class=dclass;
		_type=type;
	}


	/**
	 * @return the class
	 */
	public Class<?> getDestroyerClass() {
		return _class;
	}

	/**
	 * The type which should be destroyed.
	 * @return type which should be destroyed.
	 */
	public INFOTYPE getType(){
		return _type;
	}

	@Override
	protected void dispatch(InfoBoxDestroyEventHandler handler) {
		handler.onDestroyInfo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<InfoBoxDestroyEventHandler> getAssociatedType() {
		return InfoBoxDestroyEvent.TYPE;
	}

}
