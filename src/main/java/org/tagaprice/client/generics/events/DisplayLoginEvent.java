package org.tagaprice.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

public class DisplayLoginEvent extends GwtEvent<DisplayLoginEventHandler> {

	public static Type<DisplayLoginEventHandler> TYPE = new Type<DisplayLoginEventHandler>();
	
	private final boolean _show;
	
	public DisplayLoginEvent(boolean show) {
		_show=show;
	}
	
	
	/**
	 * @return the show
	 */
	public boolean isShow() {
		return _show;
	}




	@Override
	protected void dispatch(DisplayLoginEventHandler handler) {
		handler.onDisplayLogin(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DisplayLoginEventHandler> getAssociatedType() {
		return DisplayLoginEvent.TYPE;
	}

}
