package org.tagaprice.server.service;

import org.junit.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.UserAlreadyLoggedInException;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@ContextConfiguration
public class SessionServiceTest  extends AbstractJUnit4SpringContextTests {

	private SessionService _service;

	@Before
	public void SetUp() {
		_service = applicationContext.getBean("sessionService", SessionService.class);
	}

	@Test
	public void createSession_shouldCreateSession() throws Exception {
		Account account = EntityCreator.createAccount();

		Session actual = _service.createSession(account);

		assertThat(actual.getSessionId().length, greaterThan(0)); //TODO insert meaningful length here.
	}

	@Test(expected = UserAlreadyLoggedInException.class)
	public void createSessionTwice_shouldThrowException() throws Exception {
		Account account = EntityCreator.createAccount();

		_service.createSession(account);
		_service.createSession(account);
	}

	@Test
	public void getAccount_shouldGetAccount() throws Exception {
		//		Account account = new Account(1L, "test@mail.coom", new Date());
		//
		//		Session session = _service.createSession(account);
		//
		//		Account actual = _service.getAccount(session);
		//
		//		assertThat(actual, equalTo(account));
		fail();
	}

	@Test(expected = UserNotLoggedInException.class)
	public void getAccount_shouldThrowException() throws Exception {
		Session session = new Session("12345".getBytes());
		_service.getAccount(session);
	}
}
