package org.tagaprice.client.gwt.client.generics.widgets.devView;


import org.tagaprice.client.gwt.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.gwt.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.gwt.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.gwt.client.generics.widgets.IInfoBox;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

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
	private static MyLogger _logger = LoggerFactory
	.getLogger(InfoBox.class);
	public InfoBox() {
		initWidget(_infos);
	}

	@Override
	public void addInfoBoxEvent(InfoBoxShowEvent event) {
		_infos.add(new SimpleInfo(event));
	}

	@Override
	public void removeInfoBoxEvent(InfoBoxDestroyEvent event) {
		InfoBox._logger.log("destroy events");
		// Destroy all from this class

		if (event.getType() == null) {
			System.err.println("type=null");
			for (int i = 0; i < _infos.getWidgetCount(); i++) {
				if (((SimpleInfo) _infos.getWidget(i)).getEvent().getSenderClass() == event.getDestroyerClass()) {
					((SimpleInfo) _infos.getWidget(i)).removeMe();
					i--;
				}
			}
		} else {
			System.err.println("type=Notnull");
			for (int i = 0; i < _infos.getWidgetCount(); i++) {
				if (((SimpleInfo) _infos.getWidget(i)).getEvent().getSenderClass() == event.getDestroyerClass() &&
						((SimpleInfo) _infos.getWidget(i)).getEvent().getType()==event.getType()) {
					((SimpleInfo) _infos.getWidget(i)).removeMe();
					i--;
				}
			}
		}

		// TODO Auto-generated method stub

	}

	class SimpleInfo extends SimplePanel {

		InfoBoxShowEvent _event;

		public SimpleInfo(InfoBoxShowEvent event) {
			_event = event;

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

		public void removeMe() {
			removeFromParent();
		}

		public InfoBoxShowEvent getEvent() {
			return _event;
		}
	}



}
