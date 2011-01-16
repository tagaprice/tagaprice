package org.tagaprice.server.service;

import java.util.HashMap;
import java.util.Map;

import org.tagaprice.core.api.UserAlreadyLoggedInException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.utilities.RandomNumberGenerator;

public class SessionService {

	private static final int SESSION_KEY_LENGTH = 256;
	private Map<Account, Session> _accountsLoggedIn = new HashMap<Account, Session>();

	public Session createSession(Account account) throws UserAlreadyLoggedInException {

		if(_accountsLoggedIn.get(account) != null)
			throw new UserAlreadyLoggedInException("User already logged in.");

		Session session = new Session(RandomNumberGenerator.generateRandom(SessionService.SESSION_KEY_LENGTH));
		_accountsLoggedIn.put(account, session);
		return session;
	}
}
