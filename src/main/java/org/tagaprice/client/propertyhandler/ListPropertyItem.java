package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;

import org.tagaprice.client.widgets.HorizontalInfoWidget;
import org.tagaprice.client.widgets.MorphWidget;
import org.tagaprice.client.widgets.IMorphWidgetInfoHandler;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyDefinition.Datatype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Is a simple ListItem used by the {@link ListPropertyHandler}
 *
 */
public class ListPropertyItem extends Composite {
	PropertyDefinition definition;
	IPropertyChangeHandler handler;
	ArrayList<PropertyData> propertyData;
	VerticalPanel vePa1 = new VerticalPanel();

	
	/**
	 * Displays one property and opens a info box if one property throws an exception.
	 * @param definition is the definition of the property
	 * @param propertyData is the value of the property
	 */
	public ListPropertyItem(PropertyDefinition definition,
			ArrayList<PropertyData> propertyData) {
		this.definition = definition;
		this.propertyData = propertyData;

		// style
		vePa1.setWidth("100%");

		initWidget(vePa1);

		fillItems();
	}

	/**
	 * Implement a change handler that is called if a property has changed.
	 * 
	 * @param handler
	 *            is called if a property has changed.
	 */
	public void addChangeHandler(IPropertyChangeHandler handler) {
		this.handler = handler;
	}

	private void addItem(final PropertyData pdCp) {

		final HorizontalInfoWidget temp = new HorizontalInfoWidget();
		temp.setStyleName("DefaultPropertyHandler");
		final MorphWidget mp = new MorphWidget(pdCp.getValue(),
				definition.getType(), true);

		// Listen
		mp.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {

			@Override
			public void onEmpty() {
				if (!pdCp.getValue().isEmpty()) {
					pdCp.setValue("");
					propertyData.remove(pdCp);
					if (!definition.isUnique()) {
						vePa1.remove(temp);
					}

					handler.onSuccess();

				}
				temp.showInfo(false);
			}

			@Override
			public void onError(Datatype errorType) {
				temp.showInfo("Error");
				if (handler != null)
					handler.onError();
			}

			@Override
			public void onSuccess(Datatype errorType) {

				// if(!pdCp.getValue().equals(mp.getText()) &&
				// pdCp.getValue().isEmpty() && !mp.getText().isEmpty()){
				if (!pdCp.getValue().equals(mp.getValue())
						&& pdCp.getValue().isEmpty()) {

					if (!definition.isUnique()) {
						// propertyData.add(pdCp);

						addItem(new PropertyData(definition.getName(),
								definition.getTitle(), "", definition.getUnit()));

					}
				}

				if (!pdCp.getValue().equals(mp.getValue())) {
					if (pdCp.getValue().isEmpty())
						propertyData.add(pdCp);

					pdCp.setValue(mp.getValue());

					if (handler != null)
						handler.onSuccess();

				}

				temp.showInfo(false);
			}
		});

		Label lTitle = new Label(definition.getTitle());
		temp.add(lTitle);
		temp.getPanel().setCellWidth(lTitle, "100%");
		temp.add(mp);
		if (definition.getUnit() != null)
			temp.add(new Label(definition.getUnit().getTitle()));

		System.out.println("UNIT:" + definition.getUnit());
		vePa1.add(temp);
	}

	private void fillItems() {
		for (PropertyData pd : propertyData) {
			addItem(pd);
			pd.setRead(true);
		}

		if (propertyData.isEmpty() || !definition.isUnique()) {
			addItem(new PropertyData(definition.getName(),
					definition.getTitle(), "", definition.getUnit()));

		}
	}

}
