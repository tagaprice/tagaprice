package org.tagaprice.server.service;

import org.tagaprice.core.api.ILoginService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.dao.interfaces.IAccountDAO;

public class DefaultLoginService implements ILoginService {
	IAccountDAO _accountDAO;
	SessionService _sessionFactory;

	@Override
	public Session login(String email, String password) throws ServerException {
		if(email == null)
			throw new IllegalArgumentException("email is null");
		if(password == null)
			throw new IllegalArgumentException("password is null");

		Account account = _accountDAO.getByEmailAndPassword(email, password);

		return _sessionFactory.createSession(account);
	}

	public void setLocalAccountDAO(IAccountDAO localAccountDAO) {
		_accountDAO = localAccountDAO;
	}

	public void setSessionFactory(SessionService sessionFactory) {
		_sessionFactory = sessionFactory;
	}
}


