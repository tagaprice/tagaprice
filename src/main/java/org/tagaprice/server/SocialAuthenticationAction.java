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

import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.tagaprice.server.dao.couchdb.CouchDbDaoFactory;

public class SocialAuthenticationAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Properties _properties;
	
	public SocialAuthenticationAction() throws IOException {
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

		if(req.getParameter("invitekey")!=null){
			session.setAttribute("invitekey", req.getParameter("invitekey").trim());
		}
		
		String id = req.getParameter("id").trim();
		

		// Create an instance of SocialAuthConfgi object
		SocialAuthConfig config = SocialAuthConfig.getDefault();

		// load configuration. By default load the configuration from
		// oauth_consumer.properties.
		// You can also pass input stream, properties object or properties file
		// name.
		try {
			config.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create an instance of SocialAuthManager and set config
		SocialAuthManager manager = new SocialAuthManager();
		try {
			manager.setSocialAuthConfig(config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// URL of YOUR application which will be called after authentication
		//System.out.println("RequestURL:"+req.getRequestURL());
		//System.out.println("hostName: "+req.getLocalName());
		String successUrl =_properties.getProperty("redirecturl")+"/TagAPrice/socialAuthSuccessAction";	
		

		// get Provider URL to which you should redirect for authentication.
		// id can have values "facebook", "twitter", "yahoo" etc. or the OpenID
		// URL
		String url = null;
		try {
			url = manager.getAuthenticationUrl(id, successUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Store in session
		session.setAttribute("authManager", manager);

		resp.sendRedirect(url);

		// rd.forward(req, resp);
		// out.print(getServletContext().getRequestDispatcher(""));
		// out.print(url);
	}
}
