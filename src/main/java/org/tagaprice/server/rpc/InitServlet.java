package org.tagaprice.server.rpc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.tagaprice.server.dao.IDaoFactory;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static IDaoFactory m_daoFactory = null;
	
	public void init() throws ServletException {
		try {
			ServletContext context = getServletContext();
			String daoFactoryClassName = context.getInitParameter("daoFactory");
			if (daoFactoryClassName == null) throw new ServletException("No 'daoFactory' context parameter found!");
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
	
	public static IDaoFactory getDaoFactory() {
		return m_daoFactory;
	}
}