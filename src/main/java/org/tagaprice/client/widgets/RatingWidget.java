/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: RatingWidget.java
 * Date: 12.05.2010
 */
package org.tagaprice.client.widgets;

import org.tagaprice.client.ImageBundle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Edit able RatingWidget with stars.
 * 
 */
public class RatingWidget extends Composite {

	private int _dynRating = 0;
	private boolean _editable;
	private ClickHandler _exthandler;
	private int _height;
	private SimplePanel _noStars;
	private int _rating;
	private Image _stars;
	private int _width;

	/**
	 * Create a RatingWidget with the rating in percent (0-100) and an edit able
	 * flag. If editable=false at constructing, the widget can't be set to
	 * editable=true afterwards.
	 * 
	 * @param rating
	 *            Rating is in percent (0-100)
	 * @param editable
	 *            Is editable=false you can't change to editable=true
	 */
	public @UiConstructor
	RatingWidget(int rating, boolean editable) {
		this._rating = validateRating(rating);
		this._editable = editable;
		this._width = ImageBundle.INSTANCE.rating0().getWidth();
		this._height = ImageBundle.INSTANCE.rating0().getHeight();

		if (this._editable)
			startEditable();
		else
			startStatic();

	}

	/**
	 * Doesn't work if editable is false.
	 * 
	 * @param handler is called if the rating is being changed.
	 */
	public void addClickHandler(ClickHandler handler) {
		_exthandler = handler;
	}

	/**
	 * Returns the rating in percent.
	 * 
	 * @return is in percent (0-100)
	 */
	public int getRating() {
		return _rating;
	}

	/**
	 * Sets the rating in percent.
	 * 
	 * @param rating
	 *            is in percent (0-100)
	 */
	public void setRating(int rating) {
		rating = validateRating(rating);
		if (_editable == true) {
			this._rating = rating;
			setStars(calcRating(this._rating));
		} else {
			this._rating = rating;
			_stars.setSize(normalizeWidth(this._rating) + "px", _height + "px");
		}
	}

	/**
	 * Returns the Rating steps (5)
	 * @param percent
	 * @return
	 */
	private int calcRating(int percent) {
		if (percent > 0 && percent <= 20) {
			return 20;
		}
		if (percent > 20 && percent <= 40) {
			return 40;
		}
		if (percent > 40 && percent <= 60) {
			return 60;
		}
		if (percent > 60 && percent <= 80) {
			return 80;
		}
		if (percent > 80 && percent <= 100) {
			return 100;
		}
		return 0;
	}

	/**
	 * Normalize the percent value to the widht value
	 * @param percent
	 * @return
	 */
	private int normalizeWidth(int percent) {
		return (this._width * percent) / 100;
	}

	/**
	 * Set the rating and displays it.
	 * @param rating
	 */
	private void setStars(int rating) {
		if (rating == 20) {
			_stars.setResource(ImageBundle.INSTANCE.rating1());
		}
		if (rating == 40) {
			_stars.setResource(ImageBundle.INSTANCE.rating2());
		}
		if (rating == 60) {
			_stars.setResource(ImageBundle.INSTANCE.rating3());
		}
		if (rating == 80) {
			_stars.setResource(ImageBundle.INSTANCE.rating4());
		}
		if (rating == 100) {
			_stars.setResource(ImageBundle.INSTANCE.rating5());
		}
	}

	/**
	 * Registers mouseHandler on the Widget if the constructor flag editable is set TRUE
	 */
	private void startEditable() {
		_stars = new Image(ImageBundle.INSTANCE.rating0());
		initWidget(_stars);
		setStars(calcRating(_rating));
		_stars.setSize(_width + "px", _height + "px");

		_stars.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				int fifth = _stars.getWidth() / 5;
				if (event.getX() > fifth * 0 && event.getX() <= fifth * 1) {
					_stars.setResource(ImageBundle.INSTANCE.ratingChoose1());
					_dynRating = 20;
				}
				if (event.getX() > fifth * 1 && event.getX() <= fifth * 2) {
					_stars.setResource(ImageBundle.INSTANCE.ratingChoose2());
					_dynRating = 40;
				}
				if (event.getX() > fifth * 2 && event.getX() <= fifth * 3) {
					_stars.setResource(ImageBundle.INSTANCE.ratingChoose3());
					_dynRating = 60;
				}
				if (event.getX() > fifth * 3 && event.getX() <= fifth * 4) {
					_stars.setResource(ImageBundle.INSTANCE.ratingChoose4());
					_dynRating = 80;
				}
				if (event.getX() > fifth * 4 && event.getX() <= fifth * 5) {
					_stars.setResource(ImageBundle.INSTANCE.ratingChoose5());
					_dynRating = 100;
				}

			}
		});

		_stars.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				setStars(calcRating(_rating));

			}
		});

		_stars.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_rating = _dynRating;
				setStars(calcRating(_dynRating));

				if (_exthandler != null)
					_exthandler.onClick(event);

			}
		});
	}

	/**
	 * 
	 */
	private void startStatic() {
		_noStars = new SimplePanel();
		initWidget(_noStars);

		// set 0Stars
		_noStars.setStyleName("RatingWidget0Stars");
		_noStars.setSize(_width + "px", _height + "px");

		// set 5Stars
		_stars = new Image(ImageBundle.INSTANCE.rating5());
		_noStars.setWidget(_stars);
		_stars.setSize(normalizeWidth(_rating) + "px", _height + "px");
	}

	/**
	 * Validates rating if it is between 0% and 100%, instead return 0.
	 * 
	 * @param rating
	 * @return Returns rating. If invalid 0
	 */
	private int validateRating(int rating) {
		if (rating < 0 || rating > 100) {
			return 0;
		}
		return rating;
	}

}
