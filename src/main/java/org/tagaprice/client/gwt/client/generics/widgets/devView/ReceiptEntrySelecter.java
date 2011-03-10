package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.widgets.CurrencySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.IReceiptEntrySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.QuantitySelecter;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReceiptEntrySelecter extends Composite implements IReceiptEntrySelecter {

	private VerticalPanel vePa1 = new VerticalPanel();
	private VerticalPanel receiptContainer = new VerticalPanel();
	private HorizontalPanel hoPaTitles = new HorizontalPanel();

	public ReceiptEntrySelecter() {
		initWidget(vePa1);
		vePa1.add(hoPaTitles);
		hoPaTitles.add(new Label("Product"));
		hoPaTitles.add(new Label("Quantity"));
		hoPaTitles.add(new Label("Unit"));
		hoPaTitles.add(new Label("Price"));
		hoPaTitles.add(new Label("Currency"));
		vePa1.add(receiptContainer);
	}

	@Override
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries) {
		receiptContainer.clear();
		for(IReceiptEntry ir:receiptEntries){
			OneReceiptEntry ore = new OneReceiptEntry();
			ore.setReceiptEntry(ir);

			receiptContainer.add(ore);
		}

	}

	@Override
	public void addReceiptEntrie(IReceiptEntry receiptEntry) {
		OneReceiptEntry ore = new OneReceiptEntry();
		ore.setReceiptEntry(receiptEntry);

		receiptContainer.add(ore);

	}

	@Override
	public ArrayList<IReceiptEntry> getReceiptEntries() {
		ArrayList<IReceiptEntry> returnList = new ArrayList<IReceiptEntry> ();

		for(int i=0;i<receiptContainer.getWidgetCount();i++){
			returnList.add(((OneReceiptEntry)receiptContainer.getWidget(i)).getReceiptEntry());
		}

		return returnList;
	}

	class OneReceiptEntry extends Composite{

		private HorizontalPanel hoPa1 = new HorizontalPanel();
		private Label productTextBox = new Label();
		private QuantitySelecter quantitySelecter = new QuantitySelecter();
		private TextBox priceTextBox = new TextBox();
		private CurrencySelecter currencySelecter = new CurrencySelecter();
		private Button removeButton = new Button("-");
		private IReceiptEntry _receiptEntry;

		public OneReceiptEntry() {
			initWidget(hoPa1);

			hoPa1.add(productTextBox);
			hoPa1.add(quantitySelecter);
			hoPa1.add(priceTextBox);
			hoPa1.add(currencySelecter);
			hoPa1.add(removeButton);

			removeButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					removeMeFromParent();
				}
			});


		}

		private void removeMeFromParent(){
			this.removeFromParent();
		}

		public void setReceiptEntry(IReceiptEntry receiptEntry){
			productTextBox.setText(receiptEntry.getPackage().getProduct().getTitle());
			quantitySelecter.setQuantity(receiptEntry.getPackage().getQuantity());
			priceTextBox.setText(""+receiptEntry.getPrice().getPrice());
			currencySelecter.setCurrency(receiptEntry.getPrice().getCurrency());

		}

		public IReceiptEntry getReceiptEntry(){
			return _receiptEntry;
		}
	}

}
