package org.tagaprice.server.service;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.api.UserAlreadyLoggedInException;
import org.tagaprice.core.api.WrongEmailOrPasswordException;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.dao.interfaces.IAccountDAO;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;



@ContextConfiguration
public class DefaultLoginServiceTest  extends AbstractJUnit4SpringContextTests {
	private DefaultLoginService _loginService;
	private IAccountDAO _localAccountDaoMock;
	private SessionService _sessionFactoryMock;

	@BeforeClass
	public static void setUpBefore() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_loginService = applicationContext.getBean("defaultLoginService", DefaultLoginService.class); //maybe replace this with autowire
		_localAccountDaoMock = mock(IAccountDAO.class);
		_loginService.setLocalAccountDAO(_localAccountDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help

		_sessionFactoryMock = mock(SessionService.class);
		_loginService.setSessionFactory(_sessionFactoryMock);

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

	@Test(expected=UserAlreadyLoggedInException.class)
	public void login_shouldThrowUserAlreadyLoggedInException() throws ServerException {
		String email = "user@mail.com";
		String password = "12345";

		when(_localAccountDaoMock.getByEmailAndPassword(email, password)).thenReturn(EntityCreator.createAccount(email));
		when(_sessionFactoryMock.createSession(EntityCreator.createAccount(email))).thenThrow(new UserAlreadyLoggedInException("User already logged in."));

		_loginService.login(email, password);
	}

	@Test
	public void login_shouldReturnSession() throws ServerException {
		String email = "user@mail.com";
		String password = "12345";

		when(_localAccountDaoMock.getByEmailAndPassword(email, password)).thenReturn(EntityCreator.createAccount(email));
		when(_sessionFactoryMock.createSession(EntityCreator.createAccount(email))).thenReturn(EntityCreator.createSession());

		Session session = _loginService.login(email, password);

		assertThat(session, is(EntityCreator.createSession()));
	}

}
