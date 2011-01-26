package org.tagaprice.client.gwt.client.features.receiptmanagement.listReceipt.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.ColumnDefinition;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;

import com.google.gwt.user.client.ui.*;

public class ReceiptEntryColumnDefinitions {
	ArrayList<ColumnDefinition<IReceiptEntry>> columns = new ArrayList<ColumnDefinition<IReceiptEntry>>();

	public ReceiptEntryColumnDefinitions() {
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
				str = (t.getPrice() / 100) + "." + (t.getPrice() % 100) + " EUR";
				return new HTML(str);
			}

			@Override
			public String getColumnName() {
				return "price";
			}
		});
		this.columns.add(new ColumnDefinition<IReceiptEntry>() {

			@Override
			public Widget render(IReceiptEntry t) {
				return new HTML(String.valueOf(t.getQuantity()));
			}

			@Override
			public String getColumnName() {
				return "Quantity";
			}
		});
		this.columns.add(new ColumnDefinition<IReceiptEntry>() {

			@Override
			public Widget render(IReceiptEntry t) {
				long price = t.getPrice() * t.getQuantity();

				String str = "";
				str = (price / 100) + "." + (price % 100) + " EUR";
				return new HTML(str);
			}

			@Override
			public String getColumnName() {
				return "priceLine";
			}
		});

	}

	public ArrayList<ColumnDefinition<IReceiptEntry>> getColumnDefinitions() {
		return this.columns;
	}
}
