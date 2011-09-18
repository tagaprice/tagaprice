package org.tagaprice.shared.entities.searchmanagement;

import java.util.Date;

import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.entities.productmanagement.Package;

public class StatisticResult extends Document {

	private static final long serialVersionUID = 1L;

	private Date _date;
	private Shop _shop;
	private Product _product;
	@Deprecated
	private Quantity _quantity;
	private Price _price;
	private Package _package;


	public StatisticResult() {
	}

	@Deprecated
	public StatisticResult(Date date, Shop shop, Product product, Quantity quantity, Price price) {
		super();
		_date = date;
		_shop = shop;
		_product = product;
		_quantity = quantity;
		_price = price;
	}
	
	public StatisticResult(Date date, Shop shop, Product product, Package pack, Price price) {
		super();
		_date = date;
		_shop = shop;
		_product = product;
		_package = pack;
		_price = price;
	}
	
	


	/**
	 * @return the package
	 */
	public Package getPackage() {
		return _package;
	}


	/**
	 * @return the date
	 */
	public Date getDate() {
		return _date;
	}


	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return _shop;
	}


	/**
	 * @return the product
	 */
	public Product getProduct() {
		return _product;
	}


	/**
	 * @return the quantity
	 */
	@Deprecated
	public Quantity getQuantity() {
		return _quantity;
	}


	/**
	 * @return the price
	 */
	public Price getPrice() {
		return _price;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		_date = date;
	}

	/**
	 * @param shop the shop to set
	 */
	public void setShop(Shop shop) {
		_shop = shop;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		_product = product;
	}

	/**
	 * @param quantity the quantity to set
	 */
	@Deprecated
	public void setQuantity(Quantity quantity) {
		_quantity = quantity;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Price price) {
		_price = price;
	}

	/**
	 * @param package1 the package to set
	 */
	public void setPackage(Package package1) {
		_package = package1;
	}



}
