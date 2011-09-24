package org.tagaprice.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

public class SocialAuthenticationAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession(true);

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
		String successUrl ="http://"+req.getLocalName()+":"+req.getLocalPort();	
		
		
		successUrl += "/TagAPrice/socialAuthSuccessAction";

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
