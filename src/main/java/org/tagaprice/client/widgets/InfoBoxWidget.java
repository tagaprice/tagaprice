package org.tagaprice.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays a information box with four different information styles (Success,
 * Information, Waring, Error). If wished the InfoBox will automatically close
 * after x-sec.
 * 
 */
public class InfoBoxWidget extends Composite {

	public enum BoxType {
		/**
		 * Display a ErrorBox (red background)
		 */
		ERRORBOX,
		/**
		 * Display a SuccessBox (green background)
		 */
		SUCCESSBOX,
		/**
		 * Display a WaringBox (orange background)
		 */
		WARNINGBOX,
		/**
		 * Display a InformationBox (blue background)
		 */
		INFORMATIONBOX
	}

	private BoxType boxType;

	private SimplePanel infoBox = new SimplePanel();

	/**
	 * Standard constructor to create a InfoBox. The InfoBoxWidget is hidden at
	 * creating. To set visible use the {@link #showInfo(String, BoxType)} or
	 * {@link #showInfo(Widget, BoxType)} method.
	 * 
	 * @param autohide
	 *            set the InfoBoxWidget automatically to hidden, or displays a
	 *            close button.
	 */
	public InfoBoxWidget(boolean autohide) {
		initWidget(infoBox);
		infoBox.setWidth("100%");
		infoBox.setVisible(false);
	}

	/**
	 * Standard constructor to create a InfoBoxWidget with a text and a BoxType
	 * The InfoBoxWidget is not hidden at creating.
	 * 
	 * @param autohide
	 *            set the InfoBoxWidget automatically to hidden, or displays a
	 *            close button.
	 * @param text
	 *            text that will displayed.
	 * @param boxType
	 *            BoxType (ERRORBOX, SUCCESSBOX, WARNINGBOX, INFORMATIONBOX)
	 */
	public InfoBoxWidget(boolean autohide, String text, BoxType boxType) {
		this(autohide);
		showInfo(text, boxType);
	}

	/**
	 * Standard constructor to create a InfoBoxWidget with a widget and a
	 * BoxType The InfoBoxWidget is not hidden at creating.
	 * 
	 * @param autohide
	 *            set the InfoBoxWidget automatically to hidden, or displays a
	 *            close button.
	 * @param wid
	 *            widget that will displayed.
	 * @param boxType
	 *            BoxType (ERRORBOX, SUCCESSBOX, WARNINGBOX, INFORMATIONBOX)
	 */
	public InfoBoxWidget(boolean autohide, Widget wid, BoxType boxType) {
		this(autohide);
		showInfo(wid, boxType);
	}

	/**
	 * Hide the InfoBoxWidget. (setVisible(false))
	 */
	public void hideInfo() {
		infoBox.setVisible(false);
	}

	/**
	 * Set InfoBoxWidget visible and displays the text with the specific
	 * BoxType.
	 * 
	 * @param text
	 *            text that will displayed.
	 * @param boxType
	 *            BoxType (ERRORBOX, SUCCESSBOX, WARNINGBOX, INFORMATIONBOX)
	 */
	public void showInfo(String text, BoxType boxType) {
		showInfo(new Label(text), boxType);
	}

	/**
	 * Set InfoBoxWidget visible and displays a widget with the specific
	 * BoxType.
	 * 
	 * @param wid
	 *            widget that will displayed.
	 * @param boxType
	 *            type BoxType (ERRORBOX, SUCCESSBOX, WARNINGBOX,
	 *            INFORMATIONBOX)
	 */
	public void showInfo(Widget wid, BoxType boxType) {
		this.boxType = boxType;

		if (this.boxType == BoxType.ERRORBOX) {
			infoBox.setStyleName("InfoBox-Error");
		} else if (this.boxType == BoxType.SUCCESSBOX) {
			infoBox.setStyleName("InfoBox-Success");
		} else if (this.boxType == BoxType.WARNINGBOX) {
			infoBox.setStyleName("InfoBox-Warning");
		} else if (this.boxType == BoxType.INFORMATIONBOX) {
			infoBox.setStyleName("InfoBox-Information");
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
