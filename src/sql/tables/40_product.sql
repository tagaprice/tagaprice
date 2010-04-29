CREATE TABLE product (
	ent_id bigint NOT NULL,
	type_id bigint NOT NULL,
	brand_id bigint,

	PRIMARY KEY (ent_id),
	FOREIGN KEY (ent_id) REFERENCES entity (ent_id),
	FOREIGN KEY (type_id) REFERENCES productType (type_id),
	FOREIGN KEY (brand_id) REFERENCES brand (brand_id)
);
