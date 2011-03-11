package org.tagaprice.client.gwt.server.mock;

import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.core.api.UserAlreadyLoggedInException;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.api.WrongEmailOrPasswordException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements ILoginService {

	String sessionId=null; // is working for just one user ;-)


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String setLogin(String email, String password) throws WrongEmailOrPasswordException, UserAlreadyLoggedInException {

		if(email.isEmpty() || password.isEmpty())
			throw new WrongEmailOrPasswordException("Please controll user and password");

		if(email.equals("test")) throw new WrongEmailOrPasswordException("Please controll user and password");

		if(sessionId!=null) throw new UserAlreadyLoggedInException("User already logged in");
		//impl UserAlreadyLoggedInException

		sessionId = ""+Math.random();
		// TODO Auto-generated method stub
		return sessionId;
	}

	@Override
	public void setLogout() throws UserNotLoggedInException {

		if(sessionId==null) throw new UserNotLoggedInException("User is not logged in.");

		sessionId=null;
	}

	@Override
	public String isLoggedIn() {

		return sessionId;
	}

	@Override
	public Boolean isEmailAvailable(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean registerUser(String email, String reCaptchaOK) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setNewPassword(String oldPassword, String newPassword, String newPassword2)
	throws UserNotLoggedInException {
		// TODO Auto-generated method stub
		return null;
	}

}
