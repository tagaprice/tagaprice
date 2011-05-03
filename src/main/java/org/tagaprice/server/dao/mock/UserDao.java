package org.tagaprice.server.dao.mock;

import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.shared.entities.accountmanagement.User;

public class UserDao extends DaoClass<User> implements IUserDao {
	@Override
	public User getByMail(String mail) {
		User rc = null;
		for (User user: _getCurrentRevisions()) {
			if (user.getMail().equals(mail)) {
				rc = user;
				break;
			}
		}
		return rc;
	}

}
