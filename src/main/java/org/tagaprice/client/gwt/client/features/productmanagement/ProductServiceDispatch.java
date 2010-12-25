package org.tagaprice.client.gwt.client.features.productmanagement;

import org.tagaprice.client.gwt.shared.rpc.productmanagement.old.ProductServiceAsync;

import com.google.gwt.user.client.ui.NotificationMole;

public interface ProductServiceDispatch extends ProductServiceAsync{

	public void setMole(NotificationMole mole);

}
