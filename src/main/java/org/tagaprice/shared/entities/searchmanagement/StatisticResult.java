package org.tagaprice.shared.entities.searchmanagement;

import java.io.Serializable;
import java.util.Date;

import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.shopmanagement.Shop;

public class StatisticResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date _date;
	private Shop _shop;
	private Product _product;
	private Quantity _quantity;
	private Price _price;


	public StatisticResult() {
		// TODO Auto-generated constructor stub
	}


	public StatisticResult(Date date, Shop shop, Product product, Quantity quantity, Price price) {
		super();
		_date = date;
		_shop = shop;
		_product = product;
		_quantity = quantity;
		_price = price;
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
	public Quantity getQuantity() {
		return _quantity;
	}


	/**
	 * @return the price
	 */
	public Price getPrice() {
		return _price;
	}



}
