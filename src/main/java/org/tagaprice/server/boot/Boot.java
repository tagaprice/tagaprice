package org.tagaprice.server.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This class loads the globally application context, initializes all services, daos and the database (as stated in application context).
 * @author "forste"
 *
 */
public class Boot extends RemoteServiceServlet {
	Logger _log = LoggerFactory.getLogger(Boot.class);
	private ClassPathXmlApplicationContext applicationContext;
	
	
	public Boot() throws Exception {
		_log.info("Booting...");
		try {
			/*
			 * TODO to avoid static mapping by providing name of context, try something like this.
			 * Does not work since this servlet apparently does not have the root application context associated with it.
			 * 
			 * WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			 * applicationContext.getBean("defaultProductService", org.tagaprice.core.api.IProductService.class)
			 * 
			 */
			_log.info("Trying to load application context.");
			applicationContext = new ClassPathXmlApplicationContext("spring/beans.xml");
			_log.info("Loaded application context successfully.");
			
			_log.info("Trying to fill database with testdata");
			IDbTestInitializer _dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);
			_dbInitializer.dropAndRecreate();
			_dbInitializer.fillTables();
			_log.info("Filled database with testdata successfully");
		} catch(Exception e) {
			_log.debug(e.getClass()+": "+e.getMessage());
			throw e;
		} finally {
		}
	}


	public ClassPathXmlApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
