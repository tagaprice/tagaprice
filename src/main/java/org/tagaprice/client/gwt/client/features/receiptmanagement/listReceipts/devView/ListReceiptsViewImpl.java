package org.tagaprice.client.gwt.client.features.receiptmanagement.listReceipts.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceipt;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class ListReceiptsViewImpl extends Composite implements IListReceiptsView {


	public ListReceiptsViewImpl() {
		initWidget(new Label("List Receipts"));
	}

	@Override
	public void setReceipts(ArrayList<IReceipt> receipts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub

	}

}
