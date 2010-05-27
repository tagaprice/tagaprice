create table unit (
	unit_id BIGINT NOT NULL,
	fallback_unit BIGINT NULL,
	factor float NULL,

	PRIMARY KEY (unit_id),
	FOREIGN KEY (unit_id) REFERENCES entity (ent_id),
	FOREIGN KEY (fallback_unit) REFERENCES unit (unit_id)
);
