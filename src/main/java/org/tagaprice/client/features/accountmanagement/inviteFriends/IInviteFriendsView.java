package org.tagaprice.client.features.accountmanagement.inviteFriends;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface IInviteFriendsView extends IsWidget {

	public void setPresenter(Presenter presenter);
	
	public void setInviteCount(long count);
	
	public String getInviteMailAddress();
	
	public interface Presenter{
		
		public void goTo(Place place);
		
		public void onLogout();
		
		public void onSendInvitation();
	}
}
