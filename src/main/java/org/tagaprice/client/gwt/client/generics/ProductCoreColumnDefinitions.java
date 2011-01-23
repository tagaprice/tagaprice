package org.tagaprice.client.gwt.client.generics;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;



import com.google.gwt.user.client.ui.*;

public class ProductCoreColumnDefinitions {

	ArrayList<ColumnDefinition<IProduct>> columns = new ArrayList<ColumnDefinition<IProduct>>();

	public ProductCoreColumnDefinitions() {
		this.columns.add(new ColumnDefinition<IProduct>() {

			@Override
			public Widget render(IProduct t) {
				return new HTML(t.getTitle());
			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return "name";
			}
		});
		this.columns.add(new ColumnDefinition<IProduct>() {

			@Override
			public Widget render(IProduct t) {
				return new HTML(t.getCategory().toString());
			}

			@Override
			public String getColumnName() {
				return "Category";
			}
		});
		this.columns.add(new ColumnDefinition<IProduct>() {

			@Override
			public Widget render(IProduct t) {
				return new HTML(t.getQuantity().toString());
			}

			@Override
			public String getColumnName() {
				return "Selling Unit";
			}
		});
		this.columns.add(new ColumnDefinition<IProduct>() {

			@Override
			public Widget render(IProduct t) {

				return new Anchor("Open/Edit", "#CreateProduct:/show/id/" + t.getRevisionId().getId());

			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return "Action";
			}
		});
		this.columns.add(new ColumnDefinition<IProduct>() {

			@Override
			public Widget render(IProduct t) {
				return new Anchor("Delete");
			}

			@Override
			public String getColumnName() {
				return "Delete";
			}
		});


	}

	public ArrayList<ColumnDefinition<IProduct>> getColumnDefinitions() {
		return this.columns;
	}
}
