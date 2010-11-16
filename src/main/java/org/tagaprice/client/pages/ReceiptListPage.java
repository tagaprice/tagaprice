package org.tagaprice.client.pages;

import java.util.ArrayList;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.TitleWidget.Headline;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReceiptListPage extends APage {

	private VerticalPanel _verticalPanel_1 = new VerticalPanel();
	private Grid _table;

/**
 * 	
 */
	public ReceiptListPage() {
		init(_verticalPanel_1);
		_verticalPanel_1.setWidth("100%");
		
		
		
		try {
			RPCHandlerManager.getReceiptHandler().getUserReceipts(new AsyncCallback<ArrayList<ReceiptData>>() {
/**
 * A new receipt with name, date and price is created in a personal receipt table				
 * @param result
 */
	
				@Override
				public void onSuccess(ArrayList<ReceiptData> result) {
					
					_table = new Grid(result.size()+1,3);
					int c = 1;
					
					//head
					_table.setWidth("100%");
					_table.setText(0, 0, "Name");
					_table.setText(0, 1, "Date");
					_table.setText(0, 2, "Price [€]");
					
					long totalPrice = 0;
					
					//get Rows
					for(final ReceiptData pd: result){
						Label title = new Label(pd.getTitle()+": "+pd.getId());
						if(pd.getDraft()){
							title.setText(title.getText()+" [DRAFT]");
						}
						_table.setWidget(c, 0, title);
						_table.setText(c, 1, DateTimeFormat.getLongDateFormat().format(pd.getDate()));
						_table.setText(c, 2, ""+(pd.getTotalPrice()/100.00));
						
						totalPrice = totalPrice + pd.getTotalPrice();
						
						title.addClickHandler(new ClickHandler() {							
							@Override
							public void onClick(ClickEvent event) {
								History.newItem("receipt/get&id="+pd.getId());							
							}
						});						
						c++;
					}
					
					
					_verticalPanel_1.add(new TitleWidget("MyReceipts", _table, Headline.H2));
					
					_verticalPanel_1.add(new Label("Total Price: "+(totalPrice/100.00)+"[€]"));
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					showInfo("getUserReceipts FAIL ", BoxType.WARNINGBOX);					
				}
			});
		} catch (IllegalArgumentException e) {
			showInfo("IllegalArgumentException: "+e, BoxType.WARNINGBOX);
		} catch (InvalidLoginException e) {
			showInfo("InvalidLoginException: "+e, BoxType.WARNINGBOX);

		}
	}
/**
 * Sets the position
 */
	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}
}
