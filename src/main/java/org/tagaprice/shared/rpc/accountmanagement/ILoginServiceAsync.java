package org.tagaprice.shared.rpc.accountmanagement;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ILoginServiceAsync {

	void setLogin(String email, String password, AsyncCallback<String> callback);
	void setLogout(AsyncCallback<Void> callback);
	void isLoggedIn(AsyncCallback<String> callback);
	void isEmailAvailable(String email, AsyncCallback<Boolean> callback);
	void setNewPassword(String oldPassword, String newPassword, String newPassword2, AsyncCallback<Boolean> callback);
	void registerUser(String email, String password, boolean agreeTerms, AsyncCallback<String> callback);
}
