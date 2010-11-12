package org.tagaprice.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Displays the progress of a shop or product, by surrounding the product/shop
 * image.
 * 
 */
public class ProgressWidget extends Composite {
	private Image _image;
	private int _progress;
	private SimplePanel simplePanel = new SimplePanel();

	/**
	 * Creates a progressWidget with an inner Image.
	 * 
	 * @param image
	 *            the image in the the center of the ProgressWidget
	 * @param progress
	 *            is the progress a product/shop has
	 */
	public ProgressWidget(Image image, int progress) {
		initWidget(simplePanel);
		this._image = image;
		this._progress = progress;

		simplePanel.setWidget(this._image);
	}
}
