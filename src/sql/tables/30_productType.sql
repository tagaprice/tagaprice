CREATE TABLE productType (
	type_id bigint NOT NULL,
	parent_id bigint,

	PRIMARY KEY (type_id),
	FOREIGN KEY (type_id) REFERENCES entity (ent_id),
	FOREIGN KEY (parent_id) REFERENCES productType (type_id)
);
