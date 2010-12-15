BEGIN;

CREATE TABLE unitRevision (
	unit_id BIGINT NOT NULL,
	rev INTEGER NOT NULL,
	base_id BIGINT,
	factor FLOAT,

	PRIMARY KEY (unit_id, rev),
	FOREIGN KEY (unit_id) REFERENCES unit(unit_id),
	FOREIGN KEY (base_id) REFERENCES unit(unit_id),
	FOREIGN KEY (unit_id, rev) REFERENCES entityRevision(ent_id, rev),
	
	CHECK (base_id IS NULL = factor IS NULL), -- either none or both have to be set
	CHECK (factor != 0)
);

COMMIT;
