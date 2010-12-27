package org.tagaprice.client.gwt.client.features.productmanagement;

import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductServiceAsync;

import com.google.gwt.user.client.ui.NotificationMole;

public interface ProductServiceDispatch extends IProductServiceAsync {

	public void setMole(NotificationMole mole);

}
