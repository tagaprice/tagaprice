package org.tagaprice.shared.rpc.accountmanagement;

import org.tagaprice.shared.exceptions.UserAlreadyLoggedInException;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;

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

	public String setLogin(String email, String password) throws WrongEmailOrPasswordException,
	UserAlreadyLoggedInException;


	/**
	 * clear sessionId on server
	 */
	public void setLogout() throws UserNotLoggedInException;


	/**
	 * Ask server if user is loggedIn. If so the server will return a new sessionId.
	 * 
	 * @return Returns a new or old sessionId if user is logged in. If user is not logged in, return value is NULL.
	 */
	public String isLoggedIn();


	/**
	 * Returns true if email is not used yet.
	 * 
	 * @param email
	 *            to lookup.
	 * @return true if email is not used yet.
	 */
	public Boolean isEmailAvailable(String email);



	/**
	 * Register a user with an email and controls if it is a human with a reCaptcha. The user will receive an email with
	 * an auto generated password.
	 * @param email that should be registered
	 * @param password password
	 * @param agreeTerms if user agrees our terms and conditions
	 * @return return a SessionKey if the registration was OK. If not NULL
	 */
	public String registerUser(String email, String password, boolean agreeTerms);

	/**
	 * Set new password.
	 * 
	 * @param oldPassword
	 *            old password
	 * @param newPassword
	 *            new password
	 * @param newPassword2
	 *            second time the new password
	 * @return True if password has being changed.
	 */
	public Boolean setNewPassword(String oldPassword, String newPassword, String newPassword2)
	throws UserNotLoggedInException;
}
