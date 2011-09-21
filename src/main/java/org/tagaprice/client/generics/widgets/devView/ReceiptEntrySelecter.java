package org.tagaprice.client.generics.widgets.devView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.generics.widgets.CurrencySelecter;
import org.tagaprice.client.generics.widgets.IReceiptEntrySelecter;
import org.tagaprice.client.generics.widgets.QuantitySelecter;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;

import com.google.gwt.event.dom.client.ChangeHandler;
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
	public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {
		receiptContainer.clear();
		for(ReceiptEntry ir:receiptEntries){
			OneReceiptEntry ore = new OneReceiptEntry();
			ore.setReceiptEntry(ir);

			receiptContainer.add(ore);
		}

	}

	@Override
	public void addReceiptEntrie(ReceiptEntry receiptEntry) {
		OneReceiptEntry ore = new OneReceiptEntry();
		ore.setReceiptEntry(receiptEntry);

		receiptContainer.add(ore);

	}

	@Override
	public ArrayList<ReceiptEntry> getReceiptEntries() {
		ArrayList<ReceiptEntry> returnList = new ArrayList<ReceiptEntry> ();

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
		private ReceiptEntry _receiptEntry;

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

		public void setReceiptEntry(ReceiptEntry receiptEntry){
			_receiptEntry=receiptEntry;

			productTextBox.setText(receiptEntry.getPackage().getProduct().getTitle());
			quantitySelecter.setQuantity(receiptEntry.getPackage().getQuantity());
			quantitySelecter.setRelatedUnit(receiptEntry.getPackage().getQuantity().getUnit());
			priceTextBox.setText(""+receiptEntry.getPrice().getPrice());
			currencySelecter.setCurrency(receiptEntry.getPrice().getCurrency());

		}

		public ReceiptEntry getReceiptEntry(){
			_receiptEntry.setPrice(new Price(new BigDecimal(priceTextBox.getText()),
					currencySelecter.getCurrency()));

			_receiptEntry.getPackage().setQuantity(quantitySelecter.getQuantity());

			return _receiptEntry;
		}
	}

	@Override
	public void addChangeHandler(ChangeHandler handler) {
		// TODO Auto-generated method stub
		
	}

}