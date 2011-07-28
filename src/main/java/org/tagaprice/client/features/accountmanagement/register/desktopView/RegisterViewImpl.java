package org.tagaprice.client.features.accountmanagement.register.desktopView;

import org.tagaprice.client.features.accountmanagement.register.IRegisterView;
import org.tagaprice.client.generics.widgets.StdFrame;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class RegisterViewImpl extends Composite implements IRegisterView {
	private Presenter _presenter;

	private TextBox _email = new TextBox();
	private PasswordTextBox _password = new PasswordTextBox();
	private CheckBox _terms = new CheckBox();
	private Button _signUpButton = new Button("Sign Up");
	private StdFrame _frame = new StdFrame();
	private Label title = new Label("Sign Up");
	private Grid loginGrid = new Grid(3, 2);
	private VerticalPanel vePa1 = new VerticalPanel();
	
	public RegisterViewImpl() {
		initWidget(_frame);
		_frame.setHeader(title);
		_frame.setBody(vePa1);
		
		loginGrid.setStyleName("stdgrid");
		
		//design
		loginGrid.getCellFormatter().setStyleName(0, 0, "namecell");
		loginGrid.getCellFormatter().setStyleName(1, 0, "namecell");
		loginGrid.getCellFormatter().setStyleName(2, 0, "namecell");
		
		vePa1.add(loginGrid);
		//email
		loginGrid.setWidget(0, 0, new Label("Email"));
		loginGrid.setWidget(0, 1, _email);
		
		//password
		loginGrid.setWidget(1, 0, new Label("Password"));
		loginGrid.setWidget(1, 1, _password);
		
		//terms and conditions
		loginGrid.setWidget(2, 0, new HTML("Agree <a href=\"\">terms</a>"));
		loginGrid.setWidget(2, 1, _terms);
		
		//sign up button
		vePa1.add(_signUpButton);
		_signUpButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onRegisterButtonEvent();				
			}
		});
		
	}


	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	@Override
	public String getEmail() {
		return _email.getText();
	}

	@Override
	public void setEmailIsFree(boolean insFree) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPassword() {
		return _password.getText();
	}


	@Override
	public boolean getAgreeTerms() {
		return _terms.getValue();
	}


}
