package org.tagaprice.server.dao.hibernate;

import org.hibernate.SessionFactory;
import org.tagaprice.core.api.WrongEmailOrPasswordException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.server.dao.interfaces.IAccountDAO;

public class HibernateAccountDAO implements IAccountDAO {
	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	public Account getByEmailAndPassword(String email, String password) throws WrongEmailOrPasswordException {
		// TODO Auto-generated method stub
		return null;
	}
}
