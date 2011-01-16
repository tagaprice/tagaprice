package org.tagaprice.client.gwt.client.features.accountmanagement.login.devView;

import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILoginView;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class LoginViewImpl extends Composite implements ILoginView {

	private Presenter _presenter;

	public LoginViewImpl() {
		initWidget(new Label("Please implent this!!!"));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

}
