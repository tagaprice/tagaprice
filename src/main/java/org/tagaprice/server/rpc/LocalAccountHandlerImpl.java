/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License.
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: UserHandlerImpl.java
 * Date: 07.07.2010
 */
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.Cookie;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.AccountDAO;
import org.tagaprice.server.dao.postgres.LocalAccountDAO;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.server.dao.postgres.LoginDAO;
import org.tagaprice.server.dao.postgres.SessionDAO;
import org.tagaprice.server.utilities.SaltGenerator;
import org.tagaprice.shared.entities.Session;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.InvalidLoginException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.ServerException;
import org.tagaprice.shared.rpc.LocalAccountHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LocalAccountHandlerImpl extends RemoteServiceServlet implements LocalAccountHandler {
	private DBConnection db;
	private LocalAccountDAO _localAccountDao;
	private LoginDAO _loginDao;
	private SessionDAO _sessionDao;
	private AccountDAO _accountDao;

	public LocalAccountHandlerImpl() {
		try {
			db = new DBConnection();
			_localAccountDao = new LocalAccountDAO(db);
			_loginDao = new LoginDAO(db);
			new LocaleDAO(db).get("English").getId();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidLocaleException e) {
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Checks whether given mail is valid as a e-mail address for a user of tagaprice.
	 * @param mail The e-mail address to check.
	 * @return Returns whether given mail is a valid e-mail address and not used by another registered user yet.
	 * @throws DAOException
	 */
	public boolean isMailAvailable(String mail) throws DAOException {
		if(!mail.toLowerCase().trim().matches(".+@.+\\.[a-z][a-z]+")) {
			return false;
		}

		if(_accountDao.getUserIdByMail(mail) == -1)
			return true;

		return false;
	}

	/**
	 * Logs the user in and retrieves the corresponding Session object.
	 * @param mail User's e-mail address.
	 * @param password Password as entered by user. TODO encrypted ?
	 * @return A session with user id and session id set.
	 * @throws ServerException
	 */
	public Session login(String mail, String password) throws ServerException {
		long uid = _accountDao.getUserIdByMailAndPassword(mail, password);
		if(uid == -1)
			return null;

		Session session = _sessionDao.save(new Session(SaltGenerator.generateSalt(32),uid));

		return session;
	}

	//
	// Deprecated TODO reimplement for proper login and logout functionality
	//

	@Override
	@Deprecated
	public boolean checkMailAvailability(String email) throws IllegalArgumentException {
		try {
			return _localAccountDao.isEmailAvailable(email);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (NotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Deprecated
	private String getSid() throws InvalidLoginException{
		Cookie[] cookies = this.getThreadLocalRequest().getCookies();

		return _loginDao.getSid(cookies);
	}

	@Override
	@Deprecated
	public String loginDeprecated(String mail, String password) throws IllegalArgumentException {
		try {
			return _loginDao.login(mail, password);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	@Deprecated
	public Long getId() throws IllegalArgumentException, InvalidLoginException {
		try {
			return _loginDao.getId(getSid());
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	@Deprecated
	public boolean logout() throws IllegalArgumentException, InvalidLoginException {
		try {
			return _loginDao.logout(getSid());
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	//
	// Stubbed TODO reimplement for register and confirm functionality
	//

	@Override
	@Deprecated
	public boolean registerNewUser(String password,
			String confirmPassword, String email, boolean gtc)
	throws IllegalArgumentException {
		return false;
		//
		//		/// TODO add a class LocalAccountValidator and us it from both the client and the server side
		//		//Check validity
		//		if(!password.equals(confirmPassword) &&
		//				checkMailAvailability(email) &&
		//				!email.trim().isEmpty() &&
		//				gtc)
		//			return false;
		//
		//		//Start with saving and sending confirm email
		//
		//		try {
		//
		//			LocalAccount account = new LocalAccount(
		//					"usernamex",
		//					localeId,
		//					null,
		//					email,
		//					password,
		//					null);
		//
		//			_localAccountDao.save(account);
		//
		//			return true;
		//		} catch (SQLException e) {
		//			throw new IllegalArgumentException(e);
		//		} catch (NotFoundException e) {
		//			throw new IllegalArgumentException(e);
		//		} catch (RevisionCheckException e) {
		//			throw new IllegalArgumentException(e);
		//		} catch (InvalidLocaleException e) {
		//			throw new IllegalArgumentException(e);
		//		}
	}


	@Override
	@Deprecated
	public boolean confirm(String confirm) throws IllegalArgumentException {
		return false;
		//		try {
		//			return _localAccountDao.confirm(confirm);
		//		} catch (SQLException e) {
		//			throw new IllegalArgumentException(e);
		//		} catch (NotFoundException e) {
		//			throw new IllegalArgumentException(e);
		//		}
	}


}
