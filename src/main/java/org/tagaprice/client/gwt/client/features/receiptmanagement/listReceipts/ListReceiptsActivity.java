package org.tagaprice.client.gwt.client.features.receiptmanagement.listReceipts;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ListReceiptsActivity implements Activity, IListReceiptsView.Presenter {

	private static final MyLogger _logger = LoggerFactory.getLogger(ListReceiptsActivity.class);
	private ClientFactory _clientFactory;
	private ListReceiptsPlace _place;
	private IListReceiptsView _listReceiptsView;

	public ListReceiptsActivity(ListReceiptsPlace place, ClientFactory clientFactory) {
		ListReceiptsActivity._logger.log("ListReceiptsActivity created");
		_place = place;
		_clientFactory = clientFactory;
	}

	@Override
	public void goTo(Place place) {
		this._clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ListReceiptsActivity._logger.log("activity startet");

		if(_listReceiptsView==null)_listReceiptsView=_clientFactory.getListReceiptsView();

		panel.setWidget(_listReceiptsView);

	}

}
