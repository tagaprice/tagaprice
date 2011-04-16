package org.tagaprice.client.features.accountmanagement.register.devView;

import org.tagaprice.client.features.accountmanagement.register.IRegisteredView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class RegisteredViewImpl extends Composite implements IRegisteredView {

	private Label _thxText = new Label("Thanks for registation.");

	public RegisteredViewImpl() {
		initWidget(_thxText);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub

	}


}
