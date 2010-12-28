CREATE TABLE entityRevision (
	ent_id BIGINT NOT NULL,
	rev INTEGER NOT NULL,

	title varchar(255) NOT NULL,
	created_at TIMESTAMP DEFAULT NOW() NOT NULL,
	-- id of another entity (e.g. the english equivalent)
	group_id BIGINT NULL,

	PRIMARY KEY (ent_id, rev),
	FOREIGN KEY (ent_id) REFERENCES entity(ent_id),
--	FOREIGN KEY (group_id) REFERENCES entity (ent_id),
--	CHECK (rev >= 1)
);

