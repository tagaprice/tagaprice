package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateReceiptViewImpl<T> extends Composite implements ICreateReceiptView<T> {

	interface CreateProductViewImplUiBinder extends
	UiBinder<Widget, CreateReceiptViewImpl> {
	}

	private static CreateProductViewImplUiBinder uiBinder = GWT
	.create(CreateProductViewImplUiBinder.class);

	public CreateReceiptViewImpl() {
		this.initWidget(CreateReceiptViewImpl.uiBinder.createAndBindUi(this));
	}

	@Override
	public void addEntry(ArrayList entry) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getProductName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShops(ArrayList shops) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSuggestProducts(ArrayList productList) {
		// TODO Auto-generated method stub

	}

}
