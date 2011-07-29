package org.tagaprice.client.desktopView;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.IUi;
import org.tagaprice.client.features.accountmanagement.login.LoginPresenter;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.LoginChangeEventHandler;
import org.tagaprice.client.generics.widgets.InfoBox;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UIDesktop implements IUi {

	private VerticalPanel vePa1 = new VerticalPanel();
	private HorizontalPanel menu = new HorizontalPanel();
	private SimplePanel center = new SimplePanel();
	private HorizontalPanel bottom = new HorizontalPanel();
	private InfoBox _infoBox = new InfoBox();
	
	private TextBox search = new TextBox();
	private PopupPanel _infoBoxPopUp = new PopupPanel();
	
	private ActivityManager _activityManager;
	private ClientFactory _clientFactory;

	private PopupPanel loginPop = new PopupPanel(true);

	private void init(){
		{
		vePa1.setWidth("100%");
		
		//infobox
		//Add InfoBox Popup
		_infoBoxPopUp.setWidget(_infoBox);
		_infoBoxPopUp.show();
		


		//INfo test
		//TODO Find out why setWidth(100%) is not working
		_infoBox.setWidth((Window.getClientWidth()-20)+"px");
		
		
		
		
		//menu
		//menu.setSize("100%", "30px");
		menu.setStyleName("header");
		vePa1.add(menu);
		
		//search
		SimplePanel nothing = new SimplePanel();
		menu.add(nothing);
		menu.setCellWidth(nothing, "50%");
		
		search.setText("Search...");
		menu.add(search);
		menu.setCellHorizontalAlignment(search, HorizontalPanel.ALIGN_CENTER);
		
		
		//position
		Image position = new Image("desktopView/location.png");
		position.setStyleName("location");
		menu.add(position);
		
		//login
		final Label login = new Label("Sign in");
		login.setStyleName("login");
		//Button login = new Button("Sign in");
		//$(login).as(gwtquery.plugins.ui.Ui.Ui).button(gwtquery.plugins.ui.widgets.Button.Options.create().icons(gwtquery.plugins.ui.widgets.Button.Icons.create().secondary("ui-icon-triangle-1-s"))); //
		menu.add(login);
		menu.setCellHorizontalAlignment(login, HorizontalPanel.ALIGN_RIGHT);
		menu.setCellWidth(login, "50%");
		
		login.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				LoginPresenter loginPres = new LoginPresenter(_clientFactory);
				loginPop.setWidget(loginPres.getView());
				loginPop.showRelativeTo(login);				
			}
		});
		
		
		
		
		
		//center
		center.setStyleName("center");
		vePa1.add(center);
		vePa1.setCellHorizontalAlignment(center, VerticalPanel.ALIGN_CENTER);
		
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
		bottomText.add(lefthtml);
		bottomText.add(righthtml);
		bottom.add(bottomText);
		bottom.setCellHorizontalAlignment(bottomText, HorizontalPanel.ALIGN_CENTER);
				
		
		//add your stdlinks for debug
		bottomText.add(new HTML(
				"<h2>Debug</h2> " + 
				"<a href=\"#Start:null\">home</a> <br /> "
				+ "<a href=\"#CreateProduct:/create\">New Product</a> <br /> "
				+ "<a href=\"#ListProducts:/show\">List Products</a> <br /> "
				+ "<a href=\"#CreateShop:/create\">New Shop</a> <br /> "
				+ "<a href=\"#ListShops:\">List Shops</a> <br /> "
				+ "<a href=\"#CreateReceipt:/create\">New Receipt</a> <br /> "
				+ "<a href=\"#ListReceipts:/show\">List Receipts</a> <br /> "
				+ "<a href=\"#Register:/REGISTER\">Sign Up</a> <br /> "));
	}
		
		
	
		//Set Popvisilb
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {
			@Override
			public void onLoginChange(LoginChangeEvent event) {
				loginPop.hide();
			}
		});


		_activityManager.setDisplay(center);


		


		//User loggedInHandler
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {

			@Override
			public void onLoginChange(LoginChangeEvent event) {
				//TODO Set SignIn invisible and add User plus name and so on.
				/*
				if(event.isLoggedIn()){
					login.setVisible(false);
					register.setVisible(false);
					logout.setVisible(true);
				}else{
					login.setVisible(true);
					register.setVisible(true);
					logout.setVisible(false);
				}*/

			}
		});
	}


	@Override
	public void initUI(ActivityManager activityManager, ClientFactory clientFactory) {
		_activityManager=activityManager;
		_clientFactory=clientFactory;
		init();

		RootPanel.get().add(vePa1);

	}


	@Override
	public InfoBox getInfoBox() {
		return _infoBox;
	}
}
