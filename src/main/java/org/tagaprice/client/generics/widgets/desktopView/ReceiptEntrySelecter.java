package org.tagaprice.client.generics.widgets.desktopView;

import java.util.ArrayList;
import org.tagaprice.client.generics.widgets.IReceiptEntrySelecter;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReceiptEntrySelecter extends Composite implements IReceiptEntrySelecter {

	private VerticalPanel _vePa1 = new VerticalPanel();
	
	public ReceiptEntrySelecter() {
		_vePa1.setWidth("500px");
		initWidget(_vePa1);
	}
	
	

	@Override
	public void setReceiptEntries(ArrayList<ReceiptEntry> receiptEntries) {		
		_vePa1.clear();
		for(ReceiptEntry re:receiptEntries){
			addReceiptEntrie(re);
		}

	}

	@Override
	public void addReceiptEntrie(ReceiptEntry receiptEntry) {
		final HorizontalPanel _hoPaRe = new HorizontalPanel();
		_hoPaRe.setWidth("100%");
		ReceiptEntryPreview reEnt = new ReceiptEntryPreview(receiptEntry);
		reEnt.setReadOnly(false);
		_hoPaRe.add(reEnt);
		
		Button min = new Button("-", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_hoPaRe.removeFromParent();
			}
		});
		_hoPaRe.add(min);
		_hoPaRe.setCellWidth(min, "30px");
		
		_vePa1.add(_hoPaRe);
	}

	@Override
	public ArrayList<ReceiptEntry> getReceiptEntries() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
