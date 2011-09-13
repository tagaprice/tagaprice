package org.tagaprice.client.features.accountmanagement.settings;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ISettingsView extends IsWidget {

	
	public void setPresenter(Presenter presenter);
	
	public String getNewPassword();
	
	public String getConfirmPassword();
	
	public interface Presenter{
		
		public void goTo(Place place);
		
		public void onPasswordChange();
	}
}
