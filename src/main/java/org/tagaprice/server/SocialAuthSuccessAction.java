package org.tagaprice.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;

public class SocialAuthSuccessAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
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
			System.out.println(p.getFirstName());

			// OR also obtain list of contacts
			// List<Contact> contactsList = provider.getContactList();

			out.print("contactsSize: " + p.getFirstName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
