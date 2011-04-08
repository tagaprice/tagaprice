package org.tagaprice.client.features.receiptmanagement.listReceipts.devView;

import java.util.List;

import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListReceiptsViewImpl extends Composite implements IListReceiptsView {

	VerticalPanel _receiptList = new VerticalPanel();
	Presenter _presenter;

	public ListReceiptsViewImpl() {
		initWidget(_receiptList);
	}

	@Override
	public void setReceipts(List<Receipt> receipts) {
		_receiptList.clear();

		for(final Receipt r:receipts){
			Label ml = new Label(createNiceReceiptLine(r));
			ml.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getId()));
				}
			});
			_receiptList.add(ml);
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	private String createNiceReceiptLine(Receipt receipt){
		String nice="";

		if (receipt.getId() != null) nice+="Id: "+receipt.getId()+" | ";

		nice+=receipt.getTitle()+" | ";

		int money=0;
		for(ReceiptEntry re: receipt.getReceiptEntries()){
			money+=re.getPrice().getPrice();
		}
		nice+="m: "+money;


		return nice;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
