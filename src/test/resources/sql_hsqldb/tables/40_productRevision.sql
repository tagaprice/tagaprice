CREATE TABLE productRevision (
	prod_id BIGINT NOT NULL,
	rev INTEGER NOT NULL,

	type_id BIGINT NULL,
	brand_id BIGINT NULL,
	imageUrl varchar(100) NULL,

	PRIMARY KEY (prod_id, rev),
--TODO fix	FOREIGN KEY (prod_id) REFERENCES product (prod_id),
--TODO fix	FOREIGN KEY (prod_id, rev) REFERENCES entityRevision(ent_id, rev),
--	FOREIGN KEY (type_id) REFERENCES productType (type_id),
--	FOREIGN KEY (brand_id) REFERENCES brand (brand_id)
);
