package org.tagaprice.shared.rpc.unitmanagement;

import java.util.List;

import org.tagaprice.shared.entities.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IUnitServiceAsync {

	public void getUnit(String id, AsyncCallback<Unit> callback);
	public void getUnit(String id, String revision, AsyncCallback<Unit> callback);
	public void getFactorizedUnits(String id, AsyncCallback<List<Unit>> callback);
}
