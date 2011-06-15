package org.tagaprice.client.features.productmanagement.listProducts.devView;

import java.util.List;

import org.tagaprice.client.features.productmanagement.listProducts.ListProductsView;
import org.tagaprice.shared.entities.productmanagement.Product;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * The Class ListProductsViewImpl<T> uses UIBinder and the template ListProductsViewImpl.ui.xml
 * 
 * 
 * @param <T>
 */
public class ListProductsViewImpl extends Composite implements ListProductsView {
	interface ListProductsViewImplUiBinder extends UiBinder<Widget, ListProductsViewImpl> {}
	private static ListProductsViewImplUiBinder uiBinder = GWT.create(ListProductsViewImplUiBinder.class);



	Presenter presenter;

	public ListProductsViewImpl() {
		initWidget(ListProductsViewImpl.uiBinder.createAndBindUi(this));
	}


	@Override
	public void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
	}


	@UiField
	VerticalPanel _table;



	@Override
	public void setData(List<Product> data) {
		_table.clear();

		for(Product p:data)
			_table.add(new HTML("<a href=\"#CreateProduct:/show/id/"+p.getId()+"\">"+p.getTitle()+"</a>"));

	}

}