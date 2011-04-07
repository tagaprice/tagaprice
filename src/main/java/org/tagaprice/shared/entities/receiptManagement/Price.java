package org.tagaprice.shared.entities.receiptManagement;

/**
 * Set the price of an {@link IReceiptEntry}
 *
 */
public class Price implements IPrice {
	private static final long serialVersionUID = 1L;

	private int _price;
	private Currency _currency;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Price() {}


	/**
	 * <b>NEW</b>
	 * Create a new price
	 * @param price the price
	 * @param currency the currency
	 */
	public Price(int price, Currency currency){
		setPrice(price);
		setCurrency(currency);
	}

	@Override
	public void setPrice(int price) {
		_price=price;

	}

	@Override
	public void setCurrency(Currency currency) {
		_currency=currency;

	}

	@Override
	public int getPrice() {
		return _price;
	}

	@Override
	public Currency getCurrency() {
		return _currency;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Price [_price=" + _price + ", _currency=" + _currency + "]";
	}



}