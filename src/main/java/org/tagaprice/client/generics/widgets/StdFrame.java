package org.tagaprice.client.generics.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * This is just a wrapper!
 *
 */
public class StdFrame extends Composite implements IStdFrame {

	private IStdFrame stdFrame = GWT.create(IStdFrame.class);
	
	public StdFrame() {
		initWidget(stdFrame.asWidget());
	}
	
	@Override
	public void setHeader(IsWidget header) {
		stdFrame.setHeader(header);		
	}

	@Override
	public void setBody(IsWidget body) {
		stdFrame.setBody(body);
	}

}
