package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateReceiptActivity implements ICreateReceiptView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateReceiptActivity.class);

	private CreateReceiptPlace _place;
	private ClientFactory _clientFactory;

	public CreateReceiptActivity(CreateReceiptPlace place, ClientFactory clientFactory) {
		CreateReceiptActivity._logger.log("CreateProductActivity created");
		_place = place;
		_clientFactory = clientFactory;
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
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub
		CreateReceiptActivity._logger.log("activity startet");

		//panel.setWidget(new HTML("Impl here some cool stuff"));
		panel.setWidget(_clientFactory.getCreateReceiptView());
	}

}
