package org.tagaprice.shared.entities.receiptManagement;

import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.shared.entities.IEntity;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;



public interface IReceipt extends IEntity<IReceipt> {

	public Date getDate();

	public void setDate(Date date);

	public ISubsidiary getAddress();

	public void setAddress(ISubsidiary address);

	public User getUser();

	public void setUser(User user);

	public ArrayList<IReceiptEntry> getReceiptEntries();

	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries);

	public void addReceiptEntriy(IReceiptEntry receiptEntry);

}