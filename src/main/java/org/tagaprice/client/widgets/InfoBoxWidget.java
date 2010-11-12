package org.tagaprice.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Shows a success, warning or error info.
 * 
 */
public class InfoBoxWidget extends Composite {
	public enum BoxType {
		ERRORBOX, SUCCESSBOX, WARNINGBOX
	}

	protected BoxType type;

	private SimplePanel infoBox = new SimplePanel();

	public InfoBoxWidget() {
		initWidget(infoBox);
		// infoBox.setSize("100%", "auto");
		infoBox.setWidth("100%");
		infoBox.setVisible(false);
	}

	public void hideInfo() {
		infoBox.setVisible(false);
	}

	public void showInfo(String text, BoxType type) {
		showInfo(new Label(text), type);
	}

	public void showInfo(Widget wid, BoxType type) {
		this.type = type;

		if (this.type == BoxType.ERRORBOX) {
			infoBox.setStyleName("InfoBox-Error");
		} else if (this.type == BoxType.SUCCESSBOX) {
			infoBox.setStyleName("InfoBox-Success");
		} else if (this.type == BoxType.WARNINGBOX) {
			infoBox.setStyleName("InfoBox-Warning");
		} else {
			infoBox.setStyleName("InfoBox-Warning");
		}

		infoBox.setWidget(wid);
		infoBox.setVisible(true);
		/*
		 * Timer t = new Timer() { public void run() {
		 * infoBox.setVisible(false); } };
		 * 
		 * t.schedule(1500);
		 */
	}
}
