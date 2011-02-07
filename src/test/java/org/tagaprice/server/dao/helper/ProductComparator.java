package org.tagaprice.server.dao.helper;

import static org.junit.Assert.assertEquals;

import java.util.SortedSet;

import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;

public class ProductComparator {

	public static void compareProductsAndRevisions(Product actual, Product expected) {
		assertEquals(actual, expected);
		
		compareProductRevisions(actual.getRevisions(), expected.getRevisions());
	}

	public static void compareProductRevisions(
			SortedSet<ProductRevision> actual,
			SortedSet<ProductRevision> expected) {
		assertEquals(actual, expected);
	}
}
