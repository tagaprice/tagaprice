create table unit (
	unit_id BIGINT NOT NULL,
	currentRevision INTEGER NOT NULL,

	PRIMARY KEY (unit_id),
	FOREIGN KEY (unit_id) REFERENCES entity(ent_id)
);