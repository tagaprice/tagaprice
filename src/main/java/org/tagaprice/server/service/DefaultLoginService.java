package org.tagaprice.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.ILoginService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.dao.interfaces.IAccountDAO;

public class DefaultLoginService implements ILoginService {
	private IAccountDAO _accountDAO;
	private SessionService _sessionFactory;
	
	private static Logger _log = LoggerFactory.getLogger(DefaultLoginService.class);

	@Transactional(readOnly=true)
	@Override
	public Session login(String email, String password) throws ServerException {
		if(email == null)
			throw new IllegalArgumentException("email is null");
		if(password == null)
			throw new IllegalArgumentException("password is null");

		Account account = _accountDAO.getByEmailAndPassword(email, password);

		Session session = _sessionFactory.createSession(account);
		
		_log.debug("account: "+account.toString() +" has logged in. Session: "+session);
		
		return session;
	}
	
	@Override
	public void logout(Session session) {
		_log.debug("session: "+session);
		
		_sessionFactory.deleteSession(session);
	}

	public void setLocalAccountDAO(IAccountDAO localAccountDAO) {
		_accountDAO = localAccountDAO;
	}

	public void setSessionFactory(SessionService sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	
}


