package org.tagaprice.client;

import org.tagaprice.client.TitlePanel.Level;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReceiptListPage extends InfoBoxComposite {

	private VerticalPanel vePa1 = new VerticalPanel();
	private Grid table = new Grid(1,3);
	
	public ReceiptListPage() {
		init(vePa1);
		vePa1.setWidth("100%");
		
		//head
		table.setText(0, 0, "Name");
		table.setText(0, 1, "Date");
		table.setText(0, 2, "Price");
		
		vePa1.add(new TitlePanel("MyReceipts", table, Level.H2));
	}
}
