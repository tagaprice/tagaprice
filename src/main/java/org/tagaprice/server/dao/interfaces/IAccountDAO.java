package org.tagaprice.server.dao.interfaces;

import org.tagaprice.core.api.WrongEmailOrPasswordException;
import org.tagaprice.core.entities.Account;

public interface IAccountDAO {
	//	String query_getByMailAndPassword;

	//	public Account save();

	/**
	 * Return
	 */
	public Account getByEmailAndPassword(String email, String password) throws WrongEmailOrPasswordException;
}
