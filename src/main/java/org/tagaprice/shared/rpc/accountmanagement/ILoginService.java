package org.tagaprice.shared.rpc.accountmanagement;

import org.tagaprice.shared.exceptions.UserAlreadyLoggedInException;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginservice")
public interface ILoginService extends RemoteService {

	/**
	 * Try to login using a unique mail address and a password
	 * @param email User's mail address
	 * @param password User's password
	 * @return Session-ID
	 * @throws WrongEmailOrPasswordException if the login failed
	 * @throws DaoException if something went wrong at the database backend
	 */
	public String setLogin(String email, String password) throws WrongEmailOrPasswordException,
	UserAlreadyLoggedInException, DaoException;


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
	public boolean isEmailAvailable(String email);



	/**
	 * Register a user with an email. The user will receive an email with
	 * an auto generated password.
	 * @param email that should be registered
	 * @param password password
	 * @param agreeTerms if user agrees our terms and conditions
	 * @return return true if everything was ok.
	 * @throws DaoException
	 */
	public boolean registerUser(String email, String password, boolean agreeTerms) throws DaoException;

	/**
	 * Set new password.
	 * 
	 * @param newPassword
	 *            new password
	 * @param confirmPassword
	 *            second time the new password
	 * @return True if password has being changed.
	 */
	public boolean setNewPassword(String newPassword, String confirmPassword)
	throws UserNotLoggedInException, DaoException;
	
	/**
	 * Returns true/false if this email has been confirmed. But false if not, or not available. 
	 * @param email email to check
	 * @return true/false if this email has been confirmed. But false if not, or not available. 
	 */
	public boolean isEmailConfirmed(String email);
}
