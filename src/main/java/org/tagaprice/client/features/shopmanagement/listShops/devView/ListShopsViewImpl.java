package org.tagaprice.client.features.shopmanagement.listShops.devView;

import java.util.List;

import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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


		for(final Shop s: data){
			Label text = new Label(s.getTitle());
			text.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					presenter.goTo(new CreateShopPlace(s.getId(), null, null, null, null));
				}
			});
			_table.add(text);
		}

	}

}