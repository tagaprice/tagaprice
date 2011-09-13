package org.tagaprice.client.features.accountmanagement.settings.desktopView;

import org.tagaprice.client.features.accountmanagement.settings.ISettingsView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class SettingsView extends Composite implements ISettingsView {

	private Presenter _presenter;
	
	public SettingsView() {
		initWidget(new Label("setting"));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}
}
