package org.tagaprice.core.api;

public interface ILoginService {
	//TODO change return value to Session
	//TODO add exception
	/**
	 * Attempts to create a session object for user identified by given mail and password.
	 * If no such user exists, i.e. email does not exist or password does not match, an exception will be thrown.
	 * @throws ServerException
	 */
	void login(String email, String password) throws ServerException;
}
