package org.tagaprice.client.generics.events;

import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Destroy an Info or all informations from a sender
 *
 */
public class InfoBoxDestroyEvent extends GwtEvent<InfoBoxDestroyEventHandler> {

	public static Type<InfoBoxDestroyEventHandler> TYPE = new Type<InfoBoxDestroyEventHandler>();

	private Class<?> _class=null;
	private INFOTYPE _type=null;
	private InfoBoxShowEvent _displayedInfo=null;


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
	 * Destroy only this info
	 * @param displayedInfo a reference to a showed info
	 */
	public InfoBoxDestroyEvent(InfoBoxShowEvent displayedInfo){
		_displayedInfo=displayedInfo;
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

	/**
	 * The reference to a info that should be destroyed.
	 * @return reference to a info that should be destroyed.
	 */
	public InfoBoxShowEvent getDesplayedEvent(){
		return _displayedInfo;
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
