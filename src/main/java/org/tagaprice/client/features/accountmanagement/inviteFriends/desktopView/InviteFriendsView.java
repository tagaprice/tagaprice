package org.tagaprice.client.features.accountmanagement.inviteFriends.desktopView;

import org.tagaprice.client.features.accountmanagement.inviteFriends.IInviteFriendsView;
import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget.MENUPOINT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InviteFriendsView extends Composite implements IInviteFriendsView {

	private Label _header = new Label("Dashboad / Invite Friends");
	private StdFrame _frame = new StdFrame();
	private DashboardMenuWidget _menu = new DashboardMenuWidget();
	private VerticalPanel _vePa = new VerticalPanel();
	private MorphWidget _inviationMail = new MorphWidget();
	private Presenter _presenter;
	private Label inviteCount = new Label("You have 0 invitations left");
	
	public InviteFriendsView() {
		_frame.setHeader(_header);		
		
		//menu
		_frame.setBody(_menu, "200px");
		_menu.setActiveType(MENUPOINT.INVITEFRIENDS);
		_menu.addLogoutClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onLogout();
				
			}
		});
		
		//Invitiation
		_vePa.add(inviteCount);
		_inviationMail.setReadOnly(false);
		
		Label inviteText = new Label("Invite friend via email");
		inviteText.setStyleName("propertyHeader");
		_vePa.add(inviteText);
		
		
		//email
		_vePa.add(new Label("Email"));
		_vePa.add(_inviationMail);
		
		
		//send
		_vePa.add(new Button("Send invitation", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSendInvitation();
			}
		}));
		
		_frame.setBody(_vePa);
		
		initWidget(_frame);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

	@Override
	public void setInviteCount(long count) {
		inviteCount.setText("You have "+count+" invitations left");
		
	}

	@Override
	public String getInviteMailAddress() {
		return _inviationMail.getValue();
	}

}
