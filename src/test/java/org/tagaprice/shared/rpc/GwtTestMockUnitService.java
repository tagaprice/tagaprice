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
				assertEquals(5,response.size());
				for(Unit r: response){
					assertEquals("testUser_id",r.getCreator().getId());
					assertEquals("testRev",r.getCreator().getRevision());
					assertEquals("Test User",r.getCreator().getTitle());
					assertTrue(
							r.getTitle().contains("st") ||
							r.getTitle().contains("kg") ||
							r.getTitle().contains("g") ||
							r.getTitle().contains("l") ||
							r.getTitle().contains("ml"));
					logger.log("-Unit: "+r.getTitle());
				}

				assertTrue(true);
				finishTest();
			}

			@Override
			public void onFailure(Throwable e) {
				assertTrue(false);
				finishTest();

			}
		});

		delayTestFinish(3000);
	}

	public void testLiterList(){
		final IUnitServiceAsync async = GWT.create(IUnitService.class);



		async.getFactorizedUnits(null, new AsyncCallback<List<Unit>>() {

			@Override
			public void onSuccess(List<Unit> response) {


				assertEquals(5,response.size());

				for(Unit u:response){
					if(u.getTitle().equals("l"))
						async.getFactorizedUnits(u.getId(), new AsyncCallback<List<Unit>>() {

							@Override
							public void onSuccess(List<Unit> response) {
								assertEquals(2,response.size());

								for(Unit u:response){
									if(u.getFactor() == 1000 || u.getFactor() == 0.001){
										assertTrue(true);
									}else{
										assertFalse(false);
									}
								}

								finishTest();
							}

							@Override
							public void onFailure(Throwable e) {
								assertTrue(false);
								finishTest();

							}
						});
				}



			}

			@Override
			public void onFailure(Throwable e) {
				assertTrue(false);
				finishTest();

			}
		});


		delayTestFinish(3000);
	}

}
