package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.widgets.IReceiptEntrySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.QuantitySelecter;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReceiptEntrySelecter extends Composite implements IReceiptEntrySelecter {

	private VerticalPanel vePa1 = new VerticalPanel();
	private HorizontalPanel hoPaTitles = new HorizontalPanel();

	public ReceiptEntrySelecter() {
		initWidget(vePa1);
		vePa1.add(hoPaTitles);
		hoPaTitles.add(new Label("Product"));
		hoPaTitles.add(new Label("Quantity"));
		hoPaTitles.add(new Label("Unit"));
		hoPaTitles.add(new Label("Price"));
		hoPaTitles.add(new Label("Currency"));
	}

	@Override
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries) {
		for(IReceiptEntry ir:receiptEntries){
			OneReceiptEntry ore = new OneReceiptEntry();
			ore.setReceiptEntry(ir);

			vePa1.add(ore);
		}

	}

	@Override
	public void addReceiptEntrie(IReceiptEntry receiptEntry) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<IReceiptEntry> getReceiptEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	class OneReceiptEntry extends Composite{

		private HorizontalPanel hoPa1 = new HorizontalPanel();
		private TextBox productTextBox = new TextBox();
		private QuantitySelecter quantitySelecter = new QuantitySelecter();
		private TextBox priceTextBox = new TextBox();
		private TextBox currencyTextBox = new TextBox();

		public OneReceiptEntry() {
			initWidget(hoPa1);

			hoPa1.add(productTextBox);
			hoPa1.add(quantitySelecter);
			hoPa1.add(priceTextBox);
			hoPa1.add(currencyTextBox);

		}

		public void setReceiptEntry(IReceiptEntry receiptEntry){
			productTextBox.setText(receiptEntry.getPackage().getProduct().getTitle());
			quantitySelecter.setQuantity(receiptEntry.getPackage().getQuantity());
			priceTextBox.setText(""+receiptEntry.getPrice().getPrice());
			currencyTextBox.setText(""+receiptEntry.getPrice().getCurrency());

		}
	}

}
