package org.tagaprice.server.rpc;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.xml.DOMConfigurator;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.mock.MockDaoFactory;
import org.tagaprice.shared.exceptions.dao.DaoException;

/**
 * DAO Factory initialization servlet
 * 
 * This servlet's sole purpose is to read the DAO factory class that should be instantiated
 * 
 * Its &lt;servlet&gt; block in web.xml is annotated with load-on-startup which makes sure the init()
 * method gets called on ther server's startup
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static IDaoFactory m_daoFactory = null;

	/**
	 * Read the daoFactory context-parameter from web.xml
	 */
	@Override
	public void init() throws ServletException {
		// load the Log4j configuration file
		DOMConfigurator.configureAndWatch("log4j.xml");
		
		// init the DAO factory
		initDaoFactory();
	}
	
	private void initDaoFactory() throws ServletException {
		try {
			// First get the class name
			String daoFactoryClassName = System.getenv("daoFactoryClass");
			if (daoFactoryClassName == null) {
				ServletContext context = getServletContext();
				daoFactoryClassName = context.getInitParameter("daoFactoryClass");
				if (daoFactoryClassName == null) throw new ServletException("No 'daoFactory' context parameter found!");
			}
			log("Using DAO factory '"+daoFactoryClassName+"'");

			// Then ask the ClassLoader to resolve it for us and create an instance
			Object daoFactoryObject = Thread.currentThread().getContextClassLoader().loadClass(daoFactoryClassName).newInstance();
			if (daoFactoryObject instanceof IDaoFactory) {
				InitServlet.m_daoFactory = (IDaoFactory) daoFactoryObject;
				InitServlet.m_daoFactory.init();
			}
			
			//Initialization of the mail class
			try {
				Mail.init();
			} catch (IOException e) {
				throw new ServletException("mailing subsystem init failed: "+e.getMessage(), e);
			}
		}
		catch (ClassNotFoundException e) {
			throw new ServletException("Couldn't load DAO Factory", e);
		}
		catch (IllegalAccessException e) {
			throw new ServletException("Couldn't access DAO factory", e);
		}
		catch (InstantiationException e) {
			throw new ServletException("Failed to load DAO Factory", e);
		}
	}

	/**
	 * Returns a singleton instance of the configured {@link IDaoFactory} implementation
	 * @return IDaoFactory singleton instance
	 */
	public static IDaoFactory getDaoFactory() {
		//TODO maybe there is a more beautiful implementation. Because this is only important for testings.
		if(InitServlet.m_daoFactory==null){
			try {
				InitServlet.m_daoFactory=new MockDaoFactory();
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return InitServlet.m_daoFactory;
	}

}