package org.tagaprice.client.gwt.client.generics.widgets.devView;


import org.tagaprice.client.gwt.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.gwt.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.gwt.client.generics.widgets.IInfoBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InfoBox extends Composite implements IInfoBox {

	private VerticalPanel _infos = new VerticalPanel();

	public InfoBox() {
		initWidget(_infos);
	}

	@Override
	public void addInfoBoxEvent(InfoBoxShowEvent event) {

		_infos.add(new SimpleInfo(event));
	}

	class SimpleInfo extends SimplePanel {

		public SimpleInfo(InfoBoxShowEvent event) {

			if (event.getType() == INFOTYPE.SUCCESS) {
				setStyleName("Success");
			} else if (event.getType() == INFOTYPE.INFO) {
				setStyleName("Info");
			} else if (event.getType() == INFOTYPE.ERROR) {
				setStyleName("Error");
			}

			if (event.getAutoCloseTime() > 0) {
				setWidget(new Label(event.getInfo()));

				Timer t = new Timer() {
					@Override
					public void run() {
						removeMe();
					}
				};

				t.schedule(event.getAutoCloseTime());

			} else {
				HorizontalPanel _hoPa = new HorizontalPanel();
				_hoPa.setWidth("100%");
				_hoPa.add(new Label(event.getInfo()));
				_hoPa.add(new Button("x", new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						removeMe();
					}
				}));

				setWidget(_hoPa);
			}
		}

		private void removeMe() {
			removeFromParent();
		}
	}

}
