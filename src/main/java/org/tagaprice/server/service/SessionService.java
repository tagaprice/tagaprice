package org.tagaprice.server.service;

import java.util.HashMap;
import java.util.Map;

import org.tagaprice.core.api.UserAlreadyLoggedInException;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.utilities.RandomNumberGenerator;

public class SessionService {

	private static final int SESSION_KEY_LENGTH = 256;
	private Map<Account, Session> _accountsToSessions = new HashMap<Account, Session>();
	private Map<Session, Account> _sessionsToAccounts = new HashMap<Session, Account>();

	public Session createSession(Account account) throws UserAlreadyLoggedInException {

		if(_accountsToSessions.get(account) != null)
			throw new UserAlreadyLoggedInException("User already logged in.");

		Session session = new Session(RandomNumberGenerator.generateRandom(SessionService.SESSION_KEY_LENGTH));
		_accountsToSessions.put(account, session);
		_sessionsToAccounts.put(session, account);
		return session;
	}

	public Account getAccount(Session session) throws UserNotLoggedInException {
		Account account = _sessionsToAccounts.get(session);
		if(account == null)
			throw new UserNotLoggedInException("Requested user is not logged in.");
		return account;
	}
}
