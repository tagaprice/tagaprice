package org.tagaprice.client.widgets;

import java.util.ArrayList;
import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.ImageBundle;
import org.tagaprice.shared.Type;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Create a type selection widget optimized for finger clicking. It will show
 * the current type of a product and makes it possible to easy change the type.
 * 
 */
// TODO Optimize for finger clicking
public class TypeWidget extends Composite {

	private ITypeWidgetHandler handler;
	private HorizontalPanel hoPa1 = new HorizontalPanel();
	private Image rootB = new Image(ImageBundle.INSTANCE.typeSelectRight());
	private Type type;
	private PopupPanel typeItems = new PopupPanel(true);

	/**
	 * Creates a TypeWidget that will show the current type.s
	 * 
	 * @param type the current type.
	 * @param handler is called if the type has changed.
	 */
	public TypeWidget(Type type, ITypeWidgetHandler handler) {
		this.type = type;
		this.handler = handler;

		// localeId = type.getLocaleId();

		// hoPa1.setWidth("100%");
		initWidget(hoPa1);
		setStyleName("TypeWidget");

		createMenu();
	}

	/**
	 * Create a Type menu with arrows and buttons
	 */
	private void createMenu() {
		// Type
		Type iterType = type;
		hoPa1.clear();

		if (iterType.getSuperType() != null) {
			do {
				final Type innerType = iterType;
				Label typeLabel = new Label(iterType.getTitle());
				typeLabel.setStyleName("TypeWidget-Item");
				hoPa1.insert(typeLabel, 0);
				final Image arrow = new Image(
						ImageBundle.INSTANCE.typeSelectRight());
				// final Button arrow = new Button(">");
				hoPa1.insert(arrow, 1);
				final String sType = iterType.getTitle();

				// Open Product by Type
				typeLabel.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						System.out.println("Open Type: " + sType);

					}
				});

				arrow.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						typeItems.setWidget(new Label("Loading..."));
						RPCHandlerManager.getTypeHandler().getTypeList(
								innerType,
								new AsyncCallback<ArrayList<Type>>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO
										// TaPMng.getInfoBox().showInfo("typeWidget Error",
										// BoxType.WARNINGBOX);
									}

									@Override
									public void onSuccess(ArrayList<Type> result) {

										VerticalPanel vePa1 = new VerticalPanel();
										// vePa1.add(new Button("---"));
										for (final Type ty : result) {
											Label tsb = new Label(ty.getTitle());
											tsb.setStyleName("TypeWidget-Item");
											tsb.addClickHandler(new ClickHandler() {
												@Override
												public void onClick(
														ClickEvent event) {
													handler.onChange(ty);
													typeItems.hide();
												}
											});

											vePa1.add(tsb);
										}
										typeItems.setWidget(vePa1);
									}
								});

						typeItems.showRelativeTo(arrow);

					}
				});

				arrow.addMouseOverHandler(new MouseOverHandler() {

					@Override
					public void onMouseOver(MouseOverEvent event) {
						typeItems.setWidget(new Label("Loading..."));
						RPCHandlerManager.getTypeHandler().getTypeList(
								innerType,
								new AsyncCallback<ArrayList<Type>>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO
										// TaPMng.getInfoBox().showInfo("typeWidget Error",
										// BoxType.WARNINGBOX);
									}

									@Override
									public void onSuccess(ArrayList<Type> result) {

										VerticalPanel vePa1 = new VerticalPanel();
										// vePa1.add(new Button("---"));
										for (final Type ty : result) {
											Label tsb = new Label(ty.getTitle());
											tsb.setStyleName("TypeWidget-Item");
											tsb.addClickHandler(new ClickHandler() {
												@Override
												public void onClick(
														ClickEvent event) {
													handler.onChange(ty);
													typeItems.hide();
												}
											});

											vePa1.add(tsb);
										}
										typeItems.setWidget(vePa1);
									}
								});

						typeItems.showRelativeTo(arrow);

					}
				});

				iterType = iterType.getSuperType();
			} while (iterType.getSuperType() != null);
		}

		// root
		rootB.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				openRootArrow();
			}
		});

		rootB.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				openRootArrow();
			}
		});

		hoPa1.insert(rootB, 0);
	}

	
	/**
	 * Is called if the root arrow is selected
	 */
	private void openRootArrow() {
		typeItems.setWidget(new Label("Loading..."));
		RPCHandlerManager.getTypeHandler().getTypeList(null,
				new AsyncCallback<ArrayList<Type>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO TaPMng.getInfoBox().showInfo("typeWidget Error",
						// BoxType.WARNINGBOX);
					}

					@Override
					public void onSuccess(ArrayList<Type> result) {

						VerticalPanel vePa1 = new VerticalPanel();
						// vePa1.add(new Button("---"));
						for (final Type ty : result) {

							Label tsb = new Label(ty.getTitle());
							tsb.setStyleName("TypeWidget-Item");
							tsb.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									handler.onChange(ty);
									typeItems.hide();
								}
							});

							vePa1.add(tsb);
						}
						typeItems.setWidget(vePa1);
					}
				});

		typeItems.showRelativeTo(rootB);
	}
}
