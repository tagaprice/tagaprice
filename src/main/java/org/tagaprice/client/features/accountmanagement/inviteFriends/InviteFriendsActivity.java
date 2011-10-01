package org.tagaprice.client.features.accountmanagement.inviteFriends;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.inviteFriends.IInviteFriendsView.Presenter;
import org.tagaprice.client.features.startmanagement.StartPlace;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
		
		
		
		if(_clientFactory.getAccountPersistor().getUser()!=null &&
				_clientFactory.getAccountPersistor().getUser().getProperty("inviteCount")!=null)
			_view.setInviteCount((Long)_clientFactory.getAccountPersistor().getUser().getProperty("inviteCount"));
		
		
		panel.setWidget(_view);
	}


	@Override
	public void onLogout() {
		_clientFactory.getAccountPersistor().logout();
		goTo(new StartPlace());		
	}


	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);	
		
	}


	@Override
	public void onSendInvitation() {
		if(_view.getInviteMailAddress()!=null && _view.getInviteMailAddress().toLowerCase().trim().matches(".+@.+\\.[a-z][a-z]+")){
			_clientFactory.getLoginService().sendInviteToFriend(_view.getInviteMailAddress(),new AsyncCallback<Long>() {
				
				@Override
				public void onSuccess(Long response) {
					_view.setInviteCount(response);
					
					_clientFactory.getAccountPersistor().getUser().setProperty("inviteCount",response);
					if(response>0)
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(InviteFriendsActivity.class, "Thx. You have successfully sent the invitation!", INFOTYPE.SUCCESS));
					else
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(InviteFriendsActivity.class, "Sorry. But you have no more invitations.", INFOTYPE.INFO));

				}
				
				@Override
				public void onFailure(Throwable caught) {
					try {
						throw caught;
					} catch (UserNotLoggedInException e) {
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(InviteFriendsActivity.class, "User Not Logged In", INFOTYPE.ERROR,0));
					} catch (Throwable e) {
						Log.error("Unexpected error: " + e);
					}
					
				}
			});
		}
		
	}

}
