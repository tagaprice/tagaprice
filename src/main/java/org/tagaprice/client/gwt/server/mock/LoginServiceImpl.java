package org.tagaprice.client.gwt.server.mock;

import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.core.api.WrongEmailOrPasswordException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements ILoginService {

	String sessionId=null; // is working for just one user ;-)


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String setLogin(String email, String password) throws WrongEmailOrPasswordException {

		if(email.isEmpty() || password.isEmpty())
			throw new WrongEmailOrPasswordException("Please controll user and password");

		if(email.equals("test")) throw new WrongEmailOrPasswordException("Please controll user and password");

		sessionId = ""+Math.random();
		// TODO Auto-generated method stub
		return sessionId;
	}

	@Override
	public boolean setLogout() {


		if(sessionId!=null){
			sessionId=null;
			return true;
		}
		return false;
	}

	@Override
	public String isLoggedIn() {
		return sessionId;
	}

}
