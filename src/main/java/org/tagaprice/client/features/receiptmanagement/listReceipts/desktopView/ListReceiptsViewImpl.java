package org.tagaprice.client.features.receiptmanagement.listReceipts.desktopView;

import java.math.BigDecimal;
import java.util.List;

import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget.MENUPOINT;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class ListReceiptsViewImpl extends Composite implements IListReceiptsView {

	private Presenter _presenter;
	private StdFrame _frame = new StdFrame();
	private Label _header = new Label("Dashboad / My Receipts");
	private Grid _receiptList = new Grid();
	private DashboardMenuWidget _menu = new DashboardMenuWidget();

	public ListReceiptsViewImpl() {
		_frame.setHeader(_header);
		
		//menu
		_frame.setBody(_menu,"200px");
		_menu.setActiveType(MENUPOINT.MYRECEIPTS);
		_menu.addLogoutClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onLogout();
			}
		});
		
		_frame.setBody(_receiptList);

		initWidget(_frame);
	}

	@Override
	public void setReceipts(List<Receipt> receipts) {

		_receiptList.clear();
		_receiptList.resize(receipts.size()+1, 3);
		
		//head
		_receiptList.setWidget(0, 0, new Label("Date"));
		_receiptList.setWidget(0, 1, new Label("Name"));
		_receiptList.setWidget(0, 2, new Label("Value"));
		
		
		
		int i=1;
		for(final Receipt r:receipts){
			
			Label date = new Label(r.getDate().toString());
			date.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getId()));
				}
			});
			Label name = new Label(r.getShop().getTitle()+", "+r.getShop().getAddress().getStreet());
			name.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getId()));
				}
			});
			
			
			//calc value
			BigDecimal money = new BigDecimal("0");
			for(ReceiptEntry re: r.getReceiptEntries()){
				money = money.add(re.getPrice().getPrice());
			}
			Label value = new Label(money.toString());
			
			
			value.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getId()));
				}
			});
			
			
			_receiptList.setWidget(i, 0, date);
			_receiptList.setWidget(i, 1, name);
			_receiptList.setWidget(i, 2, value);
			
			
			
			
			i++;
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}


}
