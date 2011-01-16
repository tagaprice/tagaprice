package org.tagaprice.server.service;

import org.tagaprice.core.api.ILoginService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.server.dao.interfaces.ILocalAccountDAO;

public class DefaultLoginService implements ILoginService {
	ILocalAccountDAO _localAccountDAO;

	@Override
	public void login(String email, String password) throws ServerException {
		Account account = _localAccountDAO.getByEmailAndPassword(email, password);

		//return sessionFactory.getSession(account);
	}

	public void setLocalAccountDAO(ILocalAccountDAO localAccountDAO) {
		_localAccountDAO = localAccountDAO;
	}
}
