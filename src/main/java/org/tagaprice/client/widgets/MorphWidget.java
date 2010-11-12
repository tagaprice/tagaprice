package org.tagaprice.client.widgets;

import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * The MorphWidget is a special TextBox which is defined by a type (integer,
 * double, string) and calls an ActionListener if the input type is wrong, or is
 * being changed.
 * 
 */
public class MorphWidget extends SimplePanel {

	private MorphWidgetInfoHandler _handler;
	private TextBox _textBox = new TextBox();
	private Datatype _type;

	/**
	 * Creates a MorphWidget with a text (input value), a special type (integer,
	 * double, string) and defines if the text is editable.
	 * 
	 * @param text
	 *            the input variable as String (could be empty, Integer, Double,
	 *            String) but NOT NULL
	 * @param type
	 *            the input type. The input will automatically controlled if it
	 *            has this type.
	 * @param isEditable
	 *            set the MorphWidget editable or not
	 */
	public MorphWidget(String text, Datatype type, boolean isEditable) {
		setWidget(_textBox);
		_type = type;
		this.setStyleName("MorphWidget");
		setWidth("140px");

		setText(text);

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
	 * 
	 * @param handler
	 */
	public void addChangeHandler(ChangeHandler handler) {
		_textBox.addChangeHandler(handler);
	}

	public void addKeyUpHandler(KeyUpHandler handler) {
		_textBox.addKeyUpHandler(handler);
	}

	/**
	 * 
	 * @param eHandler
	 */
	public void addMorphWidgetInfoHandler(MorphWidgetInfoHandler eHandler) {
		_handler = eHandler;
	}

	/**
	 * 
	 * @return
	 */
	public String getText() {
		return _textBox.getText();
	}

	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		_textBox.setText(text);
		if (!text.isEmpty()) {
			_textBox.setStyleName("MorphWidgetNotEmpty");
		} else {
			_textBox.setStyleName("MorphWidgetEmpty");
		}
	}

	/**
	 * 
	 */
	public void setWidth(String width) {
		_textBox.setWidth(width);
		// label.setWidth(width);
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