package org.tagaprice.server.rpc;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IInvitationDao;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.exceptions.InvitationKeyUsedOrInvalidException;
import org.tagaprice.shared.exceptions.UserAlreadyLoggedInException;
import org.tagaprice.shared.exceptions.UserNotConfirmedException;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.rpc.accountmanagement.ILoginService;
import com.allen_sauer.gwt.log.client.Log;

public class LoginServiceImpl extends ASessionService implements ILoginService {

	private static final long serialVersionUID = 1L;


	//private ISessionDao _sessionDao;
	private IUserDao _userDao;
	private IInvitationDao _invitationDao;

	public LoginServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();

		//_sessionDao = daoFactory.getSessionDao();
		_userDao = daoFactory.getUserDao();
		_invitationDao = daoFactory.getInvitationDao();
		
	}

	@Override
	public User setLogin(String email, String password) throws DaoException, UserAlreadyLoggedInException, WrongEmailOrPasswordException, UserNotConfirmedException {
		User rc = null;

		User user = _userDao.getByMail(email);

		if (user == null || !_checkPassword(user, password)) throw new WrongEmailOrPasswordException("Please check your login credentials"); 

	
		if(user.isConfirmed()==false) throw new UserNotConfirmedException("Please check your email and click the confirmation link");
		
		setUser(user);
		
		rc=user;
		
		return rc;
	}

	@Override
	public void setLogout() throws UserNotLoggedInException {
		deleteSession();
	}

	@Override
	public User isLoggedIn() {
		User rc = null;

		try {
			if(getUser()!=null)rc=getUser();
		} catch (UserNotLoggedInException e) {
			return null;
		}
		

		return rc;
	}

	@Override
	public boolean isEmailAvailable(String email) {
		if(!email.toLowerCase().trim().matches(".+@.+\\.[a-z][a-z]+")) {
			return false;
		}
		User user = _userDao.getByMail(email);
		return user == null;
	}

	@Override
	public boolean isEmailConfirmed(String email){
		User user = _userDao.getByMail(email);
		if(user == null) return false;
		
		return user.isConfirmed();
	}

	@Override
	public boolean setNewPassword(String newPassword, String confirmPassword)
	throws UserNotLoggedInException, DaoException {
		
		
		newPassword=newPassword.trim();
		confirmPassword=confirmPassword.trim();
		
		if(!newPassword.equals(confirmPassword)) return false;
		if(newPassword.length() < 6 ) return false;
		
		
		String salt = generateSalt(24);
		String pwdHash;

		try {
			pwdHash = md5(newPassword+salt);
		}
		catch (NoSuchAlgorithmException e) {
			throw new DaoException("Couldn't generate password hash: "+e.getMessage(), e);
		}
		
		
		try {
			//Session session = _sessionDao.get(getUser().getId());
			
			User user = _userDao.get(getUser().getId());
			user.setPasswordSalt(salt);
			user.setPasswordHash(pwdHash);
			
			user.setConfirmSalt(generateSalt(24));
			user.setConfirm(md5(user.getMail()+user.getConfirmSalt()));
			
			_userDao.update(user);
		} catch (NoSuchAlgorithmException e) {
			throw new DaoException("Couldn't generate password hash: "+e.getMessage(), e);
		}
		
		return true;
	}

	@Override
	public boolean registerUser(String diplayName, String email, String password, String invitationKey) 
	throws InvitationKeyUsedOrInvalidException, UserAlreadyLoggedInException, DaoException {
		Log.debug("Try to register: email: " + email + ", password: " + password);

		
		
		if(!_invitationDao.checkKey(invitationKey.trim()))throw new InvitationKeyUsedOrInvalidException();

		if(_userDao.getByMail(email)!=null) throw new UserAlreadyLoggedInException("This user has been registered");
		
		password=password.trim();
		email=email.trim();
		
		// do some error handling here
		if (!isEmailAvailable(email)) return false;
		if (password.length() < 6) return false;
		if (diplayName.trim().isEmpty()) return false;


		String salt = generateSalt(24);
		String pwdHash;

		try {
			pwdHash = md5(password+salt);
		}
		catch (NoSuchAlgorithmException e) {
			throw new DaoException("Couldn't generate password hash: "+e.getMessage(), e);
		}

		
		try {
			User user = new User(diplayName); 
			user.setMail(email);
			user.setPasswordSalt(salt);
			user.setPasswordHash(pwdHash);
			
			user.setConfirmSalt(generateSalt(24));
			user.setConfirm(md5(user.getMail()+user.getConfirmSalt()));
			
			//save conf
			user = _userDao.create(user);
			
			// send confirmation mail
			HashMap<String, String> replacements = new HashMap<String, String>();
			
			replacements.put("conf", user.getConfirm());
			replacements.put("mail", user.getMail());
			replacements.put("host", "beta.tagaprice.org");
			replacements.put("link", "TagAPrice/confirmservice");
			Mail.getInstance().send("regConfirm", new InternetAddress(user.getMail()),  replacements);
			
			//set Key has been used
			_invitationDao.useKey(invitationKey.trim(), user);
			//System.out.println("key: "+_invitationDao.generateKey(user));
			//System.out.println("key: "+_invitationDao.generateKey(user));
			//System.out.println("key: "+_invitationDao.generateKey(user));
		} catch (AddressException e) {
			throw new DaoException("AddressException: "+e.getMessage(), e);
		} catch (MessagingException e) {
			throw new DaoException("MessagingException: "+e.getMessage(), e);
		} catch (IOException e) {
			throw new DaoException("IOException: "+e.getMessage(), e);
		}catch (NoSuchAlgorithmException e) {
			throw new DaoException("IOException: "+e.getMessage(), e);	
		}
		
		
		

		return true; // I don't want a session to be returned that soon. There should be a mail verification before
	}

	@Override
	public boolean addEmailToInviteQueue(String email) throws DaoException {
		return false;
		/*
		if (!isEmailAvailable(email)) return false;
		
		User user = new User(email); 
		user.setMail(email);
		user.setProperty("inviteme", "true");
		
		try {
			user = _userDao.create(user);
		} catch (DaoException e) {
			throw new DaoException("IOException: "+e.getMessage(), e);	
		}
		
		if(user.getId()!=null)
			return true;
		
		return false;
		*/
	}

}
