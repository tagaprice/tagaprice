package org.tagaprice.core.api;

import org.tagaprice.core.entities.Session;

public interface ILoginService {
	//TODO add exception
	/**
	 * Attempts to create a session object for user identified by given mail and password.
	 * If no such user exists, i.e. email does not exist or password does not match, an exception will be thrown.
	 * @return
	 * @throws ServerException
	 */
	Session login(String email, String password) throws ServerException;
}
