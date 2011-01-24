package org.tagaprice.server.integration;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashSet;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Unit;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.service.DefaultProductService;

@ContextConfiguration
public class SaveProductTest extends AbstractTransactionalJUnit4SpringContextTests { //TODO this class should not need to be transactional - service is already
	private Logger _log = LoggerFactory.getLogger(SaveProductTest.class);
	private IDbTestInitializer _dbInitializer;
	private DefaultProductService _productService;
	private SessionFactory _sessionFactory;
	
	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		_productService = applicationContext.getBean("defaultProductService", DefaultProductService.class);
		
		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
		DbSaveAssertUtility.setSimpleJdbcTemplate(super.simpleJdbcTemplate);
	}
	
	@After
	public void tearDown() throws Exception {
//		_dbInitializer.resetTables();
	}
	
	@Rollback(false)
	@Test
	public void saveNewProduct_shouldPersistProduct_shouldReturnProductWithNewIdSet() throws Exception {
		_log.info("running test");
		Long id = null;
		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		int numberRevisions = 2;
		Product productToSave = HibernateSaveEntityCreator.createProduct(id,
				HibernateSaveEntityCreator.createLocale(1),
				HibernateSaveEntityCreator.createProductRevisions(id, numberRevisions, creator, Unit.ml, HibernateSaveEntityCreator.createCategory(null, creator)));

		
		Product actual = _productService.save(productToSave);
		
		
		Long expectedId = 4L;
		Account expectedCreator = HibernateSaveEntityCreator.createAccount(1L);
		int expectedNumberRevisions = 2;
		Product expected = HibernateSaveEntityCreator.createProduct(expectedId,
				HibernateSaveEntityCreator.createLocale(1),
				HibernateSaveEntityCreator.createProductRevisions(expectedId, expectedNumberRevisions, expectedCreator, Unit.ml, HibernateSaveEntityCreator.createCategory(null, creator)));

		compareProducts(actual, expected);
//		assertEquals(actual.getRevisions(), is(expected.getRevisions()));
		
		
		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}
	
	@Rollback(false)
	@Test
	public void saveProduct_shouldPersistProductWithOutDeletingOldRevisions_shouldReturnProductWithAllRevisions() throws Exception {
		_log.info("running test");
		Long id = 1L;
		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Integer revId = 3;
		Product productToSave = HibernateSaveEntityCreator.createProduct(id,
				HibernateSaveEntityCreator.createLocale(1),
				HibernateSaveEntityCreator.createProductRevision(id, revId,	creator, Unit.ml, HibernateSaveEntityCreator.createCategory(null, creator)));

		
		Product actual = _productService.save(productToSave);
		
		
		Long expectedId = 1L;
		Account expectedCreator = HibernateSaveEntityCreator.createAccount(1L);
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		Category category1 = HibernateSaveEntityCreator.createCategory(1L, null, "rootCategory", expectedCreator);
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, 1, "coke", expectedCreator, Unit.g, 100.0, category1, "www.urlToImage.com"));
		Category category2 = HibernateSaveEntityCreator.createCategory(1L, category1, "category1", expectedCreator);
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, 2, "original coke", expectedCreator, Unit.g, 100.0, category2, "www.differentUrlToImage.com"));
		Product expected = HibernateSaveEntityCreator.createProduct(expectedId,
				HibernateSaveEntityCreator.createLocale(1),
				revisions);

		compareProducts(actual, expected);
//		assertEquals(actual.getRevisions(), is(expected.getRevisions()));
		
		
		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}
	
	private static void compareProducts(Product actual, Product expected) {
//		if (actual == expected)
//			return;
//		if (expected == null) {
//			if(actual != null)
//				fail();
//		} 
//		assertEquals(actual.getId(), equalTo(expected.getId()));
//		
//		assertEquals(actual.getRevisions(), is(expected.getRevisions()));
	}

}
