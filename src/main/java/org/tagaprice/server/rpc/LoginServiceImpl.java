package org.tagaprice.server.rpc;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.tagaprice.shared.exceptions.UserAlreadyLoggedInException;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.accountmanagement.ILoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements ILoginService {

	private static final long serialVersionUID = 2766434026811432034L;

	ArrayList<AccountUser> userList = new ArrayList<LoginServiceImpl.AccountUser>();


	String sessionId = null; // is working for just one user ;-)
	HttpSession session;


	MyLogger _logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	public LoginServiceImpl() {
		//Is only to be sure that session has been created

	}

	@Override
	public String setLogin(String email, String password) throws WrongEmailOrPasswordException,
	UserAlreadyLoggedInException {

		if (email.isEmpty() || password.isEmpty())
			throw new WrongEmailOrPasswordException("Please controll user and password");



		for(AccountUser au:userList){
			if(au.email.equals(email)){

				if(au.getPassword().equals(password)){
					//create session

					au.setSessionId(getSid());
					return au.getSessionId();
				}else{
					throw new WrongEmailOrPasswordException("Please controll user and password");
				}


			}
		}

		throw new WrongEmailOrPasswordException("Please controll user and password");

		/*
		if (email.equals("test"))
			throw new WrongEmailOrPasswordException("Please controll user and password");

		if (sessionId != null)
			throw new UserAlreadyLoggedInException("User already logged in");
		// impl UserAlreadyLoggedInException

		sessionId = "" + Math.random();
		// TODO Auto-generated method stub
		return sessionId;
		 */
	}

	@Override
	public void setLogout() throws UserNotLoggedInException {


		for(AccountUser au:userList){
			if(au.getSessionId().equals(getSid())){
				if(session==null)session=getThreadLocalRequest().getSession();
				session.invalidate();
				au.setSessionId(null);
			}
		}

	}

	@Override
	public String isLoggedIn() {

		for(AccountUser au:userList){
			if(au.getSessionId().equals(getSid())){
				return au.getSessionId();
			}
		}

		return null;
	}

	@Override
	public Boolean isEmailAvailable(String email) {
		boolean evailable = true;

		for(AccountUser au:userList){
			if(au.email.equals(email))
				evailable=false;
		}

		// TODO Auto-generated method stub
		return evailable;
	}



	@Override
	public Boolean setNewPassword(String oldPassword, String newPassword, String newPassword2)
	throws UserNotLoggedInException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerUser(String email, String password, String confirmPassword, String reCaptchaChallange,
			String reCaptchaResponse, boolean agreeTerms) {

		_logger.log("Try to register: email: " + email + ", password: " + password + ", ConfirmPassword: "
				+ confirmPassword + ", Challange: " + reCaptchaChallange + ", Response: "+reCaptchaResponse);

		if (isEmailAvailable(email) == true && password.equals(confirmPassword)
				&& isRecaptchaOK(reCaptchaChallange, reCaptchaResponse)
				&& agreeTerms==true) {

			// TODO Register User and send mail

			AccountUser au = new AccountUser(email, password);
			userList.add(au);


			try {
				au.setSessionId(setLogin(email, password));
				return au.getSessionId();
			} catch (WrongEmailOrPasswordException e) {
				e.printStackTrace();
			} catch (UserAlreadyLoggedInException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * Check if recaptche is ok
	 * 
	 * @param reCaptchaChallange
	 * @param reCaptchaResponse
	 * @return
	 */
	private boolean isRecaptchaOK(String reCaptchaChallange, String reCaptchaResponse) {

		return true;
	}

	private String getSid(){
		if(session==null)session = getThreadLocalRequest().getSession();

		return session.getId();
	}




	class AccountUser{
		String email;
		String password;
		String sessionId=null;

		public AccountUser(String email, String password){
			setEmail(email);
			setPassword(password);
		}


		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * @return the sessionId
		 */
		public String getSessionId() {
			return sessionId;
		}

		/**
		 * @param sessionId the sessionId to set
		 */
		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}


	}

}
