BEGIN;

CREATE TABLE unitRevision (
	unit_id BIGINT NOT NULL,
	rev INTEGER NOT NULL,
	base_id BIGINT,
	factor FLOAT,

	PRIMARY KEY (unit_id, rev),
	FOREIGN KEY (unit_id) REFERENCES unit(unit_id),
	FOREIGN KEY (base_id) REFERENCES unit(unit_id),
	CHECK (base_id IS NULL = factor IS NULL)
);

ALTER TABLE unit ADD CONSTRAINT fkey_unit_currentRev FOREIGN KEY (unit_id, currentRevision) REFERENCES unitRevision(unit_id, rev) DEFERRABLE INITIALLY DEFERRED;


COMMIT;
