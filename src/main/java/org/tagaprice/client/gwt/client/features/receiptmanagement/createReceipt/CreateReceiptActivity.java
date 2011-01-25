package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView.Presenter;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateReceiptActivity implements Presenter, Activity{

	MyLogger logger = LoggerFactory.getLogger(CreateReceiptActivity.class);

	CreateReceiptPlace _place ;
	ClientFactory _clientFactory ;


	public CreateReceiptActivity(CreateReceiptPlace place, ClientFactory clientFactory) {
		logger.log("CreateReceiptActivity created");
		this._place = place;
		this._clientFactory =clientFactory;

		// TODO Auto-generated constructor stub
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
		logger.log("start");
		ICreateReceiptView<IReceiptEntry> createReceiptView = this._clientFactory.getCreateReceiptView();

		panel.setWidget(createReceiptView.asWidget());

	}



	@Override
	public void onAddEntry() {
		// TODO Auto-generated method stub

	}



	@Override
	public void onSave() {
		// TODO Auto-generated method stub

	}

}
