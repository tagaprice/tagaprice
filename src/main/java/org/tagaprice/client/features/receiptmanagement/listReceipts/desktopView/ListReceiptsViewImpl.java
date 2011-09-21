package org.tagaprice.client.features.receiptmanagement.listReceipts.desktopView;

import java.math.BigDecimal;
import java.util.List;

import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget;
import org.tagaprice.client.generics.widgets.desktopView.DashboardMenuWidget.MENUPOINT;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class ListReceiptsViewImpl extends Composite implements IListReceiptsView {

	private Presenter _presenter;
	private StdFrame _frame = new StdFrame();
	private Label _header = new Label("Dashboad / My Receipts");
	private Grid _receiptList = new Grid();
	private DashboardMenuWidget _menu = new DashboardMenuWidget();
	private SimplePanel _listSiPa = new SimplePanel();
	private DateTimeFormat fmt = DateTimeFormat.getFormat("dd, MMM. yyyy");
	private NumberFormat dfmt = NumberFormat.getFormat("0.00");
	//private CellTable<Receipt> _cellTable = new CellTable<Receipt>();
	
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
		
		_receiptList.setWidth("100%");
		
		_listSiPa.setWidth("100%");
		
		_frame.setBody(_listSiPa);

		/*
		//Create table
		_cellTable.setWidth("100%");
		TextColumn<Receipt> dateColunn = new TextColumn<Receipt>() {

			@Override
			public String getValue(Receipt arg0) {
				return fmt.format(arg0.getDate());
			}
		};
		// Add the columns.
		dateColunn.setSortable(true);		
		_cellTable.addColumn(dateColunn, "Date");
		
		_cellTable.addCellPreviewHandler(new Handler<Receipt>() {

			@Override
			public void onCellPreview(CellPreviewEvent<Receipt> arg0) {
				// TODO Auto-generated method stub
				System.out.println("id: "+arg0.getValue().getId());
				
			}
		});
		
		
		
		TextColumn<Receipt> shopColunn = new TextColumn<Receipt>() {

			@Override
			public String getValue(Receipt arg0) {
				String shop=arg0.getShop().getTitle();
				
				if(arg0.getAddress().getStreet()!=null)
					shop+=", "+arg0.getAddress().getStreet();
				
				return shop;
			}
		};
		// Add the columns.
		shopColunn.setSortable(true);
		_cellTable.addColumn(shopColunn, "Shop");
		
		
		TextColumn<Receipt> priceColunn = new TextColumn<Receipt>() {

			@Override
			public String getValue(Receipt arg0) {
				if(arg0.getPrice()==null)return "0.0€";
				return arg0.getPrice().getPrice().toEngineeringString()+"€";
			}
		};
		// Add the columns.
		priceColunn.setSortable(true);
		_cellTable.addColumn(priceColunn, "Price");
		
		
		TextColumn<Receipt> noteColunn = new TextColumn<Receipt>() {

			@Override
			public String getValue(Receipt arg0) {
				if(arg0.getTitle()==null)
					return "";
				
				return arg0.getTitle();
			}
		};
		// Add the columns.
		_cellTable.addColumn(noteColunn, "Note");
		*/
		
		
		
		setReceiptListIsLoading();
		initWidget(_frame);
	}

	@Override
	public void setReceipts(List<Receipt> receipts) {
		
		_receiptList.clear();
		_receiptList.resize(receipts.size()+1, 4);
		
		//head
		_receiptList.setWidget(0, 0, new Label("Date"));
		_receiptList.setWidget(0, 1, new Label("Name"));
		_receiptList.setWidget(0, 2, new Label("Value"));
		_receiptList.setWidget(0, 3, new Label("Note"));
		
		
		
		int i=1;
		for(final Receipt r:receipts){
			
			Label date = new Label(fmt.format(r.getDate()));
			date.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getId()));
				}
			});
			String namet = r.getShop().getTitle();
			if(r.getShop().getAddress() !=null && r.getShop().getAddress().getStreet()!=null)
				namet+=", "+r.getShop().getAddress().getStreet();
			Label name = new Label(namet);
			name.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getId()));
				}
			});
			
			
			BigDecimal money = new BigDecimal("0.0");
			if(r.getPrice()!=null)
				money=r.getPrice().getPrice();
			
			Label value = new Label(dfmt.format(money)+"€");
			
			
			value.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent e) {
					_presenter.goTo(new CreateReceiptPlace(r.getId()));
				}
			});
			
			Label note = new Label();
			if(r.getTitle()!=null)
				note.setText(r.getTitle());
			
			
			_receiptList.setWidget(i, 0, date);
			_receiptList.setWidget(i, 1, name);
			_receiptList.setWidget(i, 2, value);
			_receiptList.setWidget(i, 3, note);
			
			
			
			i++;
		}

		_listSiPa.setWidget(_receiptList);
		/*
		_cellTable.setRowData(receipts);
		_listSiPa.setWidget(_cellTable);
		*/
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	@Override
	public void setReceiptListIsLoading() {
		_listSiPa.setWidget(new Image("desktopView/ajax-loader.gif"));
		
	}


}
