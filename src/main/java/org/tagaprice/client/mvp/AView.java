package org.tagaprice.client.gwt.client.mvp;

public abstract class AView<PresenterType> implements IView<PresenterType> {

	protected PresenterType _presenter;

	@Override
	public void setPresenter(PresenterType presenter) {
		this._presenter = presenter;
	}

}
