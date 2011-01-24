package org.tagaprice.server.integration;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

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
import org.tagaprice.core.api.IProductService;
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
public class SaveProductTest extends AbstractTransactionalJUnit4SpringContextTests{ //TODO should not need to be transactional
	private static Logger _log = LoggerFactory.getLogger(SaveProductTest.class);
	private IDbTestInitializer _dbInitializer;
	private IProductService _productService;
	private SessionFactory _sessionFactory;
	
	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		_productService = applicationContext.getBean("defaultProductService", IProductService.class);
		
		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
		DbSaveAssertUtility.setSimpleJdbcTemplate(super.simpleJdbcTemplate);
	}
	
	@After
	public void tearDown() throws Exception {}
	
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
				HibernateSaveEntityCreator.createProductRevisions(expectedId, expectedNumberRevisions, expectedCreator, Unit.ml, HibernateSaveEntityCreator.createCategory(4L, creator)));

		compareProductsAndRevisions(actual, expected);
		
		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}
	
	@Rollback(false)
	@Test
	public void saveExistingProduct_shouldPersistProductWithOutDeletingOldRevisions_shouldReturnProductWithAllRevisions() throws Exception {
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
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, revId,	creator, Unit.ml, HibernateSaveEntityCreator.createCategory(null, creator)));
		Product expected = HibernateSaveEntityCreator.createProduct(expectedId,
				HibernateSaveEntityCreator.createLocale(1),
				revisions);

		compareProductsAndRevisions(actual, expected);
		
		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}
	
	private static void compareProductsAndRevisions(Product actual, Product expected) {
		assertEquals(actual, expected);
		
		compareProductRevisions(actual.getRevisions(), expected.getRevisions());
	}

	private static void compareProductRevisions(
			SortedSet<ProductRevision> actual,
			SortedSet<ProductRevision> expected) {
		assertEquals(actual, expected);
	}
}
