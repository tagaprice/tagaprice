package org.tagaprice.client.account;

import org.tagaprice.client.TitlePanel;
import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.TitlePanel.Level;
import org.tagaprice.client.pages.Page;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.rpc.LocalAccountHandler;
import org.tagaprice.shared.rpc.LocalAccountHandlerAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class ConfirmRegistrationPage extends Page{

	SimplePanel siPa1 = new SimplePanel();
	private LocalAccountHandlerAsync userHandler = GWT.create(LocalAccountHandler.class);
	
	public ConfirmRegistrationPage() {
		_init();
		// TODO Auto-generated constructor stub		
		siPa1.setWidget(new TitlePanel("Confirm", new Label("Please check your email account and follow the confirm link in the next 24h to finish the registration!"), Level.H2));
		
	}
	
	public ConfirmRegistrationPage(String confirm) {
		_init();
		siPa1.setWidget(new Label("Please wait while we check you confirmation!"));
		userHandler.confirm(confirm, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					siPa1.setWidget(new Label("Thank you for joining TagAPrice"));
				}else{
					siPa1.setWidget(new Label("Ubs! Problem at registration!"));

				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				showInfo("Problem at confirming your email!", BoxType.WARNINGBOX);				
			}
		});
		
		
		
	}
	
	
	private void _init(){
		siPa1.setWidth("100%");
		init(siPa1);
	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
