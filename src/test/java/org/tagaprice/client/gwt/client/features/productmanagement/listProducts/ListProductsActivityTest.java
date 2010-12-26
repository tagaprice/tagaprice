package org.tagaprice.client.gwt.client.features.productmanagement.listProducts;

import java.util.ArrayList;

import org.junit.*;
import org.mockito.Mockito;
import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.*;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.old.ProductCore;
import org.tagaprice.client.gwt.shared.entities.old.ProductCoreImpl;
import org.tagaprice.client.gwt.test.ProductServiceDispatchSuccess;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.*;

public class ListProductsActivityTest {
	private ListProductsView<ProductCore> listProductsView;
	private ClientFactory clientFactory;
	private PlaceController placeController;
	private ListProductsPlace listProductsPlace;
	private ListProductsActivity listProductsActivity;
	private ProductServiceDispatchSuccess productServiceDispatchSuccess;
	private AcceptsOneWidget panel;
	private Widget viewWidget;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		//This disables errors on GWT.create();
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//Enable GWT.create() again
		GWTMockUtilities.restore();
	}

	@Before
	public void setUp() throws Exception {

		this.listProductsView = Mockito.mock(ListProductsView.class);
		//TODO use real object
		this.viewWidget = null;
		this.clientFactory = Mockito.mock(ClientFactory.class);
		this.placeController = Mockito.mock(PlaceController.class);

		this.productServiceDispatchSuccess = new ProductServiceDispatchSuccess();

		//TODO update this for future improvements... have e.g. different ProductPlaces
		this.listProductsPlace = Mockito.mock(ListProductsPlace.class);
		this.panel = Mockito.mock(AcceptsOneWidget.class);

		//setup ClientFactor
		Mockito.when(this.clientFactory.getListProductsView()).thenReturn(this.listProductsView);
		Mockito.when(this.clientFactory.getPlaceController()).thenReturn(placeController);
		Mockito.when(this.clientFactory.getProductServiceDispatch()).thenReturn(productServiceDispatchSuccess);

		Mockito.when(this.listProductsView.asWidget()).thenReturn(this.viewWidget);




		//setup PlaceController
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests calls inside the Constructor
	 */
	@Test
	public void testConstructor() {
		this.listProductsActivity = new ListProductsActivity(this.listProductsPlace, this.clientFactory);

		Mockito.verify(this.clientFactory).getListProductsView();
		Mockito.verify(this.clientFactory).getProductServiceDispatch();
	}

	/**
	 * Tests the start Method.
	 * Is the presenter set? Are the data set? Is the View set?
	 */
	@Test
	public void testOnStartMethod() {
		/*
		 * Expect
		 * - calls on ClientFactory
		 * -- getView
		 * -- getRPCDispatch
		 * --
		 * - calls on View
		 * -- setPresenter
		 * -- setData
		 * - calls on panel
		 * -- setView
		 * - calls on eventBus
		 * -- ??? - leave it null
		 * - calls on Place
		 * -- ???
		 */

		ArrayList<ProductCore> productCores = new ArrayList<ProductCore>();
		productCores.add(new ProductCoreImpl(0, "testProduct"));
		this.productServiceDispatchSuccess.productCores = productCores;
		this.listProductsActivity = new ListProductsActivity(this.listProductsPlace, this.clientFactory);
		this.listProductsActivity.start(panel, null);

		Mockito.verify(this.listProductsView).setPresenter(this.listProductsActivity);
		Mockito.verify(this.listProductsView).setData(productCores);
		Mockito.verify(this.panel).setWidget(this.viewWidget);
	}

	@Test
	public void testOnStopMethod() {

	}

}
