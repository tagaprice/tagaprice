package org.tagaprice.client.generics.widgets.desktopView;

import java.math.BigDecimal;

import org.tagaprice.client.generics.widgets.CurrencySelecter;
import org.tagaprice.client.generics.widgets.IMorphWidget.Type;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ReceiptEntryPreview extends PackagePreview {

	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private MorphWidget _price = new MorphWidget();
	private CurrencySelecter _currency = new CurrencySelecter();
	private boolean _readonly = true;
	private ReceiptEntry _receiptEntry;
	
	
	public ReceiptEntryPreview(ReceiptEntry receiptEntry) {
		super(receiptEntry.getPackage().getProduct(), receiptEntry.getPackage());
		_receiptEntry=receiptEntry;
		
		//price
		_price.config(true, false);
		_price.setValue(receiptEntry.getPrice().getPrice().toPlainString());
		_price.setWidth("50px");
		_hoPa1.add(_price);
		
		//Currency
		_currency.setCurrency(receiptEntry.getPrice().getCurrency());
		_hoPa1.add(_currency);
		
		_vePaPricePack.add(_hoPa1);
	}
	
	public void addChangeHandler(ChangeHandler handler){
		_price.addChangeHandler(handler);
	}

	public boolean isReadOnly() {
		return _readonly;
	}
	
	public void setReadOnly(boolean read) {
		_readonly=read;
		
		_price.setReadOnly(_readonly);
		
		
		if(_receiptEntry.getPackage().getQuantity().getQuantity().equals(new BigDecimal("0.0")) || _receiptEntry.getPackageId()==null){
			super.setReadOnly(_readonly);
		}
		
	}
	
	public ReceiptEntry getReceiptEntry(){
		_receiptEntry.setPrice(new Price(new BigDecimal(_price.getValue()), _currency.getCurrency()));
		_receiptEntry.getPackage().setQuantity(getPackage().getQuantity());
		
		return _receiptEntry;
	}
}
