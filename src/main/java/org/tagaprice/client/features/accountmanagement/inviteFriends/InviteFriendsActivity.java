package org.tagaprice.client.features.accountmanagement.inviteFriends;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.inviteFriends.IInviteFriendsView.Presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class InviteFriendsActivity implements Activity, Presenter {

	private ClientFactory _clientFactory;
	private InviteFriendsPlace _place;
	private IInviteFriendsView _view;
	
	public InviteFriendsActivity(InviteFriendsPlace place, ClientFactory clientFactory) {
		_clientFactory=clientFactory;
		_place=place;
	}
	
	
	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Log.debug("activity startet");
		Window.setTitle("TagAPrice - InviteFriends");
		
		_view = _clientFactory.getInivteFriendsView();
		_view.setPresenter(this);
		
		panel.setWidget(_view);
	}

}
