package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView;

import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class CreateReceiptViewImpl extends Composite implements ICreateReceiptView {

	private Presenter _presenter;

	public CreateReceiptViewImpl() {
		initWidget(new HTML("impl uibuilder.xml here"));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;

	}


}
