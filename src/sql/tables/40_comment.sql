CREATE TABLE comment (
	ent_id bigint NOT NULL,
	uid bigint NOT NULL,
	parent_id bigint NOT NULL,
	text varchar(140) NOT NULL,

	PRIMARY KEY (ent_id),
	FOREIGN KEY (ent_id) REFERENCES entity (ent_id),
	FOREIGN KEY (parent_id) REFERENCES entity(ent_id),
	FOREIGN KEY (uid) REFERENCES account(uid),
	CHECK (parent_id <> ent_id)
);
