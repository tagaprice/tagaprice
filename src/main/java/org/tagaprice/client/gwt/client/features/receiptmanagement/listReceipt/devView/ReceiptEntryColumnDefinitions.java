package org.tagaprice.client.gwt.client.features.receiptmanagement.listReceipt.devView;

import java.util.ArrayList;


import org.tagaprice.client.gwt.client.generics.ColumnDefinition;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;

import com.google.gwt.user.client.ui.*;

public class ReceiptEntryColumnDefinitions {
	ArrayList<ColumnDefinition<IReceiptEntry>> columns = new ArrayList<ColumnDefinition<IReceiptEntry>>();

	public 	ReceiptEntryColumnDefinitions() {
		this.columns.add(new ColumnDefinition<IReceiptEntry>() {

			@Override
			public Widget render(IReceiptEntry t) {
				return new HTML(t.getProduct().getTitle());
			}

			@Override
			public String getColumnName() {
				return "title";
			}
		});
		this.columns.add(new ColumnDefinition<IReceiptEntry>() {

			@Override
			public Widget render(IReceiptEntry t) {

				String str = "";
				str = (t.getPrice() / 100) + "." + (t.getPrice()%100) + " EUR";
				return new HTML(str);
			}

			@Override
			public String getColumnName() {
				return "price";
			}
		});

	}
	public ArrayList<ColumnDefinition<IReceiptEntry>> getColumnDefinitions() {
		return this.columns;
	}
}
