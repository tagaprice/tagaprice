package org.tagaprice.core.entities;

import java.util.Comparator;

public class RevisionComparator implements Comparator<ProductRevision> {

	@Override
	public int compare(ProductRevision arg0, ProductRevision arg1) {
		return arg1.getRevisionNumber() - arg0.getRevisionNumber();
	}

}