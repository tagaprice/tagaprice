package org.tagaprice.core.beans;

public class ProductRevision extends EntityRevision {

	/** TODO change this to type URL ? */
	private String _imageUrl;


	@SuppressWarnings("unused")
	private ProductRevision() { }

	public ProductRevision(String title) {
		super(title);
	}


	@SuppressWarnings("unused")
	private void setImageUrl(String imageUrl) {
		_imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return _imageUrl;
	}
}
