package org.tagaprice.core.entities;

import javax.persistence.*;


@Entity
@Table(name="typerevision")
@PrimaryKeyJoinColumns({
	@PrimaryKeyJoinColumn(name="type_id", referencedColumnName="ent_id"),
	@PrimaryKeyJoinColumn(name="rev", referencedColumnName="rev")
})
@SecondaryTable(name="producttype", pkJoinColumns={
		@PrimaryKeyJoinColumn(name="type_id", referencedColumnName="type_id")
})
public class Category extends RevisionableEntity {

	private Category _parent;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Category getParent() {
		return _parent;
	}

	@SuppressWarnings("unused")
	private void setParent(Category parent) {
		_parent = parent;
	}
}
