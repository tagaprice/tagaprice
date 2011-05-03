package org.tagaprice.server.rpc;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.shared.entities.accountmanagement.Session;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.exceptions.UserAlreadyLoggedInException;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.accountmanagement.ILoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements ILoginService {

	private static final long serialVersionUID = 2766434026811432034L;

	HttpSession session;

	MyLogger _logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	private ISessionDao _sessionDao;
	private IUserDao _userDao;
	
	public LoginServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();

		_sessionDao = daoFactory.getSessionDao();
		_userDao = daoFactory.getUserDao();
	}

	@Override
	public String setLogin(String email, String password) throws WrongEmailOrPasswordException,
	UserAlreadyLoggedInException, DaoException {
		User user = _userDao.getByMail(email);
		String rc = null;
		
		if (_checkPassword(user, password)) {
			// create Session (default expiration time: 1h)
			Date expirationDate = new Date(Calendar.getInstance().getTimeInMillis()+3600000);
			Session session = _sessionDao.create(new Session(user, expirationDate));
			rc = session.getId();
		}
		else throw new WrongEmailOrPasswordException("Please controll user and password");
		
		return rc;
	}

	@Override
	public void setLogout() throws UserNotLoggedInException {
		try {
			Session session = _sessionDao.get(getSid());
			_sessionDao.delete(session);
			
		} catch (DaoException e) {
			throw new UserNotLoggedInException("Logout error", e);
		}
	}

	@Override
	public String isLoggedIn() {
		String rc = null;

		try {
			rc = _sessionDao.get(getSid()).getId();
		}
		catch (DaoException e) {/* we'll simply return null */}
		
		return rc;
	}

	@Override
	public Boolean isEmailAvailable(String email) {
		User user = _userDao.getByMail(email);
		return user == null;
	}



	@Override
	public Boolean setNewPassword(String oldPassword, String newPassword, String newPassword2)
	throws UserNotLoggedInException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerUser(String email, String password, boolean agreeTerms) throws DaoException {
		// TODO do some error handling here
		if (!agreeTerms) return null;
		if (!isEmailAvailable(email)) return null;
		if (password.length() < 6) return null;
		
		_logger.log("Try to register: email: " + email + ", password: " + password);

		User user = new User(email); // TODO we need an actual user name here
		user.setMail(email);
		user.setPasswordHash(password);
		_userDao.create(user);

		return "successful"; // I don't want a session to be returned that soon. There should be a mail verification before
	}


	private String getSid(){
		if(session==null)session = getThreadLocalRequest().getSession();

		return session.getId();
	}
	
	private boolean _checkPassword(User user, String password) {
		return password.equals(user.getPasswordHash()); // TODO implement password check
	}

}
