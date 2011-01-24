package org.tagaprice.client.gwt.shared.rpc.accountmanagement;

import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.api.WrongEmailOrPasswordException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ILoginServiceAsync {

	void setLogin(String email, String password, AsyncCallback<String> callback) throws WrongEmailOrPasswordException;
	void setLogout(AsyncCallback<Void> callback)  throws UserNotLoggedInException;
	void isLoggedIn(AsyncCallback<String> callback);
}
