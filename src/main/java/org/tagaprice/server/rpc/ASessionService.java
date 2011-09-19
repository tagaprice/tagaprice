package org.tagaprice.server.rpc;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class ASessionService extends RemoteServiceServlet {

	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private IUserDao _userDao;
	protected static Random _random = _createPRNG();
	
	public ASessionService() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();	
		_userDao = daoFactory.getUserDao();
	}
	
	
	protected void deleteSession() throws UserNotLoggedInException{
		try{
			getThreadLocalRequest().getSession().removeAttribute("suser");
		} catch (IllegalStateException  e){
			throw new UserNotLoggedInException("Logout error", e);
		}
	}
	
	protected void setUser(User user){
		getThreadLocalRequest().getSession().setAttribute("suser", user);
	}
	
	/**
	 * Returns the current loggedin user or null
	 * @return
	 */
	protected User getUser() throws UserNotLoggedInException{
		try{
			return (User)getThreadLocalRequest().getSession().getAttribute("suser");
		} catch (IllegalStateException  e){
			throw new UserNotLoggedInException("Can't save a shop without having a valid session!");
		}
	}
	
	
	protected boolean _checkPassword(User user, String password) throws DaoException {
		String hash = user.getPasswordHash();
		String salt = user.getPasswordSalt();

		try {
			return hash.equals(md5(password+salt));
		}
		catch (NoSuchAlgorithmException e) {
			throw new DaoException("Couldn't generate password hash: "+e.getMessage(), e);
		}
	}

	protected String md5(String in) throws NoSuchAlgorithmException {
		// calculate hash
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(in.getBytes());
		byte[] hash = md5.digest();
		StringBuffer rc = new StringBuffer();
		for (int i=0; i<hash.length; i++) {
			String hex = "0"+Integer.toHexString(0xFF & hash[i]);
			if (hex.length()>2) hex = hex.substring(hex.length()-2);
			rc.append(hex);
		}

		return rc.toString();
	}

	/**
	 * Use a Pseudo Random Number Generator to generate an arbitrary-length random String that
	 * can be used as password hash salt (or for anything else)
	 * 
	 * @param len Desired length of the returned String
	 * @return Random String with the given length
	 */
	public static String generateSalt(int len) {
		String rc = "";

		for (int i = 0; i < len; i++) {
			int n = _random.nextInt(62);
			char c;
			if (n < 26) c = (char)(n+'a');
			else if (n < 52) c = (char)(n-26+'A');
			else c = (char) (n-52+'0');
			rc += c;
		}
		return rc;
	}


	/**
	 * Creates, initializes and returns a Pseudo Random Number Generator object
	 * 
	 * On UNIX-like systems this function initializes the PRNG using data read from
	 * /dev/urandom to make sure the seed is less predictable.
	 * @return PRNG object
	 */
	private static Random _createPRNG() {
		Random rc = null;

		try {
			FileInputStream in = new FileInputStream("/dev/urandom");
			int n;
			long seed = 0;

			// read 8 characters and put them in a long variable
			for (int i = 0; i < 8; i++) {
				n = in.read();
				if(n >= 0) {
					seed *= 256;
					seed += n;
				}
			}

			rc = new Random(seed);
		}
		catch (IOException e) { // /dev/urandom can't be read
			Log.error("Warning: using current time as random seed");
			rc = new Random();
		}

		return rc;
	}
}
