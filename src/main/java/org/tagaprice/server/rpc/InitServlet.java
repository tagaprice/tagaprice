package org.tagaprice.server.rpc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.tagaprice.server.dao.IDaoFactory;

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
	public void init() throws ServletException {
		try {
			// First get the class name
			String daoFactoryClassName = System.getenv("daoFactoryClass");
			if (daoFactoryClassName == null) {
				ServletContext context = getServletContext();
				daoFactoryClassName = context.getInitParameter("daoFactoryClass");
				if (daoFactoryClassName == null) throw new ServletException("No 'daoFactory' context parameter found!");
			}
			log("Blubb: "+daoFactoryClassName);
			
			// Then ask the ClassLoader to resolve it for us and create an instance
			Object daoFactoryObject = Thread.currentThread().getContextClassLoader().loadClass(daoFactoryClassName).newInstance(); 
			if (daoFactoryObject instanceof IDaoFactory) {
				m_daoFactory = (IDaoFactory) daoFactoryObject;
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
		return m_daoFactory;
	}
}