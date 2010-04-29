CREATE TABLE account (
	uid		bigint NOT NULL,

	PRIMARY KEY (uid),
	FOREIGN KEY (uid) REFERENCES entity (ent_id)
);
