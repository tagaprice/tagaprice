CREATE TABLE revisionedEntity (
	ent_id bigserial NOT NULL,
	rev integer NOT NULL,
	rev_ts timestamp NOT NULL DEFAULT now(),
	title varchar(100) NOT NULL,
	uid bigint NOT NULL,

	PRIMARY KEY (ent_id, rev),
	FOREIGN KEY (ent_id) REFERENCES entity (ent_id),
	FOREIGN KEY (uid) REFERENCES account (uid)
);
