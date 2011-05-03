package org.tagaprice.server.dao;

import org.tagaprice.shared.entities.accountmanagement.User;

public interface IUserDao extends IDaoClass<User> {
	User getByMail(String mail);
}
