package org.tagaprice.client.desktopView;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.IUi;
import org.tagaprice.client.features.accountmanagement.login.LoginPresenter;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.ListReceiptsPlace;
import org.tagaprice.client.features.searchmanagement.SearchPlace;
import org.tagaprice.client.generics.events.DisplayLoginEvent;
import org.tagaprice.client.generics.events.DisplayLoginEventHandler;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.LoginChangeEventHandler;
import org.tagaprice.client.generics.widgets.InfoBox;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UIDesktop implements IUi {

	private VerticalPanel vePa1 = new VerticalPanel();
	private VerticalPanel iVePa = new VerticalPanel();
	private HorizontalPanel menu = new HorizontalPanel();
	private SimplePanel center = new SimplePanel();
	private HorizontalPanel bottom = new HorizontalPanel();
	private InfoBox _infoBox = new InfoBox();

	private ActivityManager _activityManager;
	private ClientFactory _clientFactory;
	private PopupPanel loginPop = new PopupPanel(false);
	
	private void init(){
		
		
		vePa1.setWidth("100%");
		vePa1.add(iVePa);

		iVePa.setStyleName("main");
		vePa1.setCellHorizontalAlignment(iVePa, HorizontalPanel.ALIGN_CENTER);
		
		//infobox
		_infoBox.setWidth("100%");
		_infoBox.getElement().getStyle().setZIndex(2000);
		
		
		
		//menu
		//menu.setSize("100%", "30px");
		menu.setStyleName("header");
		menu.setWidth("100%");
		iVePa.add(menu);
		//vePa1.setCellHorizontalAlignment(menu, HorizontalPanel.ALIGN_CENTER);
		
		
		//titel
		HTML _logo = new HTML("<a href=\"#\">&lt;Tag&gt;A&lt;/Price&gt;</a>");
		_logo.setStyleName("logo");
		menu.add(_logo);
		menu.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		menu.setCellWidth(_logo, "1%");
		
		
		//SearchText
		Label searchLink = new Label("Search");
		searchLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_clientFactory.getPlaceController().goTo(new SearchPlace());
				
			}
		});
		searchLink.setStyleName("login");
		menu.add(searchLink);
		
		//empty
		SimplePanel empty = new SimplePanel();
		empty.setWidth("100%");
		menu.add(empty);
		menu.setCellWidth(empty, "100%");
		
		
		//final Label add Receipt
		final Label addReceipt = new Label("add Receipt");
		addReceipt.setStyleName("login");
		menu.add(addReceipt);
		menu.setCellHorizontalAlignment(addReceipt, HorizontalPanel.ALIGN_RIGHT);
		menu.setCellWidth(addReceipt, "1%");
		addReceipt.setVisible(false);
		addReceipt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_clientFactory.getPlaceController().goTo(new CreateReceiptPlace());
			}
		});
		
		
		//login
		final Label login = new Label("Sign in");
		login.setStyleName("login");
		//Button login = new Button("Sign in");
		//$(login).as(gwtquery.plugins.ui.Ui.Ui).button(gwtquery.plugins.ui.widgets.Button.Options.create().icons(gwtquery.plugins.ui.widgets.Button.Icons.create().secondary("ui-icon-triangle-1-s"))); //
		menu.add(login);
		menu.setCellHorizontalAlignment(login, HorizontalPanel.ALIGN_RIGHT);
		menu.setCellWidth(login, "1%");
		
		final LoginPresenter loginPres = new LoginPresenter(_clientFactory);
		
		
		
		//create poptup
		Button close = new Button("x", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				loginPop.hide();
			}
		});
		AbsolutePanel aPop = new AbsolutePanel();
		aPop.add(loginPres.getView());
		aPop.add(close);
		aPop.setWidgetPosition(close, 0, 0);
		loginPop.setWidget(aPop);
		loginPop.getElement().getStyle().setZIndex(2000);
		loginPop.setGlassEnabled(true);
		loginPop.setAnimationEnabled(true);
		loginPop.setGlassStyleName("loginPopGlass");
		
		
		
	
		login.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if(_clientFactory.getAccountPersistor().isLoggedIn()){
					_clientFactory.getPlaceController().goTo(new ListReceiptsPlace());
				}else{
					
					//loginPop.showRelativeTo(login);	
					_clientFactory.getEventBus().fireEvent(new DisplayLoginEvent(true));
				}
							
			}
		});
		
		
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE , 
				new LoginChangeEventHandler() {
					
					@Override
					public void onLoginChange(LoginChangeEvent event) {
						if(_clientFactory.getAccountPersistor().isLoggedIn()){
							login.setText("Dashboard");
							addReceipt.setVisible(true);
						}else{
							login.setText("Sign in");
							addReceipt.setVisible(false);
						}
						
					}
				});
		
		
		
		
		
		//center
		center.setStyleName("center");
		center.setWidth("100%");
		iVePa.add(center);
		//vePa1.setCellHorizontalAlignment(center, VerticalPanel.ALIGN_CENTER);
		
		//center.setHeight((Window.getClientHeight()-240-120)+"px");
		//vePa1.setCellHeight(center, "100%");
		
		
		
		//bottom
		bottom.setStyleName("bottom");
		vePa1.add(bottom);
		
		//bottom Text
		HorizontalPanel bottomText = new HorizontalPanel();
		bottomText.setStyleName("bottom-text");
		HTML lefthtml = new HTML("" +
				"<h2>Licence</h2> " +
				"<a href=\"http://creativecommons.org/licenses/by-sa/3.0/\">Creative Commons Attribution-ShareAlike 3.0 Unported License</a> <br /> " +
				"<a href=\"http://www.gnu.org/licenses/agpl.html\">AGPLv3</a> " +
				"<h2>Blog</h2> " +
				"<a href=\"http://blog.tagaprice.org/\">blog.tagaprice.org</a> " +
				"<h2>Development</h2> " +
				"<a href=\"http://github.com/tagaprice\">api.tagaprice.org</a> <br /> " +
				"<a href=\"http://github.com/tagaprice\">http://github.com/tagaprice</a>");
		HTML righthtml = new HTML("" +
				"<h2>Email</h2> " +
				"team[at]tagaprice[dot]org " +
				"<h2>Twitter</h2> " +
				"<a href=\"http://twitter.com/tagaprice\">@tagaprice</a>");
		
		HTML labels = new HTML("" +
				"<h2>Labels</h2> " +
				"<a rel=\"license\" href=\"http://creativecommons.org/licenses/by-sa/3.0/\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"desktopView/cc.png\" /></a>" +
				"<br /><br /><a rel=\"license\" href=\"http://www.gnu.org/licenses/agpl.html\"><img alt=\"GNU Affero General Public License\" style=\"border-width:0\" src=\"desktopView/agplv3.png\" /></a>" +
				"<br /><br /><a href=\"http://www.w3.org/html/logo/\"><img src=\"desktopView/html5.png\" alt=\"HTML5 Powered with CSS3 / Styling, Device Access, and Semantics\" title=\"HTML5 Powered with CSS3 / Styling, Device Access, and Semantics\"></a>");
		
		bottomText.add(lefthtml);
		bottomText.add(righthtml);
		bottomText.add(labels);
		bottom.add(bottomText);
		bottom.setCellHorizontalAlignment(bottomText, HorizontalPanel.ALIGN_CENTER);
				
		
				
	
		
		
	
		//Set Popvisilb
		
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {
			@Override
			public void onLoginChange(LoginChangeEvent event) {
				if(event.isLoggedIn())
					loginPop.hide();
			}
		});
		
		
		//ShopLogin
		_clientFactory.getEventBus().addHandler(DisplayLoginEvent.TYPE, new DisplayLoginEventHandler() {
			
			@Override
			public void onDisplayLogin(DisplayLoginEvent event) {
				if(event.isShow()){
					Log.debug("Pop Login");

					loginPop.center();
					loginPop.show();
				}
			}
		});


		_activityManager.setDisplay(center);


	}


	@Override
	public void initUI(ActivityManager activityManager, ClientFactory clientFactory) {
		_activityManager=activityManager;
		_clientFactory=clientFactory;
		init();

		RootPanel.get().add(vePa1);
		RootPanel.get().add(_infoBox,0,0);
	}


	@Override
	public InfoBox getInfoBox() {
		return _infoBox;
	}

	
	
}

