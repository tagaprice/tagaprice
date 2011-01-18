CREATE TABLE receiptEntry (
	receipt_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	product_revision BIGINT NOT NULL,
	count INTEGER NOT NULL, -- DEFAULT 1
	price BIGINT NOT NULL,

	PRIMARY KEY (receipt_id, product_id),
	FOREIGN KEY (receipt_id) REFERENCES entity (ent_id)
	FOREIGN KEY (product_id) REFERENCES product (ent_id)
	FOREIGN KEY (product_revision) REFERENCES productRevision (rev)
);
