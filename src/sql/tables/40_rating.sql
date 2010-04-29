CREATE TABLE rating (
	ent_id bigint NOT NULL,
	uid bigint NOT NULL,
	rating smallint NOT NULL,

	PRIMARY KEY (ent_id, uid),
	FOREIGN KEY (ent_id) REFERENCES entity (ent_id),
	FOREIGN KEY (uid) REFERENCES account (uid),
	CHECK (rating >= 0),
	check (rating <= 10)
);
