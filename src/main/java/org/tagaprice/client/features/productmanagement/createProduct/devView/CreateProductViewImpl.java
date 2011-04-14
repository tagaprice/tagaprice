package org.tagaprice.client.features.productmanagement.createProduct.devView;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.features.productmanagement.createProduct.I18N;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.PackageSelecter;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;

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
	ArrayList<Package> _iPackage = new ArrayList<Package>();

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
	public void setCategory(Category category) {
		this.category.setCategory(category);
	}

	@Override
	public Category getCategory() {
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
	public void setAvailableCategories(List<Category> categories) {
		this.category.setAvailableCategories(categories);
	}

	@Override
	public String getProductTitle() {
		return this.name.getText();
	}



	@Override
	public void addPackage(Package ipackage) {
		_iPackage.add(ipackage);
		_packages.setPackages(_iPackage);
	}

	@Override
	public void setPackages(ArrayList<Package> iPackage) {
		_iPackage.clear();

		_iPackage.addAll(iPackage);

		_packages.setPackages(_iPackage);
	}

	@Override
	public ArrayList<Package> getPackages() {
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

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}



}
