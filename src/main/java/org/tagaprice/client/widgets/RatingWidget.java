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

	private int dynRating = 0;
	private boolean editable;
	private ClickHandler exthandler;
	private int height;
	private SimplePanel noStars;
	private int rating;
	private Image stars;
	private int width;

	/**
	 * 
	 * @param rating
	 *            Rating is in percent (0-100)
	 * @param editable
	 *            Is editable=false you can't change to editable=true
	 */
	public @UiConstructor
	RatingWidget(int rating, boolean editable) {
		this.rating = validateRating(rating);
		this.editable = editable;
		this.width = ImageBundle.INSTANCE.rating0().getWidth();
		this.height = ImageBundle.INSTANCE.rating0().getHeight();

		if (this.editable)
			startEditable();
		else
			startStatic();

	}

	/**
	 * Doesn't work if editable is false.
	 * 
	 * @param handler
	 */
	public void addClickHandler(ClickHandler handler) {
		exthandler = handler;
	}

	/**
	 * Returns the rating in percent.
	 * 
	 * @return Is in percent (0-100)
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Sets the rating in percent.
	 * 
	 * @param rating
	 *            Is in percent (0-100)
	 */
	public void setRating(int rating) {
		rating = validateRating(rating);
		if (editable == true) {
			this.rating = rating;
			setStars(calcRating(this.rating));
		} else {
			this.rating = rating;
			stars.setSize(normalizeWidth(this.rating) + "px", height + "px");
		}
	}

	/**
	 * 
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
	 * 
	 * @param percent
	 * @return
	 */
	private int normalizeWidth(int percent) {
		return (this.width * percent) / 100;
	}

	/**
	 * 
	 * @param rating
	 */
	private void setStars(int rating) {
		if (rating == 20) {
			stars.setResource(ImageBundle.INSTANCE.rating1());
		}
		if (rating == 40) {
			stars.setResource(ImageBundle.INSTANCE.rating2());
		}
		if (rating == 60) {
			stars.setResource(ImageBundle.INSTANCE.rating3());
		}
		if (rating == 80) {
			stars.setResource(ImageBundle.INSTANCE.rating4());
		}
		if (rating == 100) {
			stars.setResource(ImageBundle.INSTANCE.rating5());
		}
	}

	/**
	 * 
	 */
	private void startEditable() {
		stars = new Image(ImageBundle.INSTANCE.rating0());
		initWidget(stars);
		setStars(calcRating(rating));
		stars.setSize(width + "px", height + "px");

		stars.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				int fifth = stars.getWidth() / 5;
				if (event.getX() > fifth * 0 && event.getX() <= fifth * 1) {
					stars.setResource(ImageBundle.INSTANCE.ratingChoose1());
					dynRating = 20;
				}
				if (event.getX() > fifth * 1 && event.getX() <= fifth * 2) {
					stars.setResource(ImageBundle.INSTANCE.ratingChoose2());
					dynRating = 40;
				}
				if (event.getX() > fifth * 2 && event.getX() <= fifth * 3) {
					stars.setResource(ImageBundle.INSTANCE.ratingChoose3());
					dynRating = 60;
				}
				if (event.getX() > fifth * 3 && event.getX() <= fifth * 4) {
					stars.setResource(ImageBundle.INSTANCE.ratingChoose4());
					dynRating = 80;
				}
				if (event.getX() > fifth * 4 && event.getX() <= fifth * 5) {
					stars.setResource(ImageBundle.INSTANCE.ratingChoose5());
					dynRating = 100;
				}

			}
		});

		stars.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				setStars(calcRating(rating));

			}
		});

		stars.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rating = dynRating;
				setStars(calcRating(dynRating));

				if (exthandler != null)
					exthandler.onClick(event);

			}
		});
	}

	/**
	 * 
	 */
	private void startStatic() {
		noStars = new SimplePanel();
		initWidget(noStars);

		// set 0Stars
		noStars.setStyleName("RatingWidget0Stars");
		noStars.setSize(width + "px", height + "px");

		// set 5Stars
		stars = new Image(ImageBundle.INSTANCE.rating5());
		noStars.setWidget(stars);
		stars.setSize(normalizeWidth(rating) + "px", height + "px");
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
