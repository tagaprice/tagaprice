package org.tagaprice.client;

import org.tagaprice.client.features.productmanagement.listProducts.ListProductsPlace;
import org.tagaprice.client.generics.I18N;
import org.tagaprice.client.generics.events.AddressChangedEvent;
import org.tagaprice.client.generics.events.AddressChangedEventHandler;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxDestroyEventHandler;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.LoginChangeEventHandler;
import org.tagaprice.client.generics.events.WaitForAddressEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.events.InfoBoxShowEventHandler;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.WaitForAddressEventHandler;
import org.tagaprice.client.generics.widgets.InfoBox;
import org.tagaprice.client.mvp.AppActivityMapper;
import org.tagaprice.client.mvp.AppPlaceHistoryMapper;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NotificationMole;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * GWT STARTPOINT - This is the class with the EntryPoint.
 * @author Helga Weik (kaltra)
 *
 */
public class TagAPrice implements EntryPoint {
	private static MyLogger logger = LoggerFactory
	.getLogger(TagAPrice.class);

	private PopupPanel _infoBoxPopUp = new PopupPanel();
	private Place defaultPlace = new ListProductsPlace("");
	private HorizontalPanel topPanel = new HorizontalPanel();
	private VerticalPanel leftPanel = new VerticalPanel();
	private SimplePanel mainPanel = new SimplePanel();
	private InfoBox _infoBox = new InfoBox();

	final private NotificationMole mole = new NotificationMole();

	private ClientFactory clientFactory;
	private EventBus eventBus;

	/**
	 * Initializes ActivityManager and ActivityMapper for each display-area.
	 * For now, we have one display-area.
	 */
	@Override
	public void onModuleLoad() {
		TagAPrice.logger.log("EntryPoint startet");
		clientFactory = GWT.create(ClientFactory.class);
		eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		//LAYOUT
		DockLayoutPanel completeScreen = new DockLayoutPanel(Unit.PX);
		completeScreen.addNorth(this.topPanel, 80);
		completeScreen.addWest(this.leftPanel, 150);
		completeScreen.add(this.mainPanel);

		//Configure Logo
		this.topPanel.add(new Image("TagaAPriceLogo.png"));
		this.topPanel.add(new HTML("<h1>TagAPrice</h1>"));
		this.topPanel.add(this.mole);
		//This is quite a mess...

		this.leftPanel.add(new HTML("<h3>"+I18N.I18N.testmenu()+"</h3>"));

		/******************** Product Links *****************/
		this.leftPanel.add(new Button("Locate", new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				locateMe();
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
			History.newItem("ListProducts");}});

		Label getProductById = new Label("get Product id=1");
		getProductById.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateProduct:/show/id/1");}});

		Label getProductByIdAndRev = new Label("get Product id=1, rev=3");
		getProductByIdAndRev.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateProduct:/show/id/1/rev/3");}});

		this.leftPanel.add(createProduct);
		this.leftPanel.add(getProduct);
		this.leftPanel.add(getProductById);
		this.leftPanel.add(getProductByIdAndRev);

		/******************** Login Links *****************/
		this.leftPanel.add(new HTML("<hr />"));
		final Label login = new Label("Login");
		login.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("LogInOut:/login");}});
		this.leftPanel.add(login);

		final Label logout = new Label("Logout");
		logout.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("LogInOut:/logout");}});
		this.leftPanel.add(logout);
		logout.setVisible(false);

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

		Label getShopById = new Label("get Shop id=1");
		getShopById.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateShop:/show/id/1");}});

		this.leftPanel.add(createShop);
		this.leftPanel.add(getShop);
		this.leftPanel.add(getShopById);



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

		{
			Label createReceipt = new Label("get Receipt id=1");
			createReceipt.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("CreateReceipt:/show/id/1");

				}
			});

			this.leftPanel.add(createReceipt);
		}


		this.mainPanel.addStyleName("mainPanel");

		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);
		activityManager.setDisplay(this.mainPanel);

		AppPlaceHistoryMapper historyMapper = GWT
		.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController, eventBus, this.defaultPlace);


		RootLayoutPanel.get().add(completeScreen);
		historyHandler.handleCurrentHistory();

		//Add InfoBox Popup
		_infoBoxPopUp.setWidget(_infoBox);
		_infoBoxPopUp.show();


		//INfo test
		//TODO Find out why setWidth(100%) is not working
		_infoBox.setWidth((Window.getClientWidth()-20)+"px");


		eventBus.addHandler(InfoBoxShowEvent.TYPE, new InfoBoxShowEventHandler() {
			@Override
			public void onNewInfo(InfoBoxShowEvent event) {
				_infoBox.addInfoBoxEvent(event);
			}

		});

		eventBus.addHandler(InfoBoxDestroyEvent.TYPE, new InfoBoxDestroyEventHandler() {

			@Override
			public void onDestroyInfo(InfoBoxDestroyEvent event) {
				TagAPrice.logger.log("Destroy event:");
				_infoBox.removeInfoBoxEvent(event);
			}
		});


		//AddressChangeHandler
		eventBus.addHandler(AddressChangedEvent.TYPE, new AddressChangedEventHandler() {

			@Override
			public void onAddressChanged(AddressChangedEvent event) {
				clientFactory.setAddress(event.getAddress());

				_infoBox.addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Address Updated: "+event.getAddress(), INFOTYPE.SUCCESS));
			}
		});

		eventBus.addHandler(WaitForAddressEvent.TYPE, new WaitForAddressEventHandler() {

			@Override
			public void onAddressChanged(WaitForAddressEvent event) {
				locateMe();
			}
		});

		eventBus.fireEvent(new WaitForAddressEvent());




		//User loggedInHandler
		eventBus.addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {

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

	private void locateMe(){
		_infoBox.addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Try to update address", INFOTYPE.INFO));


		Geolocation.getGeolocation().getCurrentPosition(new PositionCallback() {

			@Override
			public void onSuccess(final Position position) {


				//user OSM
				clientFactory.getSearchService().searchAddress(position.getCoords().getLatitude(), position.getCoords().getLongitude(),
						new AsyncCallback<Address>() {

					@Override
					public void onSuccess(Address result) {
						// TODO Auto-generated method stub
						_infoBox.addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Something was ok: "+result, INFOTYPE.INFO));
						eventBus.fireEvent(new AddressChangedEvent(result));

					}

					@Override
					public void onFailure(Throwable e) {
						_infoBox.addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Can't find address", INFOTYPE.ERROR,0));
					}
				});


			}

			@Override
			public void onFailure(PositionError error) {
				_infoBox.addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Can't find address", INFOTYPE.ERROR,0));

			}
		});
	}

}
