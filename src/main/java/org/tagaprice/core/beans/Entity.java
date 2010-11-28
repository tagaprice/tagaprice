package org.tagaprice.core.beans;

/**
 * Uses setter chaining for more details see http://weblogs.java.net/blog/emcmanus/archive/2010/10/25/using-builder-pattern-subclasses
 * @author "forste"
 *
 * @param <T>
 */
public abstract class Entity<T extends Entity<T>>{
	private Long _id = null;
	private String _title;
	//	private Long _rev = null;


	public Entity() {
	}

	protected abstract T self();

	public Long getId() {
		return _id;
	}

	public T setId(Long id) {
		_id = id;
		return self();
	}


	public String getTitle() {
		return _title;
	}


	public T setTitle(String title) {
		_title = title;
		return self();
	}


	//	public Long getRev() {
	//		return _rev;
	//	}
	//
	//
	//	public void setRev(Long rev) {
	//		_rev = rev;
	//	}

}
