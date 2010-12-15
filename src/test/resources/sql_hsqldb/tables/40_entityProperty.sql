CREATE TABLE entityProperty (
	eprop_id BIGSERIAL NOT NULL,
	prop_id BIGINT NOT NULL,
	ent_id BIGINT NOT NULL,
	value text NOT NULL,
	unit_id BIGINT,

	min_rev INTEGER NOT NULL,
	max_rev INTEGER NULL,

	PRIMARY KEY (eprop_id),
	FOREIGN KEY (prop_id) REFERENCES property (prop_id),
	FOREIGN KEY (ent_id) REFERENCES entity (ent_id),
	FOREIGN KEY (unit_id) REFERENCES unit (unit_id),
	FOREIGN KEY (ent_id, min_rev) REFERENCES entityRevision(ent_id, rev),
	FOREIGN KEY (ent_id, max_rev) REFERENCES entityRevision(ent_id, rev),
	CHECK (min_rev <= max_rev)
);
