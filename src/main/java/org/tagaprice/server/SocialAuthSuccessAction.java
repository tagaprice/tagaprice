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
import org.tagaprice.server.dao.IInvitationDao;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.server.dao.couchdb.CouchDbDaoFactory;
import org.tagaprice.server.rpc.ASessionService;
import org.tagaprice.server.rpc.InitServlet;
import org.tagaprice.shared.entities.accountmanagement.User;

public class SocialAuthSuccessAction extends ASessionService {

	private static final long serialVersionUID = 1L;

	private IUserDao _userDao;
	private IInvitationDao _inviteDao;
	private static Properties _properties;
	
	public SocialAuthSuccessAction() throws IOException {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();

		_userDao = daoFactory.getUserDao();
		_inviteDao = daoFactory.getInvitationDao();
		
		if(_properties==null){
			_properties=new Properties();
			//Get Properties
			InputStream stream = CouchDbDaoFactory.class.getResourceAsStream("/"+"oauth_consumer.properties");
			_properties.load(stream);	
		}
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
			String username=null;
			String email = null;
			if(p.getProviderId().equals("facebook")){
				if(p.getEmail()!=null)email=p.getEmail();
				if(p.getDisplayName()!=null)username=p.getDisplayName();
				else username=p.getFirstName();
			}else if(p.getProviderId().equals("twitter")){
				if(p.getDisplayName()!=null){
					email = p.getDisplayName();
					username=p.getDisplayName();
				}
			}
						
			
			if(email!=null && username!=null){
				User user = _userDao.getByMail(email);
				
				
				if(user==null){
					if(session.getAttribute("invitekey")!=null){
						
						if(!_inviteDao.checkKey((String)session.getAttribute("invitekey"))){
							session.removeAttribute("invitekey");
							resp.sendRedirect(_properties.getProperty("redirecturl")+"/#start:/null");
							
							return ;
						}else{
							
							//create new user
							user = new User(username);
							//user.setTitle(p.getDisplayName());
							user.setMail(email);
							user.setProperty(p.getProviderId(), "true");
							user.setProperty("inviteCount", 5l);
							user = _userDao.create(user);
							
							_inviteDao.useKey((String)session.getAttribute("invitekey"), user);
							
							session.removeAttribute("invitekey");
							
							//setLOGIN
							//setUser(user);
							//getThreadLocalRequest().getSession(true).setAttribute("suser", user);
							
							req.getSession(true).setAttribute("suser", user);
						}
						//resp.sendRedirect("/#start:null");
					}
					
					
				}else {
					// login with this user
					
					if("true".equals(user.getProperty(p.getProviderId()))){
						req.getSession(true).setAttribute("suser", user);
						
					}
				}
				
				resp.sendRedirect(_properties.getProperty("redirecturl")+"/#start:/null");
			}
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
