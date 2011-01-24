package org.tagaprice.client.gwt.server.mock;

import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements ILoginService {

	@Override
	public String setLogin(String email, String password) {
		String sessionId = "0123456789";
		// TODO Auto-generated method stub
		return sessionId;
	}

	@Override
	public boolean setLogout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String isLoggedIn() {
		String sessionId = "9876543210";
		// TODO Auto-generated method stub
		return null;
	}

}
