package org.tagaprice.client.gwt.client.dispatch;

import org.tagaprice.client.gwt.client.rpc.ProductServiceAsync;

import com.google.gwt.user.client.ui.NotificationMole;

public interface ProductServiceDispatch extends ProductServiceAsync{

	public void setMole(NotificationMole mole);

}
