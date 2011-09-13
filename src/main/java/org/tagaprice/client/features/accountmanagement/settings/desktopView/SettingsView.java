package org.tagaprice.client.features.accountmanagement.settings.desktopView;

import org.tagaprice.client.features.accountmanagement.settings.ISettingsView;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget.MENUPOINT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SettingsView extends Composite implements ISettingsView {

	private Presenter _presenter;
	private StdFrame _frame = new StdFrame();
	private Label _header = new Label("Dashboad / Settings");
	private DashboardMenuWidget _menu = new DashboardMenuWidget();
	private VerticalPanel _vePa = new VerticalPanel();
	private PasswordTextBox _newPassword = new PasswordTextBox();
	private PasswordTextBox _confirmPassword = new PasswordTextBox();
	private Button _changePw = new Button("Save new password");
	
	public SettingsView() {
		_frame.setHeader(_header);
		
		//menu
		_frame.setBody(_menu, "200px");
		_menu.setActiveType(MENUPOINT.SETTINGS);
		
		//Settings
		_frame.setBody(_vePa);
		
		
		//change password
		Label _settingHead = new Label("Change Password");
		_settingHead.setStyleName("propertyHeader");
		_vePa.add(_settingHead);
		
		Grid cpGrid = new Grid(2,2);
		cpGrid.setStyleName("propertyGrid");
		
		_newPassword.setStyleName("morphWidget edit");
		cpGrid.setWidget(0, 0, new Label("New Password"));
		cpGrid.setWidget(0, 1, _newPassword);
		
		_confirmPassword.setStyleName("morphWidget edit");
		cpGrid.setWidget(1, 0, new Label("Confirm Password"));
		cpGrid.setWidget(1, 1, _confirmPassword);
		
		_vePa.add(cpGrid);
		
		_vePa.add(_changePw);
		_changePw.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onPasswordChange();
			}
		});
		
		
		initWidget(_frame);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

	@Override
	public String getNewPassword() {
		return _newPassword.getText();
	}

	@Override
	public String getConfirmPassword() {
		return _confirmPassword.getText();
	}
}
