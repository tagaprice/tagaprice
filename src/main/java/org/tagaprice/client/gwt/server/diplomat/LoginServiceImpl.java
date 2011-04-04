package org.tagaprice.client.gwt.server.diplomat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagaprice.client.gwt.shared.exceptions.UserAlreadyLoggedInException;
import org.tagaprice.client.gwt.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.client.gwt.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.boot.Boot;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This is a copy from the Mock. Please implement the service methods here
 *
 */
public class LoginServiceImpl extends RemoteServiceServlet implements ILoginService {

	String sessionId=null; // is working for just one user ;-)

	private org.tagaprice.core.api.ILoginService _coreLoginService;
	
	private static Logger _log = LoggerFactory.getLogger(LoginServiceImpl.class);

	public LoginServiceImpl() {
		_log.debug("Starting GWT-LoginService");

		try {
			String service = "defaultLoginService";
			_log.debug("Attempting to load "+service+" from core.api");
			_coreLoginService = (org.tagaprice.core.api.ILoginService) Boot.getApplicationContext().getBean(service);
			_log.debug("Loaded "+service+" successfully.");
		} catch (Exception e) {
			_log.debug(e.getClass() + ": " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String setLogin(String email, String password) throws WrongEmailOrPasswordException { //TODO throw useralreadyloggedinexception, handle general serverexception!
		_log.debug("email :"+email);
		
		try {
			Session sessionToken = _coreLoginService.login(email, password);
			_log.debug("received session token" +sessionToken.toString());
			getThreadLocalRequest().getSession().setAttribute("session", sessionToken);
			return new String(sessionToken.getSessionId()); //TODO do you need return value
		} catch (UserAlreadyLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setLogout() throws UserNotLoggedInException {
		_log.debug("logout for currently logged in user");
		_coreLoginService.logout((Session) getThreadLocalRequest().getSession().getAttribute("session"));
		getThreadLocalRequest().getSession().setAttribute("session", null);
		
		return;
	}

	@Override
	public String isLoggedIn() {
		return sessionId;
	}

}
