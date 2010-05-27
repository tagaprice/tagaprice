CREATE TABLE productType (
	type_id BIGINT NOT NULL,

	PRIMARY KEY (type_id),
	FOREIGN KEY (type_id) REFERENCES entity(ent_id)
);
