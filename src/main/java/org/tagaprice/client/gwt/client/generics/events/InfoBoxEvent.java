package org.tagaprice.client.gwt.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This event defines all function a InfoBox can have.
 * 
 */
public class InfoBoxEvent extends GwtEvent<InfoBoxEventHandler> {

	public static Type<InfoBoxEventHandler> TYPE = new Type<InfoBoxEventHandler>();


	public enum INFOTYPE {
		SUCCESS, INFO, ERROR
	};

	private final String _info;
	private final INFOTYPE _type;
	private final int _autoCloseTime;

	/**
	 * Create an {@link InfoBoxEvent} that is handled by an {@link InfoBoxEventHandler}. The InfoBox will close after
	 * 2000ml.
	 * 
	 * @param info
	 *            The displayed text
	 * @param type
	 *            The INFOTYPE which should be displayed. 3 INFOTYPE (SUCCESS, INFO, ERROR)
	 */
	public InfoBoxEvent(String info, INFOTYPE type) {
		this(info, type, 2000);
	}


	/**
	 * Create an {@link InfoBoxEvent} that is handled by an {@link InfoBoxEventHandler}
	 * 
	 * @param info
	 *            The displayed text
	 * @param type
	 *            The INFOTYPE which should be displayed. 3 INFOTYPE (SUCCESS, INFO, ERROR)
	 * @param autoCloseTime
	 *            defines the times in [ms] after the InfoBox should be auto closed. If time is [0] Info box will not
	 *            auto close.
	 */
	public InfoBoxEvent(String info, INFOTYPE type, int autoCloseTime) {
		_info = info;
		_type = type;
		_autoCloseTime = autoCloseTime;
	}



	/**
	 * @return the info
	 */
	public String getInfo() {
		return _info;
	}

	/**
	 * @return the type
	 */
	public INFOTYPE getType() {
		return _type;
	}

	/**
	 * @return the autoCloseTime
	 */
	public int getAutoCloseTime() {
		return _autoCloseTime;
	}


	@Override
	protected void dispatch(InfoBoxEventHandler handler) {
		handler.onNewInfo(this);
	}

	@Override
	public Type<InfoBoxEventHandler> getAssociatedType() {
		return InfoBoxEvent.TYPE;
	}



}
