CREATE TABLE entityRevision (
	ent_id BIGINT,
	rev INTEGER NOT NULL,

	title varchar(255) NOT NULL,
	created_at TIMESTAMP DEFAULT NOW(), -- TODO fix this NOT NULL,
	creator BIGINT,
	
	PRIMARY KEY (ent_id, rev),
--TODO fix	FOREIGN KEY (ent_id) REFERENCES entity(ent_id),
--TODO fix	FOREIGN KEY (creator_id) REFERENCES account (account_id),
--	CHECK (rev >= 1)
);

