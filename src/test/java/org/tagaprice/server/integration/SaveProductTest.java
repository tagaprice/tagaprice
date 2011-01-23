package org.tagaprice.server.integration;

import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.service.DefaultProductService;

@ContextConfiguration
public class SaveProductTest extends AbstractJUnit4SpringContextTests {
	private Logger _log = LoggerFactory.getLogger(SaveProductTest.class);
	private IDbTestInitializer _dbInitializer;
	private DefaultProductService _productManagement;
	
	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		_productManagement = applicationContext.getBean("defaultProductManagement", DefaultProductService.class);
	}
	
	@After
	public void tearDown() throws Exception {
		_dbInitializer.resetTables();
	}
	
	

}
