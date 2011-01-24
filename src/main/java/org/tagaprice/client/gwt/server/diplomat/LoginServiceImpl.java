package org.tagaprice.client.gwt.server.diplomat;

import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.api.WrongEmailOrPasswordException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This is a copy from the Mock. Please implement the service methods here
 *
 */
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
	public void setLogout() throws UserNotLoggedInException {

		if(sessionId==null) throw new UserNotLoggedInException("User is not logged in.");

	}

	@Override
	public String isLoggedIn() {
		return sessionId;
	}

}
