package org.tagaprice.shared.rpc.unitmanagement;

import java.util.List;

import org.tagaprice.shared.entities.Unit;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("unitservice")
public interface IUnitService extends RemoteService {

	public Unit getUnit(String id);
	public Unit getUnit(String id, String revision);
	public List<Unit> getRelatedUnits(String id);
}
