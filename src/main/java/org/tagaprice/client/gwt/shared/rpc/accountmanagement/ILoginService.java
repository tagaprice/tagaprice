package org.tagaprice.client.gwt.shared.rpc.accountmanagement;

import org.tagaprice.core.api.WrongEmailOrPasswordException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginservice")
public interface ILoginService extends RemoteService {

	/**
	 * Set email and password to server and get a Unique SessionId
	 * 
	 * @param email
	 *            email (username)
	 * @param password
	 *            password (plane)
	 * @return Unique SessionId (Must be saved on in browser as session)
	 */

	public String setLogin(String email, String password) throws WrongEmailOrPasswordException;


	/**
	 * clear sessionId on server and return true is ok.
	 * 
	 * @return true if logout was successful
	 */
	public boolean setLogout();


	/**
	 * Ask server if user is loggedIn. If so the server will return a new sessionId.
	 * 
	 * @return Returns a new or old sessionId if user is logged in. If user is not logged in, return value is NULL.
	 */
	public String isLoggedIn();
}
