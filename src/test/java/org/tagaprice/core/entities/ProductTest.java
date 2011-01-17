package org.tagaprice.core.entities;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.tagaprice.server.service.helper.EntityCreator;


public class ProductTest {


	@Test
	public void getRevisions_shouldBeOrdered() throws Exception {
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(EntityCreator.createProductRevision(0L, 1));
		revisions.add(EntityCreator.createProductRevision(0L, 2));
		revisions.add(EntityCreator.createProductRevision(0L, 3));

		Product product = EntityCreator.createProductWithRevisions(0L, revisions);

		assertThat(product.getRevisions().first(), equalTo(EntityCreator.createProductRevision(0L, 3)));
		assertThat(product.getRevisions().last(), equalTo(EntityCreator.createProductRevision(0L, 1)));
	}
}
