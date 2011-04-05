package org.tagaprice.client.features.productmanagement.createProduct.devView;

import java.util.ArrayList;

import org.tagaprice.client.features.productmanagement.createProduct.I18N;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.PackageSelecter;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.*;
import org.tagaprice.shared.entities.productmanagement.IPackage;

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
public class CreateProductViewImpl extends Composite implements ICreateProductView{
	interface CreateProductViewImplUiBinder extends
	UiBinder<Widget, CreateProductViewImpl> {
	}

	private static CreateProductViewImplUiBinder uiBinder = GWT
	.create(CreateProductViewImplUiBinder.class);

	//TODO replace with IPackageWidget
	ArrayList<IPackage> _iPackage = new ArrayList<IPackage>();

	private Presenter _presenter;

	@UiField
	Label nameI18N;

	@UiField
	Label categoryI18N;

	@UiField
	Label id;
	@UiField
	TextBox name;

	@UiField
	UnitSelecter _unit;


	@UiField
	CategorySelecter category;


	@UiField
	Button saveButton;

	@UiField
	PackageSelecter _packages;

	public CreateProductViewImpl() {
		this.initWidget(CreateProductViewImpl.uiBinder.createAndBindUi(this));

		//Set I18N
		nameI18N.setText(I18N.I18N.name());
		categoryI18N.setText(I18N.I18N.category());
	}

	@UiHandler("saveButton")
	public void onSaveButtonClicked(ClickEvent event) {
		_presenter.onSaveEvent();
	}




	@Override
	public void setCategory(ICategory category) {
		this.category.setCategory(category);
	}

	@Override
	public ICategory getCategory() {
		return this.category.getCategory();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	@Override
	public void setTitle(String title) {
		name.setText(title);
	}

	@Override
	public void setAvailableCategories(ArrayList<ICategory> categories) {
		this.category.setAvailableCategories(categories);
	}

	@Override
	public String getProductTitle() {
		return this.name.getText();
	}


	@Override
	public void setRevisionId(IRevisionId revisionId) {
		this.id.setText(revisionId.toString());

	}

	@Override
	public void addPackage(IPackage ipackage) {
		_iPackage.add(ipackage);
		_packages.setPackages(_iPackage);
	}

	@Override
	public void setPackages(ArrayList<IPackage> iPackage) {
		_iPackage.clear();

		_iPackage.addAll(iPackage);

		_packages.setPackages(_iPackage);
	}

	@Override
	public ArrayList<IPackage> getPackages() {
		return _packages.getPackages();
	}

	@Override
	public void setUnit(Unit unit) {
		_unit.setUnit(unit);
		_packages.setRelatedUnit(unit);

	}

	@Override
	public Unit getUnit() {
		return _unit.getUnit();
	}



}
