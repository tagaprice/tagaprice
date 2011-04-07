package org.tagaprice.client.features.productmanagement.listProducts.devView;

import java.util.ArrayList;

import org.tagaprice.client.generics.ColumnDefinition;
import org.tagaprice.shared.entities.productmanagement.IProduct;



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
				return new HTML(t.getUnit().toString());
			}

			@Override
			public String getColumnName() {
				return "Selling Unit";
			}
		});
		this.columns.add(new ColumnDefinition<IProduct>() {

			@Override
			public Widget render(IProduct t) {

				return new Anchor("Open/Edit", "#CreateProduct:/show/id/" + t.getId());

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
				if(t.getId() != null)
					return new HTML("id: "+t.getId()+", rev: "+t.getRevision());
				else
					return new HTML("Revision is null");
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
