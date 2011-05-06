package org.tagaprice.client.features;

import org.junit.Test;
import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.IAccountPersistor;
import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.features.accountmanagement.login.LoginActivity;
import org.tagaprice.client.features.accountmanagement.login.LoginPlace;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

public class LoginActivityTest extends TestCase {

	@Test
	public void test_onLoginEvent(){



		LoginPlace place = new LoginPlace("1234567899");
		ClientFactory clientFactory = createMock(ClientFactory.class);

		IAccountPersistor ia = createMock(IAccountPersistor.class);
		expect(clientFactory.getAccountPersistor()).andReturn(ia).times(2);
		expect(ia.isLoggedIn()).andReturn(false);
		ia.login((String)anyObject(), (String)anyObject());
		replay(ia);

		ILoginView loginView = createMock(ILoginView.class);
		expect(clientFactory.getLoginView()).andReturn(loginView);
		expect(loginView.getPassword()).andReturn("1234").times(2);
		expect(loginView.getEmail()).andReturn("a@a.a").times(2);

		loginView.setPresenter((ILoginView.Presenter)anyObject());


		replay(loginView);
		replay(clientFactory);



		LoginActivity activity = new LoginActivity(place, clientFactory);
		AcceptsOneWidget widget = createMock(AcceptsOneWidget.class);
		activity.start(widget, null);


		activity.onLoginEvent();
		verify(clientFactory);

		System.out.println("bla");
		//assertTrue(true);
	}
}
