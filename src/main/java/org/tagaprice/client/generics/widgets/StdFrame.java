package org.tagaprice.client.generics.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

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
	public void setHeader(Widget header) {
		stdFrame.setHeader(header);		
	}

	@Override
	public void setBody(Widget body) {
		stdFrame.setBody(body);
	}


	@Override
	public void setButtonsVisible(boolean visible) {
		stdFrame.setButtonsVisible(visible);
	}

	@Override
	public void addCancleClickHandler(ClickHandler handler) {
		stdFrame.addCancleClickHandler(handler);
	}

	@Override
	public void addSaveClickHandler(ClickHandler handler) {
		stdFrame.addSaveClickHandler(handler);
	}

	@Override
	public void addEditClickHandler(ClickHandler handler) {
		stdFrame.addEditClickHandler(handler);
	}

	@Override
	public void setReadOnly(boolean readonly) {
		stdFrame.setReadOnly(readonly);
	}

	@Override
	public void setBody(Widget body, String width) {
		stdFrame.setBody(body, width);
	}

}
