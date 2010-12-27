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
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return null;
			}
		});

	}

	public ArrayList<ColumnDefinition<IProduct>> getColumnDefinitions() {
		return this.columns;
	}
}
