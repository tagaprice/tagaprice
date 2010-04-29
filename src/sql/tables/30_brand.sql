CREATE TABLE brand (
	brand_id bigint NOT NULL,

	PRIMARY KEY (brand_id),
	FOREIGN KEY (brand_id) REFERENCES entity (ent_id)
);
