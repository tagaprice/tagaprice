package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.mvp.*;
import org.tagaprice.client.gwt.client.places.ProductListPlace;
import org.tagaprice.client.gwt.shared.logging.*;

import com.google.gwt.activity.shared.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.*;
import com.google.gwt.user.client.ui.*;
/**
 * This is the class with the EntryPoint
 * @author Helga Weik (kaltra)
 *
 */
public class TagAPrice implements EntryPoint {
	private static MyLogger logger = LoggerFactory
			.getLogger(TagAPrice.class);

	private Place defaultPlace = new ProductListPlace("productlist");
	private SimplePanel appWidget = new SimplePanel();

	@Override
	public void onModuleLoad() {
		logger.log("EntryPoint startet");
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);
		activityManager.setDisplay(this.appWidget);

		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController, eventBus, this.defaultPlace);

		RootPanel.get().add(this.appWidget);
		historyHandler.handleCurrentHistory();

	}

}
