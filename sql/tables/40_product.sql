CREATE TABLE product (
	prod_id BIGINT NOT NULL,
	type_id BIGINT,

	PRIMARY KEY (prod_id),
	FOREIGN KEY (prod_id) REFERENCES entity(ent_id),
	FOREIGN KEY (type_id) REFERENCES productType(type_id)
);
