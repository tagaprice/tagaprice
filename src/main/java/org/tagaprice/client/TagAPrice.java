package org.tagaprice.client;

import org.tagaprice.client.features.startmanagement.StartPlace;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxDestroyEventHandler;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEventHandler;
import org.tagaprice.client.generics.facebook.FBCore;
import org.tagaprice.client.mvp.AppActivityMapper;
import org.tagaprice.client.mvp.AppPlaceHistoryMapper;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

/**
 * GWT STARTPOINT - This is the class with the EntryPoint.
 * @author Helga Weik (kaltra)
 *
 */
public class TagAPrice implements EntryPoint {

	private ClientFactory clientFactory;
	private EventBus eventBus;


	private IUi _iui = GWT.create(IUi.class);
	private FBCore _fbCore = new FBCore();
	InfoBoxShowEvent infoBox;


	/**
	 * Initializes ActivityManager and ActivityMapper for each display-area.
	 * For now, we have one display-area.
	 */
	@Override
	public void onModuleLoad() {
		//Log.debug("This is a 'DEBUG' test message");
		Log.debug("EntryPoint startet");
		clientFactory = GWT.create(ClientFactory.class);
		eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();


		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);

		AppPlaceHistoryMapper historyMapper = GWT
		.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController, eventBus, new StartPlace());


		//RootLayoutPanel.get().add(completeScreen);
		//RootLayoutPanel.get().add(_iui.getUI(activityManager, clientFactory));
		//RootLayoutPanel.get().add(_iui.getUI(activityManager, clientFactory));
		//RootPanel.get().add(RootLayoutPanel.get());
		//RootPanel.get().add(_iui.getUI(activityManager, clientFactory));
		_iui.initUI(activityManager, clientFactory);
		historyHandler.handleCurrentHistory();



		eventBus.addHandler(InfoBoxShowEvent.TYPE, new InfoBoxShowEventHandler() {
			@Override
			public void onNewInfo(InfoBoxShowEvent event) {
				_iui.getInfoBox().addInfoBoxEvent(event);
			}

		});

		eventBus.addHandler(InfoBoxDestroyEvent.TYPE, new InfoBoxDestroyEventHandler() {

			@Override
			public void onDestroyInfo(InfoBoxDestroyEvent event) {
				Log.debug("Destroy event:");
				_iui.getInfoBox().removeInfoBoxEvent(event);
			}
		});



		clientFactory.getAccountPersistor().setClientFactory(clientFactory);
		clientFactory.getAccountPersistor().checkLogin();




	}


}
