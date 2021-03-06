package org.tagaprice.shared.entities.searchmanagement;

import java.util.Date;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.entities.productmanagement.Package;

public class StatisticResult extends Document {

	private static final long serialVersionUID = 1L;

	private Date _date;
	private Shop _shop;
	private Category _shopCategory;
	private Product _product;
	private Category _productCategory;
	private Price _price;
	private Package _package;
	private String _receiptId;
	private Long _seqNr = null;
	private Unit _unit = null;
	private Unit _parentUnit = null;


	public StatisticResult() {}

	
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
	@JSONProperty(ignore=true)
	public Date getDate() {
		return _date;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Long getTimestamp() {
		return _date != null ? _date.getTime() : null;
	}


	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return _shop;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Category getShopCategory() {
		return _shopCategory;
	}


	/**
	 * @return the product
	 */
	public Product getProduct() {
		return _product;
	}

	@JSONProperty(ignoreIfNull=true)
	public Category getProductCategory() {
		return _productCategory;
	}


	/**
	 * @return the price
	 */
	public Price getPrice() {
		return _price;
	}
	
	/**
	 * Returns the ReceiptID this {@link StatisticResult} is associated to.
	 * 
	 * This method is required for the StatisticAggregator to work and can be ignored everywhere else.
	 * @return Receipt ID
	 */
	public String getReceiptId() {
		return _receiptId;
	}
	
	/**
	 * Returns the sequence number this StatisticResult was last updated with
	 * @return sequence number or null if it wasn't specified
	 */
	public Long getSequenceNr() {
		return _seqNr;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Unit getUnit() {
		return _unit;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Unit getParentUnit() {
		return _parentUnit;
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
	
	public void setShopCategory(Category category) {
		_shopCategory = category;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		_product = product;
	}
	
	public void setProductCategory(Category category) {
		_productCategory = category;
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

	/**
	 * Set a new receipt ID (necessary for the CouchDB statistics aggregation to work (can be ignored everywhere else)
	 * @param receiptId New Receipt ID
	 */
	public void setReceiptId(String receiptId) {
		_receiptId = receiptId;
	}
	
	public void setSequenceNr(Long sequenceNr) {
		_seqNr = sequenceNr;
	}
	
	public void setTimestamp(long timestamp) {
		_date = new Date(timestamp);
	}

	public void setUnit(Unit unit) {
		_unit = unit;
	}
	
	public void setParentUnit(Unit unit) {
		_parentUnit = unit;
	}
}
