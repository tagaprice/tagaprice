package org.tagaprice.server.service;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.api.WrongEmailOrPasswordException;
import org.tagaprice.server.dao.helper.EntityCreator;
import org.tagaprice.server.dao.interfaces.ILocalAccountDAO;
import static org.mockito.Mockito.*;



@ContextConfiguration
public class DefaultLoginServiceTest  extends AbstractJUnit4SpringContextTests {
	private DefaultLoginService _loginService;
	private ILocalAccountDAO _localAccountDaoMock;

	@BeforeClass
	public static void setUpBefore() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_loginService = applicationContext.getBean("defaultLoginService", DefaultLoginService.class); //maybe replace this with autowire
		_localAccountDaoMock = mock(ILocalAccountDAO.class);
		_loginService.setLocalAccountDAO(_localAccountDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
	}

	@After
	public void tearDown() throws Exception {}

	@Test(expected=WrongEmailOrPasswordException.class)
	public void login_shouldThrowWrongEmailOrPasswordException() throws ServerException {
		String email = "user@mail.com";
		String password = "12345";

		when(_localAccountDaoMock.getByEmailAndPassword(email, password)).thenThrow(new WrongEmailOrPasswordException("no such user"));


		_loginService.login(email, password);
	}

	//	TODO test on useralreadyloggedinexception
	//	@Test(expected=WrongEmailOrPasswordException.class)
	//	public void login_shouldThrowWrongEmailOrPasswordException() throws ServerException {
	//		String email = "user@mail.com";
	//		String password = "12345";
	//
	//		when(_localAccountDaoMock.getByEmailAndPassword(email, password)).thenThrow(new WrongEmailOrPasswordException("no such user"));
	//
	//
	//		_loginService.login(email, password);
	//	}

	public void login_shouldReturnSession() throws ServerException {
		String email = "user@mail.com";
		String password = "12345";

		when(_localAccountDaoMock.getByEmailAndPassword(email, password)).thenReturn(EntityCreator.createLocalAccount(email));


		//Session session = _loginService.login(email, password);


	}

}
