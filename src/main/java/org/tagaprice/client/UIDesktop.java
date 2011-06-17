package org.tagaprice.client;

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
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UIDesktop implements IUi {


	private PopupPanel _infoBoxPopUp = new PopupPanel();
	//private HorizontalPanel topPanel = new HorizontalPanel();
	private VerticalPanel leftPanel = new VerticalPanel();
	private SimplePanel mainPanel = new SimplePanel();
	private InfoBox _infoBox = new InfoBox();
	private DockLayoutPanel completeScreen = new DockLayoutPanel(Unit.PX);

	ActivityManager _activityManager;
	ClientFactory _clientFactory;

	private PopupPanel loginPop = new PopupPanel(true);

	private void init(){

		//Widget divLogger = Log.getLogger(DivLogger.class).getWidget();
		//LAYOUT
		//completeScreen.addSouth(divLogger, 120);
		//completeScreen.addNorth(this.topPanel, 80);
		completeScreen.addWest(this.leftPanel, 150);
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
		Label createProduct = new Label("Create Product");
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
		_activityManager.setDisplay(mainPanel);


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


		return completeScreen;
	}


	@Override
	public InfoBox getInfoBox() {
		return _infoBox;
	}
}
