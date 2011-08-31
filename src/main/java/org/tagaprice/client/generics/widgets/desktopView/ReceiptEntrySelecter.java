package org.tagaprice.client.generics.widgets.desktopView;

import java.util.ArrayList;
import java.util.List;

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
		//_vePa1.setWidth("500px");
		initWidget(_vePa1);
	}
	
	

	@Override
	public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {		
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
		reEnt.setWidth("500px");
		_hoPaRe.add(reEnt);
		
		Button min = new Button("remove", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_hoPaRe.removeFromParent();
			}
		});
		min.setStyleName("stdButton cancel");
		_hoPaRe.add(min);
		_hoPaRe.setCellWidth(min, "30px");
		
		_vePa1.add(_hoPaRe);
	}

	@Override
	public ArrayList<ReceiptEntry> getReceiptEntries() {
		ArrayList<ReceiptEntry> returnList = new ArrayList<ReceiptEntry> ();
		
		for(int i=0;i<_vePa1.getWidgetCount();i++){
			returnList.add(((ReceiptEntryPreview)((HorizontalPanel)_vePa1.getWidget(i)).getWidget(0)).getReceiptEntry());
		}
		
		return returnList;
	}
	
	

}
