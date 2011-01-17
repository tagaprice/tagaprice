package org.tagaprice.client.gwt.client.features.productmanagement.createProduct.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.I18N;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.gwt.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.QuantitySelecter;
import org.tagaprice.client.gwt.shared.entities.dump.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
/**
 * 
 * The Class EditProduct uses UIBinder and the template EditProductViewImpl.ui.xml
 * @author Helga Weik (kaltra)
 *
 */
public class CreateProductViewImpl extends Composite implements ICreateProductView{
	interface CreateProductViewImplUiBinder extends
	UiBinder<Widget, CreateProductViewImpl> {
	}

	private static CreateProductViewImplUiBinder uiBinder = GWT
	.create(CreateProductViewImplUiBinder.class);

	private Presenter _presenter;

	public CreateProductViewImpl() {
		this.initWidget(CreateProductViewImpl.uiBinder.createAndBindUi(this));

		//Set I18N
		nameI18N.setText(I18N.I18N.name());
		quantityI18N.setText(I18N.I18N.quantity());
		categoryI18N.setText(I18N.I18N.category());


		//Implement listener
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent(arg0);
			}
		});

	}



	@UiField
	Label nameI18N;
	@UiField
	Label quantityI18N;
	@UiField
	Label categoryI18N;

	@UiField
	Label id;
	@UiField
	TextBox name;
	@UiField
	QuantitySelecter _quantity;

	@UiField
	CategorySelecter category;


	@UiField
	Button saveButton;

	@Override
	public void setQuantity(IQuantity quantity) {
		_quantity.setQuantity(quantity);

	}

	@Override
	public void addQuantities(ArrayList<IQuantity> quantities) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<IQuantity> getQuantities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCategory(ICategory category) {
		this.category.setCategory(category);
	}

	@Override
	public ICategory getCategory() {
		return null;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	@Override
	public void setTitle(String title) {
		name.setText(title);
	}



}
