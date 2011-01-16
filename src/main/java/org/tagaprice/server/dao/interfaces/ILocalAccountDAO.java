package org.tagaprice.server.dao.interfaces;

import org.tagaprice.core.api.LocalAccount;
import org.tagaprice.core.api.WrongEmailOrPasswordException;

public interface ILocalAccountDAO {
	//	String query_getByMailAndPassword;

	//	public Account save();

	public LocalAccount getByEmailAndPassword(String email, String password) throws WrongEmailOrPasswordException;
}
