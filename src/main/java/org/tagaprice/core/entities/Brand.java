package org.tagaprice.core.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="account")
@PrimaryKeyJoinColumn(name="brand_id")
public class Brand extends RevisionableEntity {
	@Override
	public Integer getRevisionNumber() {
		return 0;
	}
}
