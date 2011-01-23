package org.tagaprice.server.service;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.entities.Category;
import org.tagaprice.server.dao.hibernate.HibernateCategoryDAO;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;



@ContextConfiguration
public class DefaultCategoryServiceTest  extends AbstractJUnit4SpringContextTests {
	private DefaultCategoryService _categoryService;
	private HibernateCategoryDAO _categoryDaoMock;
	
	private static Logger _log = LoggerFactory.getLogger(DefaultCategoryServiceTest.class);

	@BeforeClass
	public static void setUpBefore() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_categoryService = applicationContext.getBean("defaultCategoryService", DefaultCategoryService.class); //maybe replace this with autowire
		_categoryDaoMock = mock(HibernateCategoryDAO.class);
		_categoryService.setCategoryDAO(_categoryDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void getAll_shouldReturnAllCategoriesAsGivenByDAO() throws Exception {
		_log.info("running test");
		Long creatorId = 1L;
		
		List<Category> storedCategories = new ArrayList<Category>();
		storedCategories.add(EntityCreator.createCategory(1L, creatorId));
		storedCategories.add(EntityCreator.createCategory(2L, creatorId));
		storedCategories.add(EntityCreator.createCategory(3L, creatorId));
		
		when(_categoryDaoMock.getAll()).thenReturn(storedCategories);
		
		List<Category> actual = _categoryService.getAll();

		List<Category> expected = new ArrayList<Category>();
		expected.add(EntityCreator.createCategory(1L, creatorId));
		expected.add(EntityCreator.createCategory(2L, creatorId));
		expected.add(EntityCreator.createCategory(3L, creatorId));
		
		assertThat(actual, is(expected));
		
	}
}
