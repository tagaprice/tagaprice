package org.tagaprice.server.dao.interfaces;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This class injects all DI-containers needed for DAO-testing.
 * Extend the {@link ContextConfiguration} annotation to include more/different DI-containers
 * 
 * @author haja
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({})
//@ContextConfiguration(locations={"spring/test-beans.xml"})
public abstract class AbstractDAOTest {
	//	private static ApplicationContext s_context = new FileSystemXmlApplicationContext("/src/test/resources/spring/test-beans.xml");
	//
	//	private void setApplicationContext(ApplicationContext context) {
	//		s_context = context;
	//	}
	//
	//	protected ApplicationContext getApplicationContext() {
	//		return AbstractDAOTest.s_context;
	//	}
}
