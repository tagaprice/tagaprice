package org.tagaprice.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

public class DisplayLoginEvent extends GwtEvent<DisplayLoginEventHandler> {

	public static Type<DisplayLoginEventHandler> TYPE = new Type<DisplayLoginEventHandler>();
	
	public enum LoginType {login, invite, register};
	private final LoginType _loginType;
	private final String _inviteKey;
	
	
	public DisplayLoginEvent(LoginType loginType) {
		_loginType=loginType;
		_inviteKey=null;
	}
	
	public DisplayLoginEvent(LoginType loginType, String inviteKey) {
		_loginType=loginType;
		_inviteKey=inviteKey;
	}
	
	public LoginType getLoginType(){
		return _loginType;
	}

	public String getInviteKey(){
		return _inviteKey;
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
