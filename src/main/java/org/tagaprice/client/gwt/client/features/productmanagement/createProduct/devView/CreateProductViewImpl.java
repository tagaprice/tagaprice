package org.tagaprice.client.gwt.client.features.productmanagement.createProduct.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.gwt.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.gwt.shared.entities.dump.*;

import com.google.gwt.core.client.GWT;
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

	public CreateProductViewImpl() {
		this.initWidget(CreateProductViewImpl.uiBinder.createAndBindUi(this));
	}

	private Presenter presenter;

	@UiField
	Label id;
	@UiField
	TextBox name;
	@UiField
	TextBox price;

	@UiField
	CategorySelecter category;

	@UiField
	Button saveButton;

	@Override
	public void addQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public ICategory getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub

	}



}
