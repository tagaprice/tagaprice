package org.tagaprice.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.server.dao.couchdb.CouchDbDaoFactory;
import org.tagaprice.server.rpc.ASessionService;
import org.tagaprice.server.rpc.InitServlet;
import org.tagaprice.shared.entities.accountmanagement.User;

public class SocialAuthSuccessAction extends ASessionService {

	private static final long serialVersionUID = 1L;

	private IUserDao _userDao;
	
	public SocialAuthSuccessAction() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();

		_userDao = daoFactory.getUserDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		HttpSession session = req.getSession(true);

		// get the social auth manager from session
		SocialAuthManager manager = (SocialAuthManager) session
				.getAttribute("authManager");

		// call connect method of manager which returns the provider object.
		// Pass request parameter map while calling connect method.
		AuthProvider provider;
		try {
			provider = manager.connect(SocialAuthUtil
					.getRequestParametersMap(req));
			// get profile
			Profile p = provider.getUserProfile();

			// you can obtain profile information
			//System.out.println(p.getFirstName());

			// OR also obtain list of contacts
			// List<Contact> contactsList = provider.getContactList();
			//out.print("contactsSize: " + p.getFirstName());
			
			
			//is for systems with email
			//is email free
			if(!p.getEmail().isEmpty()){
				User user = _userDao.getByMail(p.getEmail());
				
				
				if(user==null){
					//create new user
					user = new User(p.getFirstName());
					//user.setTitle(p.getDisplayName());
					user.setMail(p.getEmail());
					user.setProperty(p.getProviderId(), "true");
					user = _userDao.create(user);
										
					//setLOGIN
					//setUser(user);
					//getThreadLocalRequest().getSession(true).setAttribute("suser", user);
					
					req.getSession(true).setAttribute("suser", user);
					
					resp.sendRedirect(p+"/#start:null");
				}else {
					// login with this user
					
					if(user.getProperty(p.getProviderId()).equals("true")){
						req.getSession(true).setAttribute("suser", user);
						resp.sendRedirect("/#start:null");
					}
				}
			}
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
