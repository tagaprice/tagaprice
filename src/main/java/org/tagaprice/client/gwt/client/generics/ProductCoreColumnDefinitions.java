package org.tagaprice.client.gwt.client.generics;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.ProductCore;



import com.google.gwt.user.client.ui.*;

public class ProductCoreColumnDefinitions {

	ArrayList<ColumnDefinition<ProductCore>> columns = new ArrayList<ColumnDefinition<ProductCore>>();

	public ProductCoreColumnDefinitions() {
		this.columns.add(new ColumnDefinition<ProductCore>() {

			@Override
			public Widget render(ProductCore t) {
				return new CheckBox();
			}

			@Override
			public boolean isClickable() {
				return true;
			}

			@Override
			public String getColumnName() {
				return "checkbox";
			}
		});

		this.columns.add(new ColumnDefinition<ProductCore>() {

			@Override
			public Widget render(ProductCore t) {
				return new HTML(String.valueOf(t.getId()));
			}

			@Override
			public boolean isSelectable() {
				return true;
			}

			@Override
			public String getColumnName() {
				return "id";
			}
		});

		this.columns.add(new ColumnDefinition<ProductCore>() {

			@Override
			public Widget render(ProductCore t) {
				return new HTML(t.getName());
			}

			@Override
			public boolean isSelectable() {
				return true;
			}

			@Override
			public String getColumnName() {
				return "name";
			}
		});
	}

	public ArrayList<ColumnDefinition<ProductCore>> getColumnDefinitions() {
		return this.columns;
	}
}
