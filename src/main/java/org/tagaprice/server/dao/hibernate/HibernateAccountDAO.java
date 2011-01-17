package org.tagaprice.server.dao.hibernate;

import org.hibernate.SessionFactory;
import org.tagaprice.core.api.WrongEmailOrPasswordException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.server.dao.interfaces.IAccountDAO;

public class HibernateAccountDAO implements IAccountDAO {
	private SessionFactory _sessionFactory;
	private String _query_email_password_string = "from Account as account where email = ? and password = ?";

	public HibernateAccountDAO() {

	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	public Account getByEmailAndPassword(String email, String password) throws WrongEmailOrPasswordException {
		Account account = (Account) _sessionFactory.getCurrentSession()
		.createQuery(_query_email_password_string)
		.setString(0, email)
		.setString(1, password)
		.uniqueResult();
		if(account == null)
			throw new WrongEmailOrPasswordException("email "+email+" does not exist or password is wrong");
		return account;
	}
}
