package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.ListProductsPlace;
import org.tagaprice.client.gwt.client.mvp.*;
import org.tagaprice.client.gwt.shared.logging.*;

import com.google.gwt.activity.shared.*;
import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.*;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
/**
 * GWT STARTPOINT - This is the class with the EntryPoint.
 * @author Helga Weik (kaltra)
 *
 */
public class TagAPrice implements EntryPoint {
	private static MyLogger logger = LoggerFactory
	.getLogger(TagAPrice.class);

	private Place defaultPlace = new ListProductsPlace("");
	private HorizontalPanel topPanel = new HorizontalPanel();
	private VerticalPanel leftPanel = new VerticalPanel();
	private SimplePanel mainPanel = new SimplePanel();

	final private NotificationMole mole = new NotificationMole();

	/**
	 * Initializes ActivityManager and ActivityMapper for each display-area.
	 * For now, we have one display-area.
	 */
	@Override
	public void onModuleLoad() {
		TagAPrice.logger.log("EntryPoint startet");
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		//LAYOUT
		DockLayoutPanel completeScreen = new DockLayoutPanel(Unit.EM);
		completeScreen.addNorth(this.topPanel, 7);
		completeScreen.addWest(this.leftPanel, 10);
		completeScreen.add(this.mainPanel);

		//Configure Logo
		this.topPanel.add(new Image("TagaAPriceLogo.png"));
		this.topPanel.add(new HTML("<h1>TagAPrice</h1>"));
		this.topPanel.add(this.mole);
		//This is quite a mess...
		clientFactory.getProductServiceDispatch().setMole(this.mole);

		this.leftPanel.add(new HTML("<h3>menu</h3>"));
		Label createProduct = new Label("Create Product");
		createProduct.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateProduct:/create");}});

		Label getProduct = new Label("getProduct");
		getProduct.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateProduct:/show");}});

		Label getProductById = new Label("getProductById");
		getProductById.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateProduct:/show/id/1");}});

		Label getProductByIdAndRev = new Label("getProductByIdAndRev");
		getProductByIdAndRev.addClickHandler(new ClickHandler() {@Override
			public void onClick(ClickEvent arg0) {
			History.newItem("CreateProduct:/show/id/1/rev/3");}});

		this.leftPanel.add(createProduct);
		this.leftPanel.add(getProduct);
		this.leftPanel.add(getProductById);
		this.leftPanel.add(getProductByIdAndRev);

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

	}

}
