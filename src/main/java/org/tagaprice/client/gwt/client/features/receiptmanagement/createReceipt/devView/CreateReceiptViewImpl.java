package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView;

import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class CreateReceiptViewImpl extends Composite implements ICreateReceiptView {
	interface CreateReceiptViewImplUiBinder extends UiBinder<Widget, CreateReceiptViewImpl>{}
	private static CreateReceiptViewImplUiBinder uiBinder = GWT.create(CreateReceiptViewImplUiBinder.class);

	private Presenter _presenter;

	public CreateReceiptViewImpl() {
		initWidget(CreateReceiptViewImpl.uiBinder.createAndBindUi(this));
	}

	@UiField
	TextBox _title;

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;

	}

	@Override
	public void setTitle(String title) {
		_title.setText(title);

	}

	@Override
	public String getTitle() {
		return _title.getText();
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public IAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(IAddress address) {
		// TODO Auto-generated method stub

	}


	@Override
	public ArrayList<IReceiptEntry> getReceiptEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries) {
		// TODO Auto-generated method stub

	}




}
