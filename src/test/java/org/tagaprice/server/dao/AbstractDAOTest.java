package org.tagaprice.server.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public abstract class AbstractDAOTest {
	private static ApplicationContext s_context = new FileSystemXmlApplicationContext("/src/test/resources/test-beans.xml");
	
	protected ApplicationContext getContext() {
		return AbstractDAOTest.s_context;
	}
}
