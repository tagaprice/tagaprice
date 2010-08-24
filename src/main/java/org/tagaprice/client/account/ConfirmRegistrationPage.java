package org.tagaprice.client.account;

import org.tagaprice.client.InfoBoxComposite;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.client.TitlePanel.Level;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfirmRegistrationPage extends InfoBoxComposite{

	VerticalPanel vePa1 = new VerticalPanel();
	
	public ConfirmRegistrationPage() {
		_init();
		// TODO Auto-generated constructor stub		
		vePa1.add(new TitlePanel("Confirm", new Label("Please check your email account and follow the confirm link to finish the registration!"), Level.H2));
		
	}
	
	public ConfirmRegistrationPage(String confirm) {
		_init();
		// TODO Auto-generated constructor stub
		vePa1.add(new Label("Confirm: "+confirm));
	}
	
	
	private void _init(){
		vePa1.setWidth("100%");
		init(vePa1);
	}
	
	
	
	
	
	
}
