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
 * Displays the date in a nice human readable style and displays a datePicker
 * (see: <a target="_blank" href="http://code.google.com/webtoolkit/doc/1.6/RefWidgetGallery.html">DatePicker</a>)
 * onclick.
 * 
 */
public class DateWidget extends Composite {
	private Date _date = new Date();
	private Label _day = new Label();
	private DateTimeFormat _dayDtF = DateTimeFormat.getFormat("d");
	private Label _monthYear = new Label();
	private DateTimeFormat _monthYearDtF = DateTimeFormat.getFormat("MMM ''yy");
	private DatePicker _picker = new DatePicker();
	private PopupPanel _pickerPop = new PopupPanel(true);
	private VerticalPanel _vePa = new VerticalPanel();

	/**
	 * Create a DateWidget with the current date. Use {@link #DateWidget(Date)} 
	 * for a different date.
	 */
	public DateWidget() {
		initWidget(_vePa);

		_monthYear.setText(_monthYearDtF.format(_date));
		_day.setText(_dayDtF.format(_date));

		_monthYear.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_pickerPop.showRelativeTo(_day);

			}
		});

		_day.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_pickerPop.showRelativeTo(_day);

			}
		});

		_pickerPop.setWidget(_picker);

		_picker.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				_monthYear.setText(_monthYearDtF.format(event.getValue()));
				_day.setText(_dayDtF.format(event.getValue()));
				_date.setTime(event.getValue().getTime());
				_pickerPop.hide();
			}
		});

		// Month year
		_vePa.add(_monthYear);
		_vePa.add(_day);

		// Style
		this.setWidth("50px");
		_vePa.setCellHorizontalAlignment(_monthYear,
				HasHorizontalAlignment.ALIGN_CENTER);
		_vePa.setCellHorizontalAlignment(_day,
				HasHorizontalAlignment.ALIGN_CENTER);
		this.setStyleName("DateWidget");
		_monthYear.setStyleName("DateWidget-MonthYear");
		_day.setStyleName("DateWidget-Day");
	}

	/**
	 * Create a DateWidget with a special date.
	 * @param date a special date.
	 */
	public DateWidget(Date date) {
		this();
		setDate(date);
	}

	/**
	 * Returns the current selected date.
	 * @return the current selected date
	 */
	public Date getDate() {
		return this._date;
	}

	/**
	 * Sets a special date.
	 * 
	 * @param date a special date.
	 */
	public void setDate(Date date) {
		this._date = date;
		_monthYear.setText(_monthYearDtF.format(this._date));
		_day.setText(_dayDtF.format(this._date));
		_picker.setValue(date);

	}
}
