/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: TitelPanel.java
 * Date: 15.05.2010
 */
package org.tagaprice.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Creates a widget container with standard title style for the whole project.
 * 
 */
public class TitleWidget extends Composite {
	public enum Headline {
		/**
		 * Headline 1
		 */
		H1, 
		/**
		 * Headline 2
		 */
		H2, 
		/**
		 * Headline 3
		 */
		H3
	}
	private Label _label = new Label();
	private SimplePanel _siPa = new SimplePanel();
	private VerticalPanel _vePa1 = new VerticalPanel();

	/**
	 * Create a TitleWidget with a title at the top, with the Headline and a inner Widget 
	 * @param title the title of the panel
	 * @param widget the inner widget
	 * @param level the type of the headline
	 */
	public TitleWidget(String title, Widget widget, Headline level) {
		_vePa1.setWidth("100%");
		initWidget(_vePa1);

		// Style
		setStyleName("TitlePanel");

		// Title
		_label.setText(title);
		if (level.equals(Headline.H1)) {
			_label.setStyleName("TitlePanel-Title-H1");
		} else if (level.equals(Headline.H2)) {
			_label.setStyleName("TitlePanel-Title-H2");
		} else if (level.equals(Headline.H3)) {
			_label.setStyleName("TitlePanel-Title-H3");
		}

		_vePa1.add(_label);

		// widget
		_siPa.setWidth("100%");
		_vePa1.add(_siPa);
		_siPa.setWidget(widget);
	}

	/**
	 * Sets a new title and widget
	 * @param title the title of the panel
	 * @param widget the inner widget
	 */
	public void setTitleWidget(String title, Widget widget) {
		_label.setText(title);
		_siPa.setWidget(widget);
	}
}
