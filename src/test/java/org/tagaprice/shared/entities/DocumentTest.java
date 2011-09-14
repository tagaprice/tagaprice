package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class DocumentTest {

	@Test
	public void testEmptyDocumentConstructor() {
		Document doc = new Document();
		//assertNull("Document's address should be null", doc.getAddress());
		assertNull("Document's creatorId should be null", doc.getCreatorId());
		assertNull("Document's type should be null", doc.getDocType());
		assertNull("Document's typeName should be null", doc.getDocTypeName());
		assertNull("Document's ID should be null", doc.getId());
		//assertNull("Document's packages should be null", doc.getPackages());
		//assertNull("Document's properties should be null", doc.getPropertyList());
		assertNull("Document's revision should be null", doc.getRevision());
		assertNull("Document's title should be null", doc.getTitle());
	}

	@Test
	public void testNullDocType() {
		Document doc = new Document();

		assertNull("Document's type should be null", doc.getDocType());
		assertNull("Document's typeName should be null", doc.getDocTypeName());
	}

	@Test
	public void testDocTypeConversion() {
		Document doc = new Document();
		
		assertNull(doc.getDocType());
		
		// test if getDocTypeName behaves as expected
		doc._setDocType(Document.Type.PRODUCT);
		assertEquals(Document.Type.PRODUCT.toString(), doc.getDocTypeName());
		assertEquals(Document.Type.PRODUCT, doc.getDocType());

		// try the same for shop (just to be sure)
		doc._setDocType(Document.Type.SHOP);
		assertEquals(Document.Type.SHOP.toString(), doc.getDocTypeName());
		assertEquals(Document.Type.SHOP, doc.getDocType());
		
		// test if string-to-enum-conversion works
		doc.setDocType(Document.Type.PRODUCT.toString());
		assertEquals(Document.Type.PRODUCT, doc.getDocType());

		doc.setDocType(Document.Type.SHOP.toString());
		assertEquals(Document.Type.SHOP, doc.getDocType());
}
}
