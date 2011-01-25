package org.tagaprice.server.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagaprice.core.api.UserAlreadyLoggedInException;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.utilities.RandomNumberGenerator;

public class SessionService {

	private static final int SESSION_KEY_LENGTH = 256;
	private Map<Account, Session> _accountsToSessions = Collections.synchronizedMap(new HashMap<Account, Session>()); 
	private Map<Session, Account> _sessionsToAccounts = Collections.synchronizedMap(new HashMap<Session, Account>());
	
	private static Logger _log = LoggerFactory.getLogger(SessionService.class);

	public synchronized Session createSession(Account account) throws UserAlreadyLoggedInException {
		_log.debug(account.toString());
		
		if(_accountsToSessions.get(account) != null)
			throw new UserAlreadyLoggedInException("User already logged in.");

		Session session = new Session(RandomNumberGenerator.generateRandom(SessionService.SESSION_KEY_LENGTH));
		_accountsToSessions.put(account, session);
		_sessionsToAccounts.put(session, account);
		
		_log.debug("created session: "+session);
		return session;
	}

	public synchronized Account getAccount(Session session) throws UserNotLoggedInException {
		_log.debug(session.toString());
		Account account = _sessionsToAccounts.get(session);
		if(account == null)
			throw new UserNotLoggedInException("Requested user is not logged in.");
		
		_log.debug("return account: "+account);
		return account;
	}

	public synchronized void deleteSession(Session session) {
		_log.debug(session.toString());
		if(!_sessionsToAccounts.containsKey(session))
			return;
		
		Account account = _sessionsToAccounts.get(session);
		_sessionsToAccounts.remove(session);
		_accountsToSessions.remove(account);
	}
	
	/**
	 * This deletes all sessions, all logged in users will be logged out.
	 */
	public synchronized void clear() {
		_sessionsToAccounts.clear();
		_accountsToSessions.clear();
	}
}
