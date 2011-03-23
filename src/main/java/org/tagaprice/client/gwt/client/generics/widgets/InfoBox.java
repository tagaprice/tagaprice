package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.client.generics.events.InfoBoxEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;


/**
 * This is just a wrapper!
 *
 */
public class InfoBox extends Composite implements IInfoBox {

	private IInfoBox infoBox = GWT.create(IInfoBox.class);

	public InfoBox() {
		initWidget(infoBox.asWidget());
	}

	@Override
	public void addInfoBoxEvent(InfoBoxEvent event) {
		infoBox.addInfoBoxEvent(event);
	}

}
