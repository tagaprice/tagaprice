package org.tagaprice.client.widgets;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * The MorphWidget is a special TextBox which is defined by a type (integer,
 * double, string) and calls an ActionListener if the input type is wrong, or is
 * being changed.
 * 
 */
public class MorphWidget extends SimplePanel {

	private IMorphWidgetInfoHandler _handler;
	private TextBox _textBox = new TextBox();
	private Datatype _type;

	public MorphWidget() {}

	/**
	 * Creates a MorphWidget with a value, a special type (integer,
	 * double, string) and defines if the value is edit able.
	 * 
	 * @param value
	 *            the input variable as String (could be empty, Integer, Double,
	 *            String) but NOT NULL
	 * @param type
	 *            the input type. The input will automatically controlled if it
	 *            has this type.
	 * @param isEditable
	 *            set the MorphWidget editable or not
	 */
	public MorphWidget(String value, Datatype type, boolean isEditable) {
		setWidget(_textBox);
		_type = type;
		this.setStyleName("MorphWidget");
		setWidth("140px");

		setText(value);

		_textBox.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				_textBox.setStyleName("MorphWidgetEmpty");
			}
		});

		_textBox.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				setAutoStyle();

				if (!_textBox.getText().isEmpty()) {
					// TypeControll
					if (_type.equals(Datatype.INT)) {
						try {
							Integer.parseInt(_textBox.getText());
							callSuccess(Datatype.INT);
						} catch (NumberFormatException nfe) {
							callError(Datatype.INT);
						}
					} else if (_type.equals(Datatype.DOUBLE)) {
						try {
							Double.parseDouble(_textBox.getText());
							callSuccess(Datatype.DOUBLE);
						} catch (NumberFormatException nfe) {
							callError(Datatype.DOUBLE);
						}
					} else if (_type.equals(Datatype.STRING)) {
						// Do nothing
						callSuccess(Datatype.STRING);
					}
				}

				// If Text is empty and wasn't empty before.
				if (_textBox.getText().isEmpty())
					callEmpty();

			}
		});

	}

	/**
	 * Adds a ChangeHandler to the MorphWidget
	 * 
	 * @param handler
	 *            is called if the value will change.
	 */
	public void addChangeHandler(ChangeHandler handler) {
		_textBox.addChangeHandler(handler);
	}

	/**
	 * Adds a KeyUpHanlder to the MorphWidget
	 * 
	 * @param handler
	 *            is called if a button is up after pressing them.
	 */
	public void addKeyUpHandler(KeyUpHandler handler) {
		_textBox.addKeyUpHandler(handler);
	}

	/**
	 * Adds a MorphWidgetInfoHandler
	 * 
	 * @param handler
	 *            is called if the value changes and it is successful, empty, or
	 *            not successful (error).
	 */
	public void addMorphWidgetInfoHandler(IMorphWidgetInfoHandler handler) {
		_handler = handler;
	}

	/**
	 * Returns the current value as string.
	 * @return returns the current value.
	 */
	public String getValue() {
		return _textBox.getText();
	}

	/**
	 * Sets a value to the MorphWidget, and calls the MorphWidgetInfoHanlder.
	 * @param value sets the MorphWidget value
	 */
	public void setText(String value) {
		_textBox.setText(value);
		if (!value.isEmpty()) {
			_textBox.setStyleName("MorphWidgetNotEmpty");
		} else {
			_textBox.setStyleName("MorphWidgetEmpty");
		}
	}

	/**
	 * Sets the width of the MorphWidget.
	 * @param width MorphWidget width.
	 */
	@Override
	public void setWidth(String width) {
		_textBox.setWidth(width);
	}

	private void callEmpty() {
		if (_handler != null)
			_handler.onEmpty();
	}

	private void callError(Datatype errorType) {
		if (_handler != null)
			_handler.onError(errorType);
	}

	private void callSuccess(Datatype errorType) {
		if (_handler != null)
			_handler.onSuccess(errorType);
	}

	private void setAutoStyle() {
		if (_textBox.getText().isEmpty()) {
			_textBox.setStyleName("MorphWidgetEmpty");
		} else {
			_textBox.setStyleName("MorphWidgetNotEmpty");
		}
	}

}