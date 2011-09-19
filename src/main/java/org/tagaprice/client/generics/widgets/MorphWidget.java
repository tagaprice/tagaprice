package org.tagaprice.client.generics.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class MorphWidget extends Composite implements IMorphWidget {
	
	private IMorphWidget morphWidget = GWT.create(IMorphWidget.class);
	
	public MorphWidget() {
		initWidget(morphWidget.asWidget());
	}

	@Override
	public void setValue(String value) {
		morphWidget.setValue(value);
	}



	@Override
	public String getValue() {
		return morphWidget.getValue();
	}

	@Override
	public void setReadOnly(boolean read) {
		morphWidget.setReadOnly(read);
	}

	@Override
	public boolean isReadOnly() {
		return morphWidget.isReadOnly();
	}

	@Override
	public void config(boolean alignRight, boolean isHeadline) {
		morphWidget.config(alignRight, isHeadline);
		
	}
}
