package org.tagaprice.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

public class DisplayLoginEvent extends GwtEvent<DisplayLoginEventHandler> {

	public static Type<DisplayLoginEventHandler> TYPE = new Type<DisplayLoginEventHandler>();
	
	public enum LoginType {login, invite};
	private final LoginType _loginType;
	
	
	public DisplayLoginEvent(LoginType loginType) {
		_loginType=loginType;
	}
	

	
	public LoginType getLoginType(){
		return _loginType;
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
