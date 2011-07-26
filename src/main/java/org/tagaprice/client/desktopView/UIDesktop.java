package org.tagaprice.client.desktopView;

import gwtquery.plugins.ui.widgets.Button.Icons;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.IUi;
import org.tagaprice.client.features.accountmanagement.login.LoginPresenter;
import org.tagaprice.client.generics.I18N;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.LoginChangeEventHandler;
import org.tagaprice.client.generics.events.WaitForAddressEvent;
import org.tagaprice.client.generics.widgets.InfoBox;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import static com.google.gwt.query.client.GQuery.*;

public class UIDesktop implements IUi {

	private VerticalPanel vePa1 = new VerticalPanel();
	private HorizontalPanel menu = new HorizontalPanel();
	private SimplePanel center = new SimplePanel();
	private HorizontalPanel bottom = new HorizontalPanel();
	
	
	private PopupPanel _infoBoxPopUp = new PopupPanel();
	//private HorizontalPanel topPanel = new HorizontalPanel();
	private HorizontalPanel leftPanel = new HorizontalPanel();
	private SimplePanel mainPanel = new SimplePanel();
	private InfoBox _infoBox = new InfoBox();
	private DockLayoutPanel completeScreen = new DockLayoutPanel(Unit.PX);

	ActivityManager _activityManager;
	ClientFactory _clientFactory;

	private PopupPanel loginPop = new PopupPanel(true);

	private void init(){
		{
		vePa1.setWidth("100%");
		
		//menu
		//menu.setSize("100%", "30px");
		menu.setStyleName("header");
		vePa1.add(menu);
		
		//login
		final Label login = new Label("Sign in");
		login.setStyleName("login");
		//Button login = new Button("Sign in");
		//$(login).as(gwtquery.plugins.ui.Ui.Ui).button(gwtquery.plugins.ui.widgets.Button.Options.create().icons(gwtquery.plugins.ui.widgets.Button.Icons.create().secondary("ui-icon-triangle-1-s"))); //
		menu.add(login);
		menu.setCellHorizontalAlignment(login, HorizontalPanel.ALIGN_RIGHT);
		
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
				"Data License: Creative Commons Attribution-ShareAlike 3.0 Unported License <br /> " +
				"Code License: AGPLv3 " +
				"<h2>Blog</h2> " +
				"blog.tagaprice.org " +
				"<h2>Development</h2> " +
				"api.tagaprice.org <br /> " +
				"github.com/tagaprice");
		HTML righthtml = new HTML("" +
				"<h2>Email</h2> " +
				"team[at]tagaprice[dot]org " +
				"<h2>Twitter</h2> " +
				"@tagaprice");
		bottomText.add(lefthtml);
		bottomText.add(righthtml);
		bottom.add(bottomText);
		bottom.setCellHorizontalAlignment(bottomText, HorizontalPanel.ALIGN_CENTER);
				
	}
		
		
		
		
		
		
		//Widget divLogger = Log.getLogger(DivLogger.class).getWidget();
		//LAYOUT
		//completeScreen.addSouth(divLogger, 120);
		//completeScreen.addNorth(this.topPanel, 80);
		completeScreen.addNorth(this.leftPanel,30);
		completeScreen.add(this.mainPanel);

		//Configure Logo
		//this.topPanel.add(new Image("TagaAPriceLogo.png"));
		//this.topPanel.add(new HTML("<h1>TagAPrice</h1>"));
		//This is quite a mess...

		this.leftPanel.add(new HTML("<h3>"+I18N.I18N.testmenu()+"</h3>"));

		/******************** Product Links *****************/
		this.leftPanel.add(new Button("Locate", new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				_clientFactory.getEventBus().fireEvent(new WaitForAddressEvent());
			}
		}));


		this.leftPanel.add(new HTML("<hr />"));
		Label createProduct = new Label("Create Product2");
		createProduct.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateProduct:/create");}});

		Label getProduct = new Label("List Products");
		getProduct.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("ListProducts:/show");}});


		this.leftPanel.add(createProduct);
		this.leftPanel.add(getProduct);


		/******************** Login Links *****************/
		this.leftPanel.add(new HTML("<hr />"));
		final Label login = new Label("Login");
		/*login.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("LogInOut:/login");}});
		 */
		login.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				LoginPresenter loginPres = new LoginPresenter(_clientFactory);
				loginPop.setWidget(loginPres.getView());
				loginPop.showRelativeTo(login);
			}
		});
		this.leftPanel.add(login);

		final Label logout = new Label("Logout");/*
		logout.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("LogInOut:/logout");}});
		 */
		logout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				LoginPresenter loginPres = new LoginPresenter(_clientFactory);
				loginPop.setWidget(loginPres.getView());
				loginPop.showRelativeTo(logout);
			}
		});
		this.leftPanel.add(logout);
		logout.setVisible(false);

		//Set Popvisilb
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {
			@Override
			public void onLoginChange(LoginChangeEvent event) {
				loginPop.hide();
			}
		});


		final Label register = new Label("Register");
		register.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("Register:/REGISTER");}});
		this.leftPanel.add(register);

		/******************** Shop Links ******************/
		this.leftPanel.add(new HTML("<hr />"));

		Label createShop = new Label("Create Shop");
		createShop.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateShop:/create");}});

		Label getShop = new Label("list Shops");
		getShop.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("ListShops:/show");}});


		this.leftPanel.add(createShop);
		this.leftPanel.add(getShop);

		/******************** Shop Links ******************/
		this.leftPanel.add(new HTML("<hr />"));

		{
			Label createReceipt = new Label("Create Receipt");
			createReceipt.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("CreateReceipt:/create");

				}
			});

			this.leftPanel.add(createReceipt);
		}

		{
			Label listReceipt = new Label("List Receipts");
			listReceipt.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("ListReceipts:/show");

				}
			});

			this.leftPanel.add(listReceipt);
		}



		mainPanel.addStyleName("mainPanel");
		_activityManager.setDisplay(center);


		//Add InfoBox Popup
		_infoBoxPopUp.setWidget(_infoBox);
		_infoBoxPopUp.show();


		//INfo test
		//TODO Find out why setWidth(100%) is not working
		_infoBox.setWidth((Window.getClientWidth()-20)+"px");


		//User loggedInHandler
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {

			@Override
			public void onLoginChange(LoginChangeEvent event) {
				if(event.isLoggedIn()){
					login.setVisible(false);
					register.setVisible(false);
					logout.setVisible(true);
				}else{
					login.setVisible(true);
					register.setVisible(true);
					logout.setVisible(false);
				}

			}
		});
	}


	@Override
	public IsWidget getUI(ActivityManager activityManager, ClientFactory clientFactory) {
		_activityManager=activityManager;
		_clientFactory=clientFactory;
		init();

		return vePa1;
		//return completeScreen;
	}


	@Override
	public InfoBox getInfoBox() {
		return _infoBox;
	}
}
