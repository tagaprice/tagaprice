package org.tagaprice.server.integration;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.IProductService;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Unit;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.dao.helper.ProductComparator;

@ContextConfiguration
public class ProductManagementTest extends AbstractJUnit4SpringContextTests{
	private static Logger _log = LoggerFactory.getLogger(ProductManagementTest.class);
	private IDbTestInitializer _dbInitializer;
	private IProductService _productService;
	@SuppressWarnings("unused")
	private SessionFactory _sessionFactory;
	
	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		_productService = applicationContext.getBean("defaultProductService", IProductService.class);
		
		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
		DbSaveAssertUtility.setDataSource(applicationContext.getBean(DataSource.class));
	}
	
	@After
	public void tearDown() throws Exception {}
	
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

		ProductComparator.compareProductsAndRevisions(actual, expected);
		
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}
	
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

		ProductComparator.compareProductsAndRevisions(actual, expected);
		
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}
	
	@Test
	public void saveExistingProduct_productHasCategoryWithParentCategoryAlreadyExistingInOriginalProduct_shouldPersistProductWithOutDeletingOldRevisions_shouldReturnProductWithAllRevisions() throws Exception {
		_log.info("running test");
		Long id = 1L;
		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Integer revId = 3;
		Product productToSave = HibernateSaveEntityCreator.createProduct(id,
				HibernateSaveEntityCreator.createLocale(1),
				HibernateSaveEntityCreator.createProductRevision(id, revId,	creator, Unit.ml, HibernateSaveEntityCreator.createCategory(
						3L, 
						HibernateSaveEntityCreator.createCategory(1L, null, "rootCategory", creator), "category2", creator)));

		
		Product actual = _productService.save(productToSave);
		
		
		Long expectedId = 1L;
		Account expectedCreator = HibernateSaveEntityCreator.createAccount(1L);
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		Category category1 = HibernateSaveEntityCreator.createCategory(1L, null, "rootCategory", expectedCreator);
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, 1, "coke", expectedCreator, Unit.g, 100.0, category1, "www.urlToImage.com"));
		Category category2 = HibernateSaveEntityCreator.createCategory(1L, category1, "category1", expectedCreator);
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, 2, "original coke", expectedCreator, Unit.g, 100.0, category2, "www.differentUrlToImage.com"));
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, revId,	creator, Unit.ml, HibernateSaveEntityCreator.createCategory(
				3L, 
				HibernateSaveEntityCreator.createCategory(1L, null, "rootCategory", creator), "category2", creator)));
		
		Product expected = HibernateSaveEntityCreator.createProduct(expectedId,
				HibernateSaveEntityCreator.createLocale(1),
				revisions);

		ProductComparator.compareProductsAndRevisions(actual, expected);
		
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}
}
