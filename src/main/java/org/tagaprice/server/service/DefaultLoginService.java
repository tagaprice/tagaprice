package org.tagaprice.server.service;

import org.tagaprice.core.api.ILoginService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.dao.interfaces.ILocalAccountDAO;

public class DefaultLoginService implements ILoginService {
	ILocalAccountDAO _localAccountDAO;
	SessionService _sessionFactory;

	@Override
	public Session login(String email, String password) throws ServerException {
		Account account = _localAccountDAO.getByEmailAndPassword(email, password);

		return _sessionFactory.createSession(account);
	}

	public void setLocalAccountDAO(ILocalAccountDAO localAccountDAO) {
		_localAccountDAO = localAccountDAO;
	}

	public void setSessionFactory(SessionService sessionFactory) {
		_sessionFactory = sessionFactory;
	}
}


