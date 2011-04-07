package org.tagaprice.client.features.receiptmanagement.listReceipts.devView;

import java.util.List;

import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.shared.entities.receiptManagement.IReceipt;
import org.tagaprice.shared.entities.receiptManagement.IReceiptEntry;

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
	public void setReceipts(List<IReceipt> receipts) {
		_receiptList.clear();

		for(final IReceipt r:receipts){
			Label ml = new Label(createNiceReceiptLine(r));
			ml.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getRevisionId().getId()));
				}
			});
			_receiptList.add(ml);
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	private String createNiceReceiptLine(IReceipt receipt){
		String nice="";

		if(receipt.getRevisionId()!=null) nice+="Id: "+receipt.getRevisionId().getId()+" | ";

		nice+=receipt.getTitle()+" | ";

		int money=0;
		for(IReceiptEntry re: receipt.getReceiptEntries()){
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
