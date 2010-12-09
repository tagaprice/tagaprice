package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.mvp.*;
import org.tagaprice.client.gwt.client.places.ProductListPlace;
import org.tagaprice.client.gwt.shared.logging.*;

import com.google.gwt.activity.shared.*;
import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.*;
import com.google.gwt.user.client.ui.*;
/**
 * GWT STARTPOINT - This is the class with the EntryPoint.
 * @author Helga Weik (kaltra)
 *
 */
public class TagAPrice implements EntryPoint {
	private static MyLogger logger = LoggerFactory
	.getLogger(TagAPrice.class);

	private Place defaultPlace = new ProductListPlace("");
	private HorizontalPanel topPanel = new HorizontalPanel();
	private SimplePanel leftPanel = new SimplePanel();
	private SimplePanel mainPanel = new SimplePanel();

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

		this.leftPanel.add(new HTML("<h3>the menu</h3>"));
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
