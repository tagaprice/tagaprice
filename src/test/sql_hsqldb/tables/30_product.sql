CREATE TABLE product (
	prod_id BIGINT NOT NULL,

	PRIMARY KEY (prod_id),
	FOREIGN KEY (prod_id) REFERENCES entity(ent_id)
);
