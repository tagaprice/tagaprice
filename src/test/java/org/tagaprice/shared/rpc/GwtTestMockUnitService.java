package org.tagaprice.shared.rpc;

import org.tagaprice.shared.rpc.unitmanagement.IUnitService;
import org.tagaprice.shared.rpc.unitmanagement.IUnitServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public class GwtTestMockUnitService  extends GWTTestCase {


	@Override
	public String getModuleName() {
		return "org.tagaprice.TagAPrice";
	}

	public void testEmptyList(){
		IUnitServiceAsync async = GWT.create(IUnitService.class);

		/*
		async.getFactorizedUnits(null, new AsyncCallback<List<Unit>>() {

			@Override
			public void onSuccess(List<Unit> response) {
				// TODO Auto-generated method stub

				for(Unit r: response)
					System.out.println("-Unit: "+r.getTitle());

				finishTest();
			}

			@Override
			public void onFailure(Throwable arg0) {
				System.out.println("error: at RPC");
				assertTrue(false);
				finishTest();

			}
		});
		 */



		delayTestFinish(60000);
	}

}
