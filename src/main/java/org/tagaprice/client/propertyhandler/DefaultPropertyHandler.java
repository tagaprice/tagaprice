package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.shared.PropertyData;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This propertyHandler is responsible to display all not read properties and to
 * make all available properties select able.
 * 
 */
public class DefaultPropertyHandler extends APropertyHandler {

	private TitleWidget _title;
	private VerticalPanel _vePa1 = new VerticalPanel();

	/**
	 * Displays all properties who are in no propertyGroup and which were never
	 * read. Plus creates a option to add all possible properties.
	 * 
	 * @param hashProperties
	 *            the currently known properties
	 * @param handler
	 *            implements the handler that will be called if a property
	 *            change.
	 */
	public DefaultPropertyHandler(
			HashMap<String, ArrayList<PropertyData>> hashProperties,
			IPropertyChangeHandler handler) {
		super(hashProperties, null, handler);

		// Remove Non Used

		_vePa1.setWidth("100%");

		_title = new TitleWidget("Unlisted", _vePa1, TitleWidget.Headline.H2);
		fillGrid();
		initWidget(_title);
	}

	private void fillGrid() {
		for (String ks : getPropertyList().keySet()) {
			for (PropertyData pd : getPropertyList().get(ks)) {
				if (!pd.getRead())
					_vePa1.add(new Label(pd.getTitle() + " | " + pd.getName()
							+ " | " + pd.getValue()));
			}
		}
	}

}
