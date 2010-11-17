package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;

import org.tagaprice.client.widgets.HorizontalInfoWidget;
import org.tagaprice.client.widgets.MorphWidget;
import org.tagaprice.client.widgets.IMorphWidgetInfoHandler;
import org.tagaprice.shared.entities.Property;
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Is a simple ListItem used by the {@link ListPropertyHandler}
 *
 */
public class ListPropertyItem extends Composite {
	private PropertyTypeDefinition _definition;
	private  IPropertyChangeHandler _handler;
	private  ArrayList<Property> _propertyData;
	private  VerticalPanel _vePa1 = new VerticalPanel();

	
	/**
	 * Displays one property and opens a info box if one property throws an exception.
	 * @param definition is the definition of the property
	 * @param propertyData is the value of the property
	 */
	public ListPropertyItem(PropertyTypeDefinition definition,
			ArrayList<Property> propertyData) {
		this._definition = definition;
		this._propertyData = propertyData;

		// style
		_vePa1.setWidth("100%");

		initWidget(_vePa1);

		fillItems();
	}

	/**
	 * Implement a change handler that is called if a property has changed.
	 * 
	 * @param handler
	 *            is called if a property has changed.
	 */
	public void addChangeHandler(IPropertyChangeHandler handler) {
		this._handler = handler;
	}

	private void addItem(final Property pdCp) {

		final HorizontalInfoWidget temp = new HorizontalInfoWidget();
		temp.setStyleName("DefaultPropertyHandler");
		final MorphWidget mp = new MorphWidget(pdCp.getValue(),
				_definition.getType(), true);

		// Listen
		mp.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {

			@Override
			public void onEmpty() {
				if (!pdCp.getValue().isEmpty()) {
					pdCp.setValue("");
					_propertyData.remove(pdCp);
					if (!_definition.isUnique()) {
						_vePa1.remove(temp);
					}

					_handler.onSuccess();

				}
				temp.showInfo(false);
			}

			@Override
			public void onError(Datatype errorType) {
				temp.showInfo("Error");
				if (_handler != null)
					_handler.onError();
			}

			@Override
			public void onSuccess(Datatype errorType) {

				// if(!pdCp.getValue().equals(mp.getText()) &&
				// pdCp.getValue().isEmpty() && !mp.getText().isEmpty()){
				if (!pdCp.getValue().equals(mp.getValue())
						&& pdCp.getValue().isEmpty()) {

					if (!_definition.isUnique()) {
						// propertyData.add(pdCp);

						addItem(new Property(_definition.getName(),
								_definition.getTitle(), "", _definition.getUnit()));

					}
				}

				if (!pdCp.getValue().equals(mp.getValue())) {
					if (pdCp.getValue().isEmpty())
						_propertyData.add(pdCp);

					pdCp.setValue(mp.getValue());

					if (_handler != null)
						_handler.onSuccess();

				}

				temp.showInfo(false);
			}
		});

		Label lTitle = new Label(_definition.getTitle());
		temp.add(lTitle);
		temp.getPanel().setCellWidth(lTitle, "100%");
		temp.add(mp);
		if (_definition.getUnit() != null)
			temp.add(new Label(_definition.getUnit().getTitle()));

		System.out.println("UNIT:" + _definition.getUnit());
		_vePa1.add(temp);
	}

	private void fillItems() {
		for (Property pd : _propertyData) {
			addItem(pd);
			pd.setRead(true);
		}

		if (_propertyData.isEmpty() || !_definition.isUnique()) {
			addItem(new Property(_definition.getName(),
					_definition.getTitle(), "", _definition.getUnit()));

		}
	}

}
