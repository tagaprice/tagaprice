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
 * Filename: DateWidget.java
 * Date: 15.05.2010
 */
package org.tagaprice.client.widgets;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * Shows the date in a nice human readable style and shows a datePicker on
 * click.d
 * 
 */

public class DateWidget extends Composite {
	VerticalPanel vePa = new VerticalPanel();
	Label monthYear = new Label();
	Label day = new Label();
	Date date = new Date();
	DatePicker picker = new DatePicker();
	PopupPanel pickerPop = new PopupPanel(true);
	DateTimeFormat monthYearDtF = DateTimeFormat.getFormat("MMM ''yy");
	DateTimeFormat dayDtF = DateTimeFormat.getFormat("d");

	/**
	 * 
	 * @param date
	 */
	public DateWidget(Date date) {
		this();
		setDate(date);
	}

	/**
	 * 
	 */
	public DateWidget() {
		initWidget(vePa);

		monthYear.setText(monthYearDtF.format(date));
		day.setText(dayDtF.format(date));

		monthYear.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pickerPop.showRelativeTo(day);

			}
		});

		day.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pickerPop.showRelativeTo(day);

			}
		});

		pickerPop.setWidget(picker);

		picker.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				monthYear.setText(monthYearDtF.format(event.getValue()));
				day.setText(dayDtF.format(event.getValue()));
				date.setTime(event.getValue().getTime());
				pickerPop.hide();
			}
		});

		// Month year
		vePa.add(monthYear);
		vePa.add(day);

		// Style
		this.setWidth("50px");
		vePa.setCellHorizontalAlignment(monthYear,
				HasHorizontalAlignment.ALIGN_CENTER);
		vePa.setCellHorizontalAlignment(day,
				HasHorizontalAlignment.ALIGN_CENTER);
		this.setStyleName("DateWidget");
		monthYear.setStyleName("DateWidget-MonthYear");
		day.setStyleName("DateWidget-Day");
	}

	/**
	 * Sets the date
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
		monthYear.setText(monthYearDtF.format(this.date));
		day.setText(dayDtF.format(this.date));
		picker.setValue(date);

	}

	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return this.date;
	}
}
