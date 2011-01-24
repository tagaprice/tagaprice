package org.tagaprice.client.gwt.shared.rpc.accountmanagement;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ILoginServiceAsync {

	void setLogin(String email, String password, AsyncCallback<String> callback);
	void setLogout(AsyncCallback<Boolean> callback);
	void isLoggedIn(AsyncCallback<String> callback);
}
