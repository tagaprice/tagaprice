package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.shared.entities.Property;
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.entities.PropertyGroup;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Displays all properties defined in a {@link PropertyGroup} and makes them
 * modify able.
 * 
 */
public class ListPropertyHandler extends APropertyHandler {

	private HashMap<String, PropertyTypeDefinition> _definition = new HashMap<String, PropertyTypeDefinition>();
	private TitleWidget _title;
	private VerticalPanel _vePa1 = new VerticalPanel();

	/**
	 * Creates a propertyHandler which displays all properties defined in a
	 * {@link PropertyGroup} and fill the list with the available properties.
	 * 
	 * @param hashProperties
	 *            the currently known properties
	 * @param propGroup
	 *            holds all properties of an actual property
	 * @param handler
	 *            implements the handler that will be called if a property
	 *            change.
	 */
	public ListPropertyHandler(
			HashMap<String, ArrayList<Property>> hashProperties,
			PropertyGroup propGroup, IPropertyChangeHandler handler) {
		super(hashProperties, propGroup, handler);

		_vePa1.setWidth("100%");

		_title = new TitleWidget(propGroup.getTitle(), _vePa1,
				TitleWidget.Headline.H2);
		convertToHash();

		createGrid();
		initWidget(_title);
	}

	/**
	 * Converts PropertyDefinition ArrayList to HashMap. So it is easy to find a
	 * Type
	 * 
	 * @param groupElements
	 */
	private void convertToHash() {
		for (PropertyTypeDefinition pg : getPropertyGroup().getGroupElements()) {
			_definition.put(pg.getName(), pg);
		}
	}

	private void createGrid() {

		for (PropertyTypeDefinition pg : getPropertyGroup().getGroupElements()) {
			if (getPropertyList().get(pg.getName()) == null) {
				getPropertyList().put(pg.getName(),
						new ArrayList<Property>());
			}

			ListPropertyItem temp = new ListPropertyItem(pg, getPropertyList()
					.get(pg.getName()));
			_vePa1.add(temp);

			temp.addChangeHandler(getPropertyChangeHandler());

		}
	}

}
