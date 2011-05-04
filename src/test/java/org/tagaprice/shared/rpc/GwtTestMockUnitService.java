package org.tagaprice.shared.rpc;

import java.util.List;

import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.unitmanagement.IUnitService;
import org.tagaprice.shared.rpc.unitmanagement.IUnitServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GwtTestMockUnitService  extends GWTTestCase {
	MyLogger logger = LoggerFactory.getLogger(GwtTestMockUnitService.class);

	@Override
	public String getModuleName() {
		return "org.tagaprice.TagAPrice";
	}

	public void testEmptyList(){
		IUnitServiceAsync async = GWT.create(IUnitService.class);


		async.getFactorizedUnits(null, new AsyncCallback<List<Unit>>() {

			@Override
			public void onSuccess(List<Unit> response) {
				// TODO Auto-generated method stub
				logger.log("onSuccess");
				for(Unit r: response)
					logger.log("-Unit: "+r.getTitle());

				assertTrue(true);
				finishTest();
			}

			@Override
			public void onFailure(Throwable e) {
				logger.log("error: at RPC");
				assertTrue(false);
				finishTest();

			}
		});




		delayTestFinish(60000);
	}

}
