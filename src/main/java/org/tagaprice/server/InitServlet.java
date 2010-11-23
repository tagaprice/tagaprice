package org.tagaprice.server;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Initialization Servlet
 * 
 *  This Servlet is just for general initialization stuff.
 * 
 *  Initializes the mail system.
 */
public class InitServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes the mail system.
	 * 
	 * @throws ServletException if the initialization of the mail system fails for any reason.
	 */
	@Override
	public void init() throws ServletException {
		try {
			Mail.init();
		} catch (IOException e) {
			throw new ServletException("mailing subsystem init failed: "+e.getMessage(), e);
		}
	}

	/**
	 * This method does nothing
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response)
	throws ServletException, IOException {
		// nothing
	}

}
