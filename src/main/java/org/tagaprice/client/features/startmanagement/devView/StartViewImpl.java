package org.tagaprice.client.features.startmanagement.devView;


import org.tagaprice.client.features.startmanagement.IStartView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class StartViewImpl extends Composite implements IStartView {

	private Presenter _presenter;

	public StartViewImpl() {
		initWidget(new Label("Welcome to TagAPrice (beta)"));
	}



	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

}
