package org.tagaprice.client.gwt.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This event defines all function a InfoBox can have.
 * 
 */
public class InfoBoxShowEvent extends GwtEvent<InfoBoxShowEventHandler> {

	public static Type<InfoBoxShowEventHandler> TYPE = new Type<InfoBoxShowEventHandler>();


	public enum INFOTYPE {
		SUCCESS, INFO, ERROR
	};

	private final String _info;
	private final INFOTYPE _type;
	private final int _autoCloseTime;
	private final Class<?> _class;

	/**
	 * Create an {@link InfoBoxEvent} that is handled by an {@link InfoBoxEventHandler}. The InfoBox will close after
	 * 2000ml.
	 * 
	 * @param iclass
	 *            the sender class
	 * @param info
	 *            The displayed text
	 * @param type
	 *            The INFOTYPE which should be displayed. 3 INFOTYPE (SUCCESS, INFO, ERROR)
	 */
	public InfoBoxShowEvent(Class<?> iclass, String info, INFOTYPE type) {
		this(iclass, info, type, 2000);
	}


	/**
	 * Create an {@link InfoBoxEvent} that is handled by an {@link InfoBoxEventHandler}
	 * 
	 * @param iclass
	 *            the sender class
	 * @param info
	 *            The displayed text
	 * @param type
	 *            The INFOTYPE which should be displayed. 3 INFOTYPE (SUCCESS, INFO, ERROR)
	 * @param autoCloseTime
	 *            defines the times in [ms] after the InfoBox should be auto closed. If time is [0] Info box will not
	 *            auto close.
	 */
	public InfoBoxShowEvent(Class<?> iclass, String info, INFOTYPE type, int autoCloseTime) {
		_class = iclass;
		_info = info;
		_type = type;
		_autoCloseTime = autoCloseTime;
	}

	public Class<?> getSenderClass(){
		return _class;
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
	protected void dispatch(InfoBoxShowEventHandler handler) {
		handler.onNewInfo(this);
	}

	@Override
	public Type<InfoBoxShowEventHandler> getAssociatedType() {
		return InfoBoxShowEvent.TYPE;
	}



}
