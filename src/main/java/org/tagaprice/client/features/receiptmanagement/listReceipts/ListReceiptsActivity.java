package org.tagaprice.client.features.receiptmanagement.listReceipts;

import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.entities.receiptManagement.Receipt;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ListReceiptsActivity implements Activity, IListReceiptsView.Presenter {

	private ClientFactory _clientFactory;
	private ListReceiptsPlace _place;
	private IListReceiptsView _listReceiptsView;

	public ListReceiptsActivity(ListReceiptsPlace place, ClientFactory clientFactory) {
		Log.debug("ListReceiptsActivity created");
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
		Log.debug("activity startet");
		Window.setTitle("Show Receipts");
		
		if(_listReceiptsView==null)_listReceiptsView=_clientFactory.getListReceiptsView();
		_listReceiptsView.setPresenter(this);
		panel.setWidget(_listReceiptsView);
		_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(ListReceiptsActivity.class, "Loading...", INFOTYPE.INFO));


		_clientFactory.getReceiptService().getReceipts(_clientFactory.getAccountPersistor().getSessionId(),new AsyncCallback<List<Receipt>>() {

			@Override
			public void onSuccess(List<Receipt> response) {				
				_listReceiptsView.setReceipts(response);

			}

			@Override
			public void onFailure(Throwable e) {
				Log.error(""+e);
				_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(ListReceiptsActivity.class, "Exception: "+e, INFOTYPE.ERROR));

			}
		});
	}

}
