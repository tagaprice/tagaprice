CREATE TABLE entityProperty (
	ent_id bigint NOT NULL,
	prop_id bigint NOT NULL,
	min_rev integer NOT NULL,
	index integer,
	max_rev integer,
	value text NOT NULL,

	PRIMARY KEY (ent_id, prop_id, min_rev, index),
	FOREIGN KEY (ent_id, min_rev) REFERENCES revisionedEntity (ent_id, rev),
	FOREIGN KEY (prop_id) REFERENCES property (prop_id),
	FOREIGN KEY (ent_id, max_rev) REFERENCES revisionedEntity (ent_id, rev)
);
