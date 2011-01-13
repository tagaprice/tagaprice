CREATE TABLE typeRevision (
	type_id BIGINT NOT NULL,
	rev INTEGER NOT NULL,

	parent_id BIGINT NULL,

	PRIMARY KEY (type_id, rev),
	FOREIGN KEY (type_id, rev) REFERENCES entityRevision (ent_id, rev),
	FOREIGN KEY (type_id) REFERENCES productType (type_id),
	FOREIGN KEY (parent_id) REFERENCES productType (type_id)
);
