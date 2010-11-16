package org.tagaprice.client.pages.previews;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;

/**
 * An abstract PagePreview class that implements all necessary methods for a
 * PagePreview
 * 
 */
public abstract class APagePreview extends Composite {

	public APagePreview() {

		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				click();
			}
		});
	}

	public abstract void click();

	public void addClickHandler(ClickHandler handler) {
		addDomHandler(handler, ClickEvent.getType());
	}

}
