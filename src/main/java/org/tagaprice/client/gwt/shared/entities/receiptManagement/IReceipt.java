package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.util.Date;
import java.util.Set;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.dump.User;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;



public interface IReceipt extends IEntity<IReceipt> {

	public abstract Date getDate();

	public abstract void setDate(Date date);

	public abstract IAddress getAddress();

	public abstract void setAddress(IAddress address);

	public abstract User getUser();

	public abstract void setUser(User user);

	public abstract Set<IReceiptEntry> getReceiptEntries();

	public abstract void setReceiptEntries(Set<IReceiptEntry> receiptEntries);

}