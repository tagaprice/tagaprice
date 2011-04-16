package org.tagaprice.client.features.shopmanagement.listShops.devView;

import java.util.List;

import org.tagaprice.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * The Class ListProductsViewImpl<T> uses UIBinder and the template ListProductsViewImpl.ui.xml
 * 
 * 
 * @param <T>
 */
public class ListShopsViewImpl extends Composite implements ListShopsView {

	interface ListProductsViewImplUiBinder extends UiBinder<Widget, ListShopsViewImpl> {}
	private static MyLogger logger = LoggerFactory.getLogger(ListShopsViewImpl.class);
	private static ListProductsViewImplUiBinder uiBinder = GWT.create(ListProductsViewImplUiBinder.class);
	Presenter presenter;


	@UiField
	VerticalPanel _table;



	public ListShopsViewImpl() {
		this.initWidget(ListShopsViewImpl.uiBinder.createAndBindUi(this));
	}





	@Override
	public void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setData(List<Shop> data) {
		_table.clear();


		for(Shop s: data)
			_table.add(new HTML("<a href=\"#CreateShop:/show/id/"+s.getId()+"\">"+s.getTitle()+"</a>"));

	}

}