package org.tagaprice.client.widgets;

import org.tagaprice.client.ImageBundle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Is a special VerticalList with selection buttons. With this buttons it's
 * possible for a user to select a special Widget in the list.
 * 
 */
public class SelectiveListWidget extends Composite {

	public enum SelectionType {
		/**
		 * Displays a minus button
		 */
		MINUSBUTTON,

		/**
		 * Displays a plus button
		 */
		PLUSBUTTON,

		/**
		 * Displays no button (like a normal VerticalPanel)
		 */
		NOBUTTON
	}

	private SelectionType _selectionType;
	private ImageResource _bottomImage;
	private ISelectiveListHandler _externalHandler;
	private ImageResource _topImage;
	private VerticalPanel _vePa1 = new VerticalPanel();

	/**
	 * Creates a SelectiveListWidget with a special SelectionType
	 * 
	 * @param selectionType
	 *            defines the displayed button.
	 */
	public @UiConstructor
	SelectiveListWidget(SelectionType selectionType) {
		initWidget(_vePa1);
		_vePa1.setWidth("100%");
		_selectionType = selectionType;

		if (_selectionType.equals(SelectionType.PLUSBUTTON)) {
			_topImage = (ImageBundle.INSTANCE.plusActive());
			_bottomImage = (ImageBundle.INSTANCE.plusInactive());
		} else if (_selectionType.equals(SelectionType.MINUSBUTTON)) {
			_topImage = (ImageBundle.INSTANCE.minusActive());
			_bottomImage = (ImageBundle.INSTANCE.minusInactive());
		} else if (_selectionType.equals(SelectionType.NOBUTTON)) {
			// Do nothing here!
		}

	}

	/**
	 * Add a widget at the end of the list plus a SelectionType at the right
	 * side.
	 * 
	 * @param widget
	 *            every widget
	 */
	public void add(final Widget widget) {
		final HorizontalPanel hoPa = new HorizontalPanel();
		hoPa.setWidth("100%");

		if (!_selectionType.equals(SelectionType.NOBUTTON)) {
			PushButton puBa = new PushButton(new Image(_topImage), new Image(
					_bottomImage));

			// insert Button
			hoPa.add(puBa);
			hoPa.setCellWidth(puBa, ImageBundle.INSTANCE.minusActive()
					.getWidth() + "px");
			hoPa.setCellVerticalAlignment(puBa,
					HasVerticalAlignment.ALIGN_MIDDLE);

			// handler
			puBa.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					if (_externalHandler != null)
						_externalHandler.onClick(widget,
								_vePa1.getWidgetIndex(hoPa));

				}
			});
		}

		hoPa.add(widget);
		_vePa1.add(hoPa);

	}

	/**
	 * Implements a selection handler at the SelectionType Button
	 * 
	 * @param handler
	 *            is called if the SelectionType button was pressed.
	 */
	public void addSelectiveListHandler(ISelectiveListHandler handler) {
		_externalHandler = handler;
	}

	/**
	 * Clear the full SelectiveListWidget.
	 */
	public void clear() {
		_vePa1.clear();
	}

	/**
	 * Returns the widget at the index position. 
	 * @param index position of the widget
	 * @return the widget at the index position
	 */
	public Widget getWidget(int index) {
		return ((HorizontalPanel) _vePa1.getWidget(index)).getWidget(1);
	}

	/**
	 * Return the count of all widgets in the SelectiveListWidget.
	 * @return return the count of all widgets in the SelectiveListWidget.
	 */
	public int getWidgetCount() {
		return _vePa1.getWidgetCount();
	}

	/**
	 * Remove a widget at the index position. 
	 * @param index removes the widget at this position. Index must not be negative.
	 */
	public void removeWidget(int index) {
		_vePa1.remove(index);
	}
}
