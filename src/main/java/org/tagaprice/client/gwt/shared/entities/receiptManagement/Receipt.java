package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.util.ArrayList;
import java.util.Date;
import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.User;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

/**
 * A single Receipt
 * Holds information about the creator {@link User} of the receipt,
 * the date when the receipt was created, a reference to the shop {@link Shop} where the receipt is from
 * and all {@link ReceiptEntry} of a receipt.
 * 
 * @author Helga Weik (kaltra)
 *
 */
public class Receipt extends AEntity<IReceipt> implements IReceipt {



	private static final long serialVersionUID = -1411130663050015079L;

	private Date _date;
	private IAddress _address;
	private User _user;
	private ArrayList<IReceiptEntry> _receiptEntries = new ArrayList<IReceiptEntry>();


	public Receipt() {
		super();

	}

	/**
	 * Create new Receipt. Used on the Client
	 * @param title the title of the receipt
	 * @param date date of the receipt
	 * @param address the shop-address
	 * @param receiptEntries all entries plus price
	 */
	public Receipt(String title, Date date, IAddress address, ArrayList<IReceiptEntry> receiptEntries) {
		setTitle(title);
		setDate(date);
		setAddress(address);
		setReceiptEntries(receiptEntries);
	}


	/**
	 * Update or select Receipt. Used on Client and Server
	 * @param title
	 * @param id
	 * @param date the date and time when a receipt was created
	 * @param shop {@link Shop} where the receipt is from
	 * @param user {@link User} who created the receipt
	 * @param receiptEntries  {@link ReceiptEntry}
	 */

	public Receipt(String title, Long id, Date date, IAddress address, User user, ArrayList<IReceiptEntry> receiptEntries) {
		setTitle(title);
		setDate(date);
		setAddress(address);
		setUser(user);
		setReceiptEntries(receiptEntries);
	}


	@Override
	public Date getDate() {
		return _date;
	}

	@Override
	public void setDate(Date date) {
		_date = date;
	}


	public IRevisionId getShopId(){
		return getAddress().getRevisionID();
	}

	public void setShopId(IRevisionId shopId){
		// TODO
	}

	@Override
	public User getUser() {
		return _user;
	}

	@Override
	public void setUser(User user) {
		_user = user;
	}

	@Override
	public ArrayList<IReceiptEntry> getReceiptEntries() {
		return _receiptEntries;
	}

	@Override
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries) {
		_receiptEntries.clear();
		_receiptEntries.addAll(receiptEntries);
	}



	@Override
	public IReceipt copy() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public IAddress getAddress() {
		return _address;
	}

	@Override
	public void setAddress(IAddress address) {
		_address=address;
	}





}
