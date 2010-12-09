package org.tagaprice.client.gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
/**
 * 
 * The Class EditProduct uses UIBinder and the template EditProductViewImpl.ui.xml
 * @author Helga Weik (kaltra)
 *
 */
public class EditProductViewImpl extends Composite implements EditProductView {
	interface EditProductViewImplUiBinder extends
			UiBinder<Widget, EditProductViewImpl> {
	}

	private static EditProductViewImplUiBinder uiBinder = GWT
			.create(EditProductViewImplUiBinder.class);

	public EditProductViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	private Presenter presenter;

	@UiField
	TextBox id;
	@UiField
	TextBox name;
	@UiField
	TextBox price;
	@UiField
	TextBox description;
	@UiField
	TextBox category;

	@UiField
	Button saveButton;

	@Override
	public void setName(String name) {
		this.name.setText(name);
	}

	@Override
	public void setDescription(String description) {
		this.description.setText(description);

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setId(int id) {
		this.id.setText(String.valueOf(id));
	}

	@Override
	public int getId() {
		int id = 0;
		try {
			id = Integer.valueOf(this.id.getText());
			return id;
		} catch (NumberFormatException nfe) {
			// TODO ExceptionHandling
			// TODO return type
			return id;
		}
	}

	@Override
	public String getName() {
		return this.name.getText();
	}

	@Override
	public String getDescription() {
		return this.description.getText();
	}

	@Override
	public void setCategory(String category) {
		this.category.setText(category);
	}

	@Override
	public String getCategory() {
		return this.category.getText();
	}
/**
 * delegates the "click" to the presenter which saves the entry made by a user
 * @param event
 */
	@UiHandler("saveButton")
	public void onSaveButtonClicked(ClickEvent event) {
		this.presenter.onSaveButtonClicked(event);
	}
/**
 * delegates the "click" to the presenter which cancels an action
 * 
 * @param event
 */
	@UiHandler("cancelButton")
	public void onCancelButtonClicked(ClickEvent event) {
		this.presenter.onCancelButtonClicked(event);
	}

	@Override
	public void setPrice(int price) {
		this.price.setText(String.valueOf(price));
	}
/**
 * ?
*/
	@Override
	public int getPrice() {
		int price = 0;
		try {
			price = Integer.valueOf(this.price.getText());
			return price;
		} catch (NumberFormatException nfe) {
			// TODO ExceptionHandling
			// TODO return type
			return price;
		}
	}

}
