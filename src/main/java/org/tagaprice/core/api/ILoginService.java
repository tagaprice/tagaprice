package org.tagaprice.core.api;

import org.tagaprice.core.entities.Session;

public interface ILoginService {
	/**
	 * Logs in user identified by given mail and password, and returns {@link Session} object.
	 * @param email E-mail to identify the user.
	 * @param password Password associated to user in plain.
	 * @return A {@link Session} object.
	 * @throws ServerException Thrown to indicate that the Server has failed handling the latest request.
	 * @throws WrongEmailOrPasswordException Thrown to indicate that given email does not exist or given password does not match.
	 * @throws UserAlreadyLoggedInException Thrown to indicate that user is already logged in. Multiple sessions for the same user are not supported.
	 */
	Session login(String email, String password) throws ServerException, WrongEmailOrPasswordException, UserAlreadyLoggedInException;

	/**
	 * Logs out user for given sessionToken if user has been logged in. Does not indicate if user has been logged in.
	 */
	void logout(Session sessionToken);
}
