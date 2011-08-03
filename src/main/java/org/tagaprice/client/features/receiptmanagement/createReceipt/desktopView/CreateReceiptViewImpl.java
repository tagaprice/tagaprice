package org.tagaprice.client.features.receiptmanagement.createReceipt.desktopView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.IMorphWidget.Type;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class CreateReceiptViewImpl extends Composite implements ICreateReceiptView {

	private StdFrame _frame = new StdFrame();
	private HorizontalPanel _headPanel = new HorizontalPanel();
	private Label _receiptDateLabel = new Label();
	private Label _fullPrice = new Label("0.0€");
	private DateTimeFormat fmt = DateTimeFormat.getFormat(" [dd, MMMM yyyy]");
	private DatePicker _datePicker = new DatePicker();
	private boolean _readonly = true;
	private PopupPanel _datePop = new PopupPanel(true);
	
	public CreateReceiptViewImpl() {
		
		//head
		_headPanel.setWidth("100%");
		_headPanel.add(new Label("Receipt"));
		
		
		//Date
		_headPanel.add(_receiptDateLabel);
		_headPanel.setCellWidth(_receiptDateLabel, "100%");
		_datePop.setWidget(_datePicker);
		_datePop.setStyleName("popBackground");
		_receiptDateLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_datePop.showRelativeTo(_receiptDateLabel);
			}
		});
		
		_datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> date) {
				setDate(date.getValue());	
				_datePop.hide();
			}
		});

		_headPanel.add(_fullPrice);
		_frame.setHeader(_headPanel);
		
		
		//body
		
		
		initWidget(_frame);
	}
	
	@Override
	public Date getDate() {
		return _datePicker.getValue();
	}

	@Override
	public void setDate(Date date) {
		_receiptDateLabel.setText(fmt.format(date));
		_datePicker.setValue(date,true);
	}

	@Override
	public Shop getShop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShop(Shop shop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShopSearchResults(List<Shop> shopResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProductSearchResults(List<Product> productResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ReceiptEntry> getReceiptEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReceiptEntries(ArrayList<ReceiptEntry> receiptEntries) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		
	}

}
