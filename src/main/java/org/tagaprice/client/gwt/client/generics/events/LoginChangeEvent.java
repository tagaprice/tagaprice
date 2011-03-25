package org.tagaprice.client.gwt.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

public class LoginChangeEvent extends GwtEvent<LoginChangeEventHandler> {

	public static Type<LoginChangeEventHandler> TYPE = new Type<LoginChangeEventHandler>();

	private final boolean _isLoggedIn;

	public LoginChangeEvent(boolean isLoggedId) {
		_isLoggedIn = isLoggedId;
	}

	@Override
	protected void dispatch(LoginChangeEventHandler handler) {
		handler.onLoginChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginChangeEventHandler> getAssociatedType() {
		return LoginChangeEvent.TYPE;
	}

	/**
	 * @return the isLoggedIn
	 */
	public boolean isLoggedIn() {
		return _isLoggedIn;
	}



}
